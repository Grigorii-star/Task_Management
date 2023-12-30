package com.test.taskmanager.controller;

import com.test.taskmanager.dto.user.LoginDto;
import com.test.taskmanager.dto.user.RegisterDto;
import com.test.taskmanager.service.interf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody RegisterDto register) {
        if (userService.register(register)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto login) {
        userService.login(login);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
