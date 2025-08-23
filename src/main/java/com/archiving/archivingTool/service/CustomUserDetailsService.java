package com.archiving.archivingTool.service;

import com.archiving.archivingTool.config.UserPrincipal;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final LdapTemplate ldapTemplate;

    public CustomUserDetailsService(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // Use explicit AttributesMapper to avoid ambiguity
            EqualsFilter filter = new EqualsFilter("uid", username);
            List<String> users = ldapTemplate.search(
                    LdapUtils.emptyLdapName(),
                    filter.encode(),
                    (Attributes attributes) -> {
                        return attributes.get("uid") != null ? attributes.get("uid").get().toString() : null;
                    }
            );

            if (users.isEmpty()) {
                throw new UsernameNotFoundException("User not found: " + username);
            }

            // For Spring Security, we return a UserDetails object
            return new UserPrincipal(
                    username,
                    "", // Password is handled by LDAP authentication
                    List.of("USER") // Default role
            );

        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found: " + username, e);
        }
    }
}