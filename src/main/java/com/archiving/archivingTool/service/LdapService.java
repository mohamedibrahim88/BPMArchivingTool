package com.archiving.archivingTool.service;

import com.archiving.archivingTool.config.AppLdapProps;
import com.archiving.archivingTool.dto.Group;
import com.archiving.archivingTool.dto.User;
import com.archiving.archivingTool.dto.UserRequest;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.naming.ldap.LdapName;
import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LdapService {

    private final LdapTemplate ldapTemplate;
    private final AppLdapProps props;
    private final PasswordEncoder passwordEncoder;

    public LdapService(LdapTemplate ldapTemplate, AppLdapProps props, PasswordEncoder passwordEncoder) {
        this.ldapTemplate = ldapTemplate;
        this.props = props;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(UserRequest request) throws Exception {
        Name userDn = LdapNameBuilder.newInstance(props.getUsersBase())
                .add("uid", request.getUsername())
                .build();

        DirContextAdapter context = new DirContextAdapter(userDn);
        context.setAttributeValues("objectClass",
                new String[]{"top", "person", "organizationalPerson", "inetOrgPerson"});
        context.setAttributeValue("cn", request.getFirstName() + " " + request.getLastName());
        context.setAttributeValue("sn", request.getLastName());
        context.setAttributeValue("givenName", request.getFirstName());
        context.setAttributeValue("mail", request.getEmail());
        context.setAttributeValue("uid", request.getUsername());

        // Let LDAP handle the password encoding
        context.setAttributeValue("userPassword", request.getPassword());

        ldapTemplate.bind(context);
    }

    public List<User> getAllUsers() {
        return ldapTemplate.search(
                props.getUsersBase(),
                "(objectclass=inetOrgPerson)",
                (AttributesMapper<User>) attributes -> {
                    return new User(
                            getAttributeValue(attributes, "uid"),
                            getAttributeValue(attributes, "cn"),
                            getAttributeValue(attributes, "mail")
                    );
                }
        );
    }

    public User getUser(String username) {
        EqualsFilter filter = new EqualsFilter("uid", username);
        List<User> users = ldapTemplate.search(
                props.getUsersBase(),
                filter.encode(),
                (AttributesMapper<User>) attributes -> {
                    return new User(
                            getAttributeValue(attributes, "uid"),
                            getAttributeValue(attributes, "cn"),
                            getAttributeValue(attributes, "mail")
                    );
                }
        );

        if (users.isEmpty()) {
            throw new RuntimeException("User not found: " + username);
        }

        return users.get(0);
    }

    public void deleteUser(String username) {
        Name userDn = LdapNameBuilder.newInstance(props.getUsersBase())
                .add("uid", username)
                .build();
        ldapTemplate.unbind(userDn);
    }

    // Group operations
    public void createGroup(String groupName) {
        Name groupDn = LdapNameBuilder.newInstance(props.getGroupsBase())
                .add("cn", groupName)
                .build();

        DirContextAdapter context = new DirContextAdapter(groupDn);
        context.setAttributeValues("objectClass",
                new String[]{"top", "groupOfNames"});
        context.setAttributeValue("cn", groupName);
        // Add a required member (can be a dummy or admin user)
        context.setAttributeValue("member", "cn=admin," + props.getBase());

        ldapTemplate.bind(context);
    }

    public List<Group> getAllGroups() {
        return ldapTemplate.search(
                props.getGroupsBase(),
                "(objectclass=groupOfNames)",
                (AttributesMapper<Group>) attributes -> {
                    return new Group(
                            getAttributeValue(attributes, "cn"),
                            getMemberCount(attributes)
                    );
                }
        );
    }

    public List<User> getUsersInGroup(String groupName) {
        // First get all member DNs from the group
        List<String> memberDns = ldapTemplate.search(
                        props.getGroupsBase(),
                        "(&(objectclass=groupOfNames)(cn=" + groupName + "))",
                        (AttributesMapper<List<String>>) attributes -> {
                            List<String> members = new ArrayList<>();
                            Attribute memberAttr = attributes.get("member");
                            if (memberAttr != null) {
                                NamingEnumeration<?> enumeration = memberAttr.getAll();
                                while (enumeration.hasMore()) {
                                    members.add(enumeration.next().toString());
                                }
                            }
                            return members;
                        }
                ).stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        List<User> users = new ArrayList<>();
        for (String memberDn : memberDns) {
            // Extract username from DN and get user details
            if (memberDn.contains("uid=")) {
                String username = extractUsernameFromDn(memberDn);
                try {
                    users.add(getUser(username));
                } catch (Exception e) {
                    System.err.println("User not found: " + username + ", DN: " + memberDn);
                    // Skip if user not found
                }
            }
        }
        return users;
    }

    public void addUserToGroup(String username, String groupName) {
        try {
            Name groupDn = LdapNameBuilder.newInstance(props.getGroupsBase())
                    .add("cn", groupName)
                    .build();

            // Search for the user to get their actual DN
            List<String> userDns = ldapTemplate.search(
                    props.getUsersBase(),
                    "(&(objectclass=inetOrgPerson)(uid=" + username + "))",
                    (AttributesMapper<String>) attributes -> {
                        // This approach doesn't give us the DN directly
                        return null;
                    }
            );

            // Build the DN properly
            String userDn = "uid=" + username + "," + props.getUsersBase();
            if (!userDn.endsWith(props.getBase())) {
                userDn = userDn + "," + props.getBase();
            }

            ModificationItem[] mods = new ModificationItem[]{
                    new ModificationItem(DirContext.ADD_ATTRIBUTE,
                            new BasicAttribute("member", userDn))
            };

            ldapTemplate.modifyAttributes(groupDn, mods);

        } catch (Exception e) {
            throw new RuntimeException("Failed to add user to group: " + e.getMessage(), e);
        }
    }

    public void removeUserFromGroup(String username, String groupName) {
        Name groupDn = LdapNameBuilder.newInstance(props.getGroupsBase())
                .add("cn", groupName)
                .build();

        // Build the complete user DN including the base
        Name userDn = LdapNameBuilder.newInstance(props.getBase()) // Start with base DN
                .add(props.getUsersBase().split(",")[0]) // Add ou=users
                .add("uid", username) // Add uid=username
                .build();

        ModificationItem[] mods = new ModificationItem[]{
                new ModificationItem(DirContext.REMOVE_ATTRIBUTE,
                        new BasicAttribute("member", userDn.toString()))
        };

        ldapTemplate.modifyAttributes(groupDn, mods);
    }

    public void deleteGroup(String groupName) {
        Name groupDn = LdapNameBuilder.newInstance(props.getGroupsBase())
                .add("cn", groupName)
                .build();
        ldapTemplate.unbind(groupDn);
    }

    private String extractUsernameFromDn(String dn) {
        // Extract username from DN like "uid=john,ou=users,dc=example,dc=com"
        if (dn.contains("uid=")) {
            String[] parts = dn.split(",");
            for (String part : parts) {
                if (part.trim().startsWith("uid=")) {
                    return part.trim().substring(4);
                }
            }
        }
        return null;
    }

    private String getAttributeValue(Attributes attributes, String attributeName) throws NamingException {
        Attribute attribute = attributes.get(attributeName);
        return attribute != null ? attribute.get().toString() : "";
    }

    private int getMemberCount(Attributes attributes) throws NamingException {
        Attribute memberAttr = attributes.get("member");
        return memberAttr != null ? memberAttr.size() : 0;
    }

    public boolean groupExists(String groupName) {
        try {
            List<Group> groups = ldapTemplate.search(
                    props.getGroupsBase(),
                    "(&(objectclass=groupOfNames)(cn=" + groupName + "))",
                    (AttributesMapper<Group>) attributes -> {
                        return new Group(
                                getAttributeValue(attributes, "cn"),
                                getMemberCount(attributes)
                        );
                    }
            );
            return !groups.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean userExists(String username) {
        try {
            List<User> users = ldapTemplate.search(
                    props.getUsersBase(),
                    "(&(objectclass=inetOrgPerson)(uid=" + username + "))",
                    (AttributesMapper<User>) attributes -> {
                        return new User(
                                getAttributeValue(attributes, "uid"),
                                getAttributeValue(attributes, "cn"),
                                getAttributeValue(attributes, "mail")
                        );
                    }
            );
            return !users.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUserInGroup(String username, String groupName) {
        try {
            // First, check if group exists
            if (!groupExists(groupName)) {
                return false;
            }

            // Build group DN
            LdapName groupDn = LdapNameBuilder.newInstance()
                    .add(props.getGroupsBase())
                    .add("cn", groupName)
                    .build();

            // Get the group entry
            DirContextOperations groupContext = ldapTemplate.lookupContext(groupDn);

            // Get the member attribute values
            Attribute memberAttribute = groupContext.getAttributes().get("member");
            if (memberAttribute == null) {
                return false;
            }

            // Build user DN to check against
            LdapName userDn = LdapNameBuilder.newInstance()
                    .add(props.getUsersBase())
                    .add("uid", username)
                    .build();

            String userDnString = userDn.toString();

            // Check if user DN is in the member list
            for (int i = 0; i < memberAttribute.size(); i++) {
                String memberDn = (String) memberAttribute.get(i);
                if (memberDn.toLowerCase().contains(userDnString.toLowerCase())) {
                    return true;
                }
            }

            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error checking if user '" + username + "' is in group '" + groupName + "'", e);
        }
    }

    public void updateUserPassword(String username, String newPassword) {
        try {
            LdapName userDn = LdapNameBuilder.newInstance(props.getUsersBase())
                    .add("uid", username)
                    .build();

            // Don't encode, let LDAP handle it
            ModificationItem[] mods = new ModificationItem[]{
                    new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
                            new BasicAttribute("userPassword", newPassword))
            };

            ldapTemplate.modifyAttributes(userDn, mods);
        } catch (Exception e) {
            throw new RuntimeException("Error updating password for user: " + username, e);
        }
    }


    // Helper method to get user DN
    private String getUserDn(String username) {
        return "uid=" + username + ","+ props.getUsersBase() + "," + props.getBase();
    }

    // Helper method to get group DN
    private String getGroupDn(String groupName) {
        return "cn=" + groupName + "," + props.getGroupsBase();
    }

}