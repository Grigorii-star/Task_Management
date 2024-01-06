package com.test.taskmanager.mapper;

import com.test.taskmanager.dto.user.PerformerDTO;
import com.test.taskmanager.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    PerformerDTO userToUserDto(User user);

}
