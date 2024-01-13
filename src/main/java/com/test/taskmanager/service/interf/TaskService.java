package com.test.taskmanager.service.interf;

import com.test.taskmanager.dto.task.CreateOrUpdateTaskDTO;
import com.test.taskmanager.dto.task.TaskDTO;
import com.test.taskmanager.dto.task.TasksDTO;
import com.test.taskmanager.enums.Status;
import org.springframework.security.core.userdetails.UserDetails;

public interface TaskService {

    TaskDTO addTask(CreateOrUpdateTaskDTO properties, Long performerId, UserDetails userDetails);
    TaskDTO getTask(Long taskId);
    TasksDTO getTasks(Long userId);
    TaskDTO updateTask(Long taskId, CreateOrUpdateTaskDTO properties);
    TaskDTO updateStatus(Long taskId, Status status);
    void removeTask(Long taskId);

}
