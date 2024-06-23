package com.himanshu.NovelNet.auth;

import com.himanshu.NovelNet.email.EmailServie;
import com.himanshu.NovelNet.email.EmailTemplateName;
import com.himanshu.NovelNet.role.Role;
import com.himanshu.NovelNet.role.RoleRepository;
import com.himanshu.NovelNet.security.JwtService;
import com.himanshu.NovelNet.user.Token;
import com.himanshu.NovelNet.user.TokenRepository;
import com.himanshu.NovelNet.user.User;
import com.himanshu.NovelNet.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailServie emailServie;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${application.mailing.frontend.activation-url}")
    private String confirmationUrl;

    public void registerUser(RegistrationRequest request) throws MessagingException {
        /*
         * Assign role by default
         * create user object and save
         * send a validation email and using email template
         * */
        Role userRole = roleRepository.findByName("USER").orElseThrow(() -> new IllegalStateException("ROLE USER was not initilized"));

        User user = User.builder().firstName(request.getFirstname()).lastName(request.getLastname()).email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).accountLocked(false).roles(List.of(userRole)).accountEnabled(false).build();

        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        String activationToken = generateAndSaveActivationToken(user);
        emailServie.sendEmail(user.getEmail(), user.getFullName(), EmailTemplateName.ACTIVATE_ACCOUNT, confirmationUrl, activationToken, "ACTIVATE Your NovelNet Account");
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(6);
        Token token = Token.builder().token(generatedToken).createdAt(LocalDateTime.now()).expiresAt(LocalDateTime.now().plusMinutes(15L)).user(user).build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {

        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int secureInt = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(secureInt));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),
                        request.getPassword())
        );

        HashMap<String, Object> claims = new HashMap<String, Object>();
        User user = (User) authenticate.getPrincipal();
        claims.put("fullName", user.getFullName());
        String token = jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder().token(token).build();

    }

    public void activateAccount(String token) throws MessagingException {
        Token userToekn = tokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid token"));
        if (LocalDateTime.now().isAfter(userToekn.getExpiresAt())) {
            sendValidationEmail(userToekn.getUser());
            throw new RuntimeException("Activation token has expired, sent you the new one!!");
        }
        User user = userRepository.findById(userToekn.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
        user.setAccountEnabled(true);
        userRepository.save(user);

        userToekn.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(userToekn);


    }
}
