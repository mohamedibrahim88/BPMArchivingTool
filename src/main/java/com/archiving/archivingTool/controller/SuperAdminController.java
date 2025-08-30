package com.archiving.archivingTool.controller;

import com.archiving.archivingTool.dto.CreateGroupRequest;
import com.archiving.archivingTool.dto.Group;
import com.archiving.archivingTool.dto.User;
import com.archiving.archivingTool.dto.UserRequest;
import com.archiving.archivingTool.model.SystemRole;
import com.archiving.archivingTool.service.LdapService;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/super-admin")
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
public class SuperAdminController {

    private final LdapService ldapService;
    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    public SuperAdminController(LdapService ldapService) {
        this.ldapService = ldapService;
    }

    // User Management Endpoints

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = ldapService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        try {
            User user = ldapService.getUser(username);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody UserRequest request) {
        try {
            // Check if user already exists
            if (ldapService.userExists(request.getUsername())) {
                return ResponseEntity.badRequest()
                        .body("User '" + request.getUsername() + "' already exists");
            }

            ldapService.createUser(request);
            return ResponseEntity.ok().body("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating user: " + e.getMessage());
        }
    }

    @PutMapping("/users/{username}/password")
    public ResponseEntity<String> updateUserPassword(@PathVariable String username,
                                                @RequestBody String newPassword) {
        try {
            // Manual validation
            if (newPassword == null || newPassword.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Password cannot be empty");
            }

            if (newPassword.length() < 8) {
                return ResponseEntity.badRequest().body("Password must be at least 8 characters long");
            }

            // Check if user exists
            if (!ldapService.userExists(username)) {
                return ResponseEntity.notFound().build();
            }

            ldapService.updateUserPassword(username, newPassword);
            return ResponseEntity.ok().body("Password updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating password: " + e.getMessage());
        }
    }

    @DeleteMapping("/users/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        try {
            // Check if user exists
            if (!ldapService.userExists(username)) {
                return ResponseEntity.notFound().build();
            }

            ldapService.deleteUser(username);
            return ResponseEntity.ok().body("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting user: " + e.getMessage());
        }
    }

    // Group Management Endpoints

    @GetMapping("/groups")
    public ResponseEntity<List<Group>> getAllGroups() {
        try {
            List<Group> groups = ldapService.getAllGroups();
            return ResponseEntity.ok(groups);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/groups/{groupName}/users")
    public ResponseEntity<List<User>> getUsersBasedOnGroup(@PathVariable String groupName) {
        try {
            // Check if group exists
            if (!ldapService.groupExists(groupName)) {
                return ResponseEntity.notFound().build();
            }

            List<User> users = ldapService.getUsersInGroup(groupName);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/groups")
    public ResponseEntity<String> createGroup(@RequestBody CreateGroupRequest request) {
        try {
            // Check if group already exists
            if (ldapService.groupExists(request.getName())) {
                return ResponseEntity.badRequest()
                        .body("Group '" + request.getName() + "' already exists");
            }

            ldapService.createGroup(request.getName());
            return ResponseEntity.ok().body("Group created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating group: " + e.getMessage());
        }
    }

    @DeleteMapping("/groups/{groupName}")
    public ResponseEntity<String> deleteGroup(@PathVariable String groupName) {
        try {
            // Check if group exists
            if (!ldapService.groupExists(groupName)) {
                return ResponseEntity.notFound().build();
            }

            if(SystemRole.SUPER_ADMIN.getValue().equals(groupName)){
                return ResponseEntity.badRequest().body("Can not delete Super Admin Role");
            }

            ldapService.deleteGroup(groupName);
            return ResponseEntity.ok().body("Group deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting group: " + e.getMessage());
        }
    }

    // Group Assignment Endpoints

    @PostMapping("/groups/{groupName}/users/{username}")
    public ResponseEntity<String> assignUserToGroup(@PathVariable String groupName,
                                               @PathVariable String username) {
        try {
            // Check if user exists
            if (!ldapService.userExists(username)) {
                return ResponseEntity.badRequest().body("User '" + username + "' does not exist");
            }

            // Check if group exists
            if (!ldapService.groupExists(groupName)) {
                return ResponseEntity.badRequest().body("Group '" + groupName + "' does not exist");
            }

            // Check if user is already in group
            if (ldapService.isUserInGroup(username, groupName)) {
                return ResponseEntity.badRequest()
                        .body("User '" + username + "' is already in group '" + groupName + "'");
            }

            ldapService.addUserToGroup(username, groupName);
            return ResponseEntity.ok().body("User assigned to group successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error assigning user to group: " + e.getMessage());
        }
    }

    @DeleteMapping("/groups/{groupName}/users/{username}")
    public ResponseEntity<String> removeUserFromGroup(@PathVariable String groupName,
                                                 @PathVariable String username) {
        try {
            // Check if user exists
            if (!ldapService.userExists(username)) {
                return ResponseEntity.badRequest().body("User '" + username + "' does not exist");
            }

            // Check if group exists
            if (!ldapService.groupExists(groupName)) {
                return ResponseEntity.badRequest().body("Group '" + groupName + "' does not exist");
            }

            // Check if user is actually in the group
            if (!ldapService.isUserInGroup(username, groupName)) {
                return ResponseEntity.badRequest()
                        .body("User '" + username + "' is not in group '" + groupName + "'");
            }

            ldapService.removeUserFromGroup(username, groupName);
            return ResponseEntity.ok().body("User removed from group successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error removing user from group: " + e.getMessage());
        }
    }
    @GetMapping("/users/{username}/groups")
    public ResponseEntity<List<Group>> getGroupsForUser(@PathVariable String username) {
        try {
            // Check if user exists
            if (!ldapService.userExists(username)) {
                return ResponseEntity.notFound().build();
            }

            List<Group> groups = ldapService.getGroupsForUser(username);
            return ResponseEntity.ok(groups);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Bulk Operations

    @PostMapping("/bulk/users")
    public ResponseEntity<String> createUsers(@RequestBody List<UserRequest> requests) {
        try {
            StringBuilder results = new StringBuilder();

            for (UserRequest request : requests) {
                try {
                    if (ldapService.userExists(request.getUsername())) {
                        results.append("User '").append(request.getUsername()).append("' already exists. ");
                        continue;
                    }

                    ldapService.createUser(request);
                    results.append("User '").append(request.getUsername()).append("' created successfully. ");
                } catch (Exception e) {
                    results.append("Error creating user '").append(request.getUsername())
                            .append("': ").append(e.getMessage()).append(". ");
                }
            }

            return ResponseEntity.ok().body(results.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing bulk user creation: " + e.getMessage());
        }
    }

    @PostMapping("/bulk/groups")
    public ResponseEntity<String> createGroups(@RequestBody List<CreateGroupRequest> requests) {
        try {
            StringBuilder results = new StringBuilder();

            for (CreateGroupRequest request : requests) {
                try {
                    if (ldapService.groupExists(request.getName())) {
                        results.append("Group '").append(request.getName()).append("' already exists. ");
                        continue;
                    }

                    ldapService.createGroup(request.getName());
                    results.append("Group '").append(request.getName()).append("' created successfully. ");
                } catch (Exception e) {
                    results.append("Error creating group '").append(request.getName())
                            .append("': ").append(e.getMessage()).append(". ");
                }
            }

            return ResponseEntity.ok().body(results.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing bulk group creation: " + e.getMessage());
        }
    }

    public boolean isValid(String password) {
        if (password == null) {
            return false;
        }
        return Pattern.matches(PASSWORD_PATTERN, password);
    }
}
