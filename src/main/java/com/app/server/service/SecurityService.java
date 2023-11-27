package com.app.server.service;

import com.app.server.auth.Dtos.PasswordChangeRequest;
import com.app.server.model.PasswordResetToken;
import com.app.server.model.User;
import com.app.server.repository.PasswordResetTokenRepository;
import com.app.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class SecurityService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Autowired
    public SecurityService(PasswordResetTokenRepository passwordResetTokenRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token).orElse(null);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    public boolean changePassword(String token, PasswordChangeRequest passwordChangeRequest) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElse(null);

        if (resetToken == null || isTokenExpired(resetToken)) {
            return false;
        }

        User user = resetToken.getUser();
        String encryptedPassword = passwordEncoder.encode(passwordChangeRequest.getNewPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        passwordResetTokenRepository.delete(resetToken);

        return true;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

}
