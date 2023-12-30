package com.test.taskmanager.mapper;

import com.test.taskmanager.dto.task.TaskDTO;
import com.test.taskmanager.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "user.id", target = "authorId")
    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.lastName", target = "authorLastName")
    @Mapping(source = "id", target = "taskId")
    TaskDTO taskToTaskDTO(Task task);

    List<TaskDTO> listTaskToListTaskDTO(List<Task> taskList);

}
