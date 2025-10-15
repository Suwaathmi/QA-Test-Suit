package com.learn.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody User newUser) {
        if (newUser.getFirstName() == null || newUser.getFirstName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("First name is required");
        }
        if (newUser.getLastName() == null || newUser.getLastName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Last name is required");
        }
        if (newUser.getPassword() == null || newUser.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Password is required");
        }
        if (newUser.getEmail() == null || newUser.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User " + newUser.getFirstName() + " " + newUser.getLastName() + " is added");
    }

    @PutMapping("/updateUser/{userId}/password")
    public ResponseEntity<String> changePassword(
            @PathVariable("userId") String userId,
            @RequestBody Map<String, String> body) {

        String currentPassword = body.getOrDefault("currentPassword", "");
        String newPassword = body.getOrDefault("newPassword", "");

        return userRepository.findById(userId)
                .map(user -> {
                    if (currentPassword.isBlank() || newPassword.isBlank()) {
                        return ResponseEntity.badRequest().body("currentPassword and newPassword are required");
                    }

                    if (!user.getPassword().equals(currentPassword)) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Current password is incorrect");
                    }

                    user.setPassword(newPassword);
                    userRepository.save(user);
                    return ResponseEntity.ok("Password updated successfully");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User " + userId + " not found"));
    }

    @DeleteMapping("/removeUser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    userRepository.deleteById(userId);
                    return ResponseEntity.ok("User " + user.getFirstName() + " " + user.getLastName() + " is deleted");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User " + userId + " not found"));
    }
}
