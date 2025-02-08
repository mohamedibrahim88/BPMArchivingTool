package com.archiving.archivingTool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class LdapAuthService {
    private final LdapTemplate ldapTemplate;


    public boolean authenticate(String username, String password) {
        try{
            return ldapTemplate.authenticate("", "(uid="+username+")", password);
        }catch (Exception e){
            return false;
        }
    }
    public UserDetails loadUserByUsername(String username) {
        return ldapTemplate.search("", "(uid="+username+")",new UserAttributesMapper())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("UserNotFound")); //todo : create custom Exception
    }

    private static class UserAttributesMapper implements AttributesMapper<UserDetails> {
        @Override
        public UserDetails mapFromAttributes(Attributes attributes) throws NamingException {
            String userName = (String) attributes.get("uid").get();
            String fullName = (String) attributes.get("cn").get();
            String role = "DEF_USER";

            //todo add it configuration
            if(attributes.get("memberOf")!=null){
                String memberOf = (String) attributes.get("memberOf").get();
                role = extractRoleFromDN(memberOf);
            }

            return User.withUsername(userName)
                    .password("")
                    .roles(role)
                    .build();
        }
        private String extractRoleFromDN(String memberOf) {
            Pattern pattern = Pattern.compile("cn=([ˆ,]+)");
            Matcher matcher = pattern.matcher(memberOf);
            if(matcher.find()){
                return matcher.group(1).toUpperCase();
            }else{
                return "DEF_USER";
            }
        }
    }

}
