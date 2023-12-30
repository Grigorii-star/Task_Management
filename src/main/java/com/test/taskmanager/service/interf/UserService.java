package com.test.taskmanager.service.interf;

import com.test.taskmanager.dto.user.LoginDTO;
import com.test.taskmanager.dto.user.RegisterDTO;

public interface UserService {

    boolean register(RegisterDTO register);
    void login(LoginDTO login);

}
