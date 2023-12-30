package com.test.taskmanager.service.interf;

import com.test.taskmanager.dto.user.LoginDto;
import com.test.taskmanager.dto.user.RegisterDto;

public interface UserService {

    boolean register(RegisterDto register);
    void login(LoginDto login);

}
