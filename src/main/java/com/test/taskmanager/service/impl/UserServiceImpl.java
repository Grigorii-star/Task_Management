package com.test.taskmanager.service.impl;

import com.test.taskmanager.dto.AuthenticationResponse;
import com.test.taskmanager.dto.user.LoginDTO;
import com.test.taskmanager.dto.user.RegisterDTO;
import com.test.taskmanager.entity.Token;
import com.test.taskmanager.entity.User;
import com.test.taskmanager.enums.Role;
import com.test.taskmanager.enums.TokenType;
import com.test.taskmanager.exception.UserAlreadyExistException;
import com.test.taskmanager.repository.TokenRepository;
import com.test.taskmanager.repository.UserRepository;
import com.test.taskmanager.security.JwtService;
import com.test.taskmanager.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Override
    public AuthenticationResponse register(RegisterDTO register) {
        Optional<User> foundedUser = userRepository.findByEmail(register.getEmail());

        if (foundedUser.isEmpty()) {
        User newUser = User.builder()
                .firstName(register.getFirstName())
                .lastName(register.getLastName())
                .email(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                .role(Role.USER)
                .build();
        User savedUser = userRepository.save(newUser);
        String jwtToken = jwtService.generateToken(newUser);
        saveUserToken(savedUser, jwtToken);

        log.info("In register - user: {} successfully registered.", savedUser);
        return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .build();
        } else {
            throw new UserAlreadyExistException("User already exist");
        }
    }

    @Override
    public AuthenticationResponse login(LoginDTO login) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getEmail(),
                        login.getPassword()
                )
        );

        User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        log.info("In login - user with email: {} successfully authenticated.", login.getEmail());
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

}
