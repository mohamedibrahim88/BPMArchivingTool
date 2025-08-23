package com.archiving.archivingTool.config;

import com.archiving.archivingTool.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public LdapContextSource contextSource(AppLdapProps props) {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(props.getUrl());
        contextSource.setBase(props.getBase());
        contextSource.setUserDn(props.getUserDn());
        contextSource.setPassword(props.getPassword());
        return contextSource;
    }

    @Bean
    public LdapAuthoritiesPopulator ldapAuthoritiesPopulator(LdapContextSource contextSource, AppLdapProps props) {
        DefaultLdapAuthoritiesPopulator authoritiesPopulator =
                new DefaultLdapAuthoritiesPopulator(contextSource, props.getGroupsBase());
        authoritiesPopulator.setGroupSearchFilter(props.getGroupSearchFilter());
        authoritiesPopulator.setSearchSubtree(true);
        authoritiesPopulator.setGroupRoleAttribute("cn");
        return authoritiesPopulator;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       LdapContextSource contextSource,
                                                       LdapAuthoritiesPopulator authoritiesPopulator,
                                                       AppLdapProps props) throws Exception {

        var authorityMapper = new SimpleAuthorityMapper();
        authorityMapper.setPrefix("ROLE_");
        authorityMapper.setConvertToUpperCase(true);

        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder.ldapAuthentication()
                .userDnPatterns("uid={0}," + props.getUsersBase())
                .contextSource(contextSource)
                .ldapAuthoritiesPopulator(authoritiesPopulator)
                .authoritiesMapper(authorityMapper);

        return authBuilder.build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            JwtTokenProvider tokenProvider,
                                            CustomUserDetailsService userDetailsService,
                                            SuperAdminFilter superAdminFilter) throws Exception {

        http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/api/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/webjars/**",
                                "/swagger-resources/**")
                        .permitAll()  // Allow public access to auth endpoints
                        // Use custom access decision for super-admin endpoints
                        .anyRequest().authenticated()
                )
                // JwtAuthenticationFilter runs FIRST to set up authentication
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                // SuperAdminFilter runs SECOND to check roles (after authentication is set)
                .addFilterAfter(superAdminFilter, JwtAuthenticationFilter.class);


        return http.build();
    }
}