package com.archiving.archivingTool.controller;

import com.archiving.archivingTool.config.JwtTokenProvider;
import com.archiving.archivingTool.dto.JwtAuthenticationResponse;
import com.archiving.archivingTool.dto.UserRequest;
import com.archiving.archivingTool.service.LdapService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final LdapService ldapService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider tokenProvider,
                          LdapService ldapService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.ldapService = ldapService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserRequest loginRequest) {
        try {
            // Authenticate user against LDAP
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Set authentication in security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            String jwt = tokenProvider.generateToken(authentication);

            // Return the token and user details
            return ResponseEntity.ok(new JwtAuthenticationResponse(
                    jwt,
                    authentication.getName(),
                    authentication.getAuthorities().stream()
                            .map(auth -> auth.getAuthority())
                            .toList()
            ));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401)
                    .body("Invalid username or password");
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body( "Internal server error");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest registrationRequest) {
        try {
            // Check if user already exists
            if (ldapService.userExists(registrationRequest.getUsername())) {
                return ResponseEntity.badRequest()
                        .body("Username already exists");
            }

            // Create user in LDAP
            ldapService.createUser(registrationRequest);

            return ResponseEntity.ok("User registered successfully");

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Error in server Side");
        }
    }

}