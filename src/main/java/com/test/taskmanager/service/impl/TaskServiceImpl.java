package com.test.taskmanager.service.impl;

import com.test.taskmanager.dto.task.CreateOrUpdateTaskDTO;
import com.test.taskmanager.dto.task.TaskDTO;
import com.test.taskmanager.dto.task.TasksDTO;
import com.test.taskmanager.service.interf.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Override
    public TaskDTO addTask(CreateOrUpdateTaskDTO properties) {
        return null;
    }

    @Override
    public TaskDTO getTask(Long taskId) {
        return null;
    }

    @Override
    public TasksDTO getTasks(Long userId) {
        return null;
    }

    @Override
    public TaskDTO updateTask(Long taskId, CreateOrUpdateTaskDTO properties) {
        return null;
    }

    @Override
    public void removeTask(Long taskId) {

    }

}
