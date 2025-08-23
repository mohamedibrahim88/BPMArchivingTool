package com.archiving.archivingTool.mapper;

import com.archiving.archivingTool.config.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserPrincipalConverter {

    public UserPrincipal convertFromLdapUserDetails(LdapUserDetails ldapUserDetails) {

        // Convert authorities from GrantedAuthority to List<String>
        List<String> authorities = ldapUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new UserPrincipal(ldapUserDetails.getUsername(),"",authorities);
    }

    public UserPrincipal convertFromAuthentication(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserPrincipal) {
            return (UserPrincipal) principal;
        } else if (principal instanceof LdapUserDetails) {
            return convertFromLdapUserDetails((LdapUserDetails) principal);
        } else {
            throw new IllegalArgumentException("Unsupported principal type: " + principal.getClass());
        }
    }
}
