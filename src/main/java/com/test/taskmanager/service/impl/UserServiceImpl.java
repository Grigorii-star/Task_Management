package com.test.taskmanager.service.impl;

import com.test.taskmanager.dto.user.LoginDTO;
import com.test.taskmanager.dto.user.RegisterDTO;
import com.test.taskmanager.entity.User;
import com.test.taskmanager.enums.Role;
import com.test.taskmanager.repository.UserRepository;
import com.test.taskmanager.security.JwtService;
import com.test.taskmanager.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Override
    public boolean register(RegisterDTO register) {
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
        log.info("Register jwt token: {}", jwtToken);
        log.info("In register - user: {} successfully registered.", savedUser);
        return true;
        } else {
            log.error("User with email {} already exist.", register.getEmail());
            return false;
        }
    }

    @Override
    public void login(LoginDTO login) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getEmail(),
                        login.getPassword()
                )
        );

        User user = userRepository.findByEmail(login.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        log.info("Login jwt token: {}", jwtToken);
    }

}
