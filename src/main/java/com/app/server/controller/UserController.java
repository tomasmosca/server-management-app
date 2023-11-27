package com.app.server.controller;

import com.app.server.auth.Dtos.PasswordChangeRequest;
import com.app.server.auth.Dtos.PasswordResetRequest;
import com.app.server.model.User;
import com.app.server.service.SecurityService;
import com.app.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;

    @Autowired
    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/requestPasswordReset")
    public ResponseEntity<?> requestPasswordReset(@RequestBody PasswordResetRequest passwordResetRequest) {
        userService.initiatePasswordReset(passwordResetRequest.getEmail());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validateResetToken")
    public ResponseEntity<?> showChangePasswordPage(@RequestParam("token") String token) {
        String result = securityService.validatePasswordResetToken(token);
        String changePasswordPageUrl = "http://localhost:4200/reset-password";
        String loginPageUrl = "http://localhost:4200/login";
        if (result == null) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, changePasswordPageUrl + "?token=" + token)
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, loginPageUrl)
                    .build();
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestParam("token") String token,
                                            @RequestBody PasswordChangeRequest passwordChangeRequest) {
        boolean isChanged = securityService.changePassword(token, passwordChangeRequest);
        if (isChanged) {
            return ResponseEntity.ok("Password successfully changed");
        } else {
            return ResponseEntity.badRequest().body("Failed to change password");
        }
    }


}
