package com.test.taskmanager.service.impl;

import com.test.taskmanager.dto.user.LoginDto;
import com.test.taskmanager.dto.user.RegisterDto;
import com.test.taskmanager.entity.User;
import com.test.taskmanager.enums.Role;
import com.test.taskmanager.repository.UserRepository;
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
    @Override
    public boolean register(RegisterDto register) {
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
        log.info("In register - user: {} successfully registered.", savedUser);
        return true;
        } else {
            log.error("User with email {} already exist.", register.getEmail());
            return false;
        }
    }

    @Override
    public void login(LoginDto login) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getEmail(),
                        login.getPassword()
                )
        );
    }

}
