package com.archiving.archivingTool.service;

import com.archiving.archivingTool.config.AppLdapProps;
import com.archiving.archivingTool.dto.Group;
import com.archiving.archivingTool.dto.User;
import com.archiving.archivingTool.dto.UserRequest;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class LdapService {

    private final LdapTemplate ldapTemplate;
    private final AppLdapProps props;

    public LdapService(LdapTemplate ldapTemplate, AppLdapProps props) {
        this.ldapTemplate = ldapTemplate;
        this.props = props;
    }

    // User operations
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
        context.setAttributeValue("userPassword", "{SHA}" + java.util.Base64.getEncoder()
                .encodeToString(java.security.MessageDigest.getInstance("SHA-1")
                        .digest(request.getPassword().getBytes())));
        context.setAttributeValue("uid", request.getUsername());

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
        // First get the group to find member DNs
        List<String> memberDns = ldapTemplate.search(
                props.getGroupsBase(),
                "(&(objectclass=groupOfNames)(cn=" + groupName + "))",
                (AttributesMapper<String>) attributes -> {
                    Attribute memberAttr = attributes.get("member");
                    if (memberAttr != null) {
                        return memberAttr.get().toString();
                    }
                    return null;
                }
        );

        List<User> users = new ArrayList<>();
        for (String memberDn : memberDns) {
            // Extract username from DN and get user details
            if (memberDn.contains("uid=")) {
                String username = extractUsernameFromDn(memberDn);
                try {
                    users.add(getUser(username));
                } catch (Exception e) {
                    // Skip if user not found
                }
            }
        }
        return users;
    }

    public void addUserToGroup(String username, String groupName) {
        Name groupDn = LdapNameBuilder.newInstance(props.getGroupsBase())
                .add("cn", groupName)
                .build();

        Name userDn = LdapNameBuilder.newInstance(props.getUsersBase())
                .add("uid", username)
                .build();

        ModificationItem[] mods = new ModificationItem[]{
                new ModificationItem(DirContext.ADD_ATTRIBUTE,
                        new BasicAttribute("member", userDn.toString()))
        };

        ldapTemplate.modifyAttributes(groupDn, mods);
    }

    public void removeUserFromGroup(String username, String groupName) {
        Name groupDn = LdapNameBuilder.newInstance(props.getGroupsBase())
                .add("cn", groupName)
                .build();

        Name userDn = LdapNameBuilder.newInstance(props.getUsersBase())
                .add("uid", username)
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

}