package com.test.taskmanager.service.impl;

import com.test.taskmanager.dto.task.CreateOrUpdateTaskDTO;
import com.test.taskmanager.dto.task.TaskDTO;
import com.test.taskmanager.dto.task.TasksDTO;
import com.test.taskmanager.entity.Task;
import com.test.taskmanager.entity.User;
import com.test.taskmanager.exception.TaskNotFoundException;
import com.test.taskmanager.exception.UserNotFoundException;
import com.test.taskmanager.mapper.TaskMapper;
import com.test.taskmanager.repository.TaskRepository;
import com.test.taskmanager.repository.UserRepository;
import com.test.taskmanager.service.interf.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    @Override
    public TaskDTO addTask(CreateOrUpdateTaskDTO properties, UserDetails userDetails) {
        Task newTask = taskMapper.createOrUpdateTaskDtoToTask(properties);
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        newTask.setUser(user);
        Task task = taskRepository.save(newTask);

        if (properties.getPerformerId() != null) {
            User taskPerformer = findUser(properties.getPerformerId());
            taskPerformer.setTask(newTask);
            userRepository.save(taskPerformer);
        }

        log.info("In add task - new task successfully created.");
        return taskMapper.taskToTaskDTO(taskRepository.getReferenceById(task.getId()));
    }

    @Override
    public TaskDTO getTask(Long taskId) {
        Task task = findTask(taskId);

        log.info("In get task - task with id {}, successfully founded.", taskId);
        return taskMapper.taskToTaskDTO(task);
    }

    @Override
    public TasksDTO getTasks(Long userId) {
        User user = findUser(userId);
        List<Task> taskList = (List<Task>) user.getTasks();
        Integer tasksCount = taskList.size();

        log.info("In get tasks - tasks successfully got.");
        return TasksDTO.builder()
                .count(tasksCount)
                .tasks(taskMapper.listTaskToListTaskDTO(taskList))
                .build();
    }

    @Override
    public TaskDTO updateTask(Long taskId, CreateOrUpdateTaskDTO properties) {
        Task task = findTask(taskId);
        Task newTask = taskMapper.createOrUpdateTaskDtoToTask(properties);
        newTask.setId(task.getId());
        newTask.setUser(task.getUser());
        Task updatedTask = taskRepository.save(newTask);

        log.info("In update task - task successfully updated.");
        return taskMapper.taskToTaskDTO(updatedTask);
    }

    @Override
    public void removeTask(Long taskId) {
        Task task = findTask(taskId);
        taskRepository.delete(task);

        log.info("In remove task - task successfully deleted.");
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
    private Task findTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }
}
