package com.archiving.archivingTool.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.ldap")
public class AppLdapProps {
    private String url;
    private String base;
    private String userDn;
    private String password;
    private String usersBase;
    private String groupsBase;
    private String groupSearchFilter = "(member={0})";

    // Getters and setters
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getBase() { return base; }
    public void setBase(String base) { this.base = base; }

    public String getUserDn() { return userDn; }
    public void setUserDn(String userDn) { this.userDn = userDn; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUsersBase() { return usersBase; }
    public void setUsersBase(String usersBase) { this.usersBase = usersBase; }

    public String getGroupsBase() { return groupsBase; }
    public void setGroupsBase(String groupsBase) { this.groupsBase = groupsBase; }

    public String getGroupSearchFilter() { return groupSearchFilter; }
    public void setGroupSearchFilter(String groupSearchFilter) { this.groupSearchFilter = groupSearchFilter; }
}
