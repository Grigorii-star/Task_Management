package com.test.taskmanager.service.interf;

import com.test.taskmanager.dto.AuthenticationResponse;
import com.test.taskmanager.dto.user.LoginDTO;
import com.test.taskmanager.dto.user.RegisterDTO;

public interface UserService {

    AuthenticationResponse register(RegisterDTO register);
    AuthenticationResponse login(LoginDTO login);

}
