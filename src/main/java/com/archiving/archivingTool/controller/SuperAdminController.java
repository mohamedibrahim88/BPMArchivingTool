package com.archiving.archivingTool.controller;

import com.archiving.archivingTool.dto.CreateGroupRequest;
import com.archiving.archivingTool.dto.Group;
import com.archiving.archivingTool.dto.User;
import com.archiving.archivingTool.dto.UserRequest;
import com.archiving.archivingTool.service.LdapService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/super-admin")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class SuperAdminController {

    private final LdapService ldapService;

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
    public ResponseEntity<?> createUser(@RequestBody UserRequest request) {
        try {
            ldapService.createUser(request);
            return ResponseEntity.ok().body("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating user: " + e.getMessage());
        }
    }

    @DeleteMapping("/users/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        try {
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
            List<User> users = ldapService.getUsersInGroup(groupName);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/groups")
    public ResponseEntity<?> createGroup(@RequestBody CreateGroupRequest request) {
        try {
            ldapService.createGroup(request.getGroupName());
            return ResponseEntity.ok().body("Group created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating group: " + e.getMessage());
        }
    }

    @DeleteMapping("/groups/{groupName}")
    public ResponseEntity<?> deleteGroup(@PathVariable String groupName) {
        try {
            ldapService.deleteGroup(groupName);
            return ResponseEntity.ok().body("Group deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting group: " + e.getMessage());
        }
    }

    // Group Assignment Endpoints

    @PostMapping("/groups/{groupName}/users/{username}")
    public ResponseEntity<?> assignUserToGroup(@PathVariable String groupName,
                                               @PathVariable String username) {
        try {
            ldapService.addUserToGroup(username, groupName);
            return ResponseEntity.ok().body("User assigned to group successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error assigning user to group: " + e.getMessage());
        }
    }

    @DeleteMapping("/groups/{groupName}/users/{username}")
    public ResponseEntity<?> removeUserFromGroup(@PathVariable String groupName,
                                                 @PathVariable String username) {
        try {
            ldapService.removeUserFromGroup(username, groupName);
            return ResponseEntity.ok().body("User removed from group successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error removing user from group: " + e.getMessage());
        }
    }

    // Bulk Operations

    @PostMapping("/bulk/users")
    public ResponseEntity<?> createUsers(@RequestBody List<UserRequest> requests) {
        try {
            for (UserRequest request : requests) {
                ldapService.createUser(request);
            }
            return ResponseEntity.ok().body("Users created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating users: " + e.getMessage());
        }
    }

    @PostMapping("/bulk/groups")
    public ResponseEntity<?> createGroups(@RequestBody List<CreateGroupRequest> requests) {
        try {
            for (CreateGroupRequest request : requests) {
                ldapService.createGroup(request.getGroupName());
            }
            return ResponseEntity.ok().body("Groups created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating groups: " + e.getMessage());
        }
    }

}
