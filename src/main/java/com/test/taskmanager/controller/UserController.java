package com.test.taskmanager.controller;

import com.test.taskmanager.dto.AuthenticationResponse;
import com.test.taskmanager.dto.user.LoginDTO;
import com.test.taskmanager.dto.user.RegisterDTO;
import com.test.taskmanager.service.interf.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "REGISTRATION",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Register fields examples",
                    content = @Content(
                            schema = @Schema(implementation = RegisterDTO.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "firstName: Grigorii"
                                    ),
                                    @ExampleObject(
                                            name = "lastName: Rarog"
                                    ),
                                    @ExampleObject(
                                            name = "email: grigorii@gmail.com"
                                    ),
                                    @ExampleObject(
                                            name = "password: password"
                                    )
                            }
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Registration completed successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Incorrect data provided",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Registration error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "User controller"
    )
    @PostMapping(value = "/register")
    public ResponseEntity<AuthenticationResponse> register (@RequestBody RegisterDTO register) {
        return new ResponseEntity<>(userService.register(register), HttpStatus.CREATED);
    }

    @Operation(
            summary = "AUTHENTICATION",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Authentication fields examples",
                    content = @Content(
                            schema = @Schema(implementation = LoginDTO.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "email: grigorii@gmail.com"
                                    ),
                                    @ExampleObject(
                                            name = "password: password"
                                    )
                            }
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Authentication completed successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Incorrect data provided",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Authentication error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "User controller"
    )
    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginDTO login) {
        return new ResponseEntity<>(userService.login(login), HttpStatus.OK);
    }

}
