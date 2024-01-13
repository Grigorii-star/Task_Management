package com.test.taskmanager.service.impl;

import com.test.taskmanager.dto.task.CreateOrUpdateTaskDTO;
import com.test.taskmanager.dto.task.TaskDTO;
import com.test.taskmanager.entity.Performer;
import com.test.taskmanager.entity.Task;
import com.test.taskmanager.entity.User;
import com.test.taskmanager.enums.Status;
import com.test.taskmanager.exception.TaskNotFoundException;
import com.test.taskmanager.exception.UserNotFoundException;
import com.test.taskmanager.mapper.TaskMapper;
import com.test.taskmanager.repository.TaskRepository;
import com.test.taskmanager.repository.UserRepository;
import com.test.taskmanager.service.interf.PerformerService;
import com.test.taskmanager.service.interf.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final PerformerService performerService;

    @Override
    public TaskDTO addTask(CreateOrUpdateTaskDTO properties, Long performerId, UserDetails userDetails) {
        Task newTask = taskMapper.createOrUpdateTaskDtoToTask(properties);
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        newTask.setUser(user);
        Task savedTask = taskRepository.save(newTask);

        if (performerId != null) {
            List<Performer> performerList = performerService.addPerformerToNewTask(
                    savedTask,
                    performerId
            );
            savedTask.setPerformers(performerList);
            taskRepository.save(savedTask);
        }

        log.info("In add task - new task successfully created.");
        return taskMapper.taskToTaskDTO(savedTask);
    }

    @Override
    public TaskDTO getTask(Long taskId) {
        Task task = findTask(taskId);

        log.info("In get task - task with id {}, successfully founded.", taskId);
        return taskMapper.taskToTaskDTO(task);
    }

    @Override
    public Page<TaskDTO> getTasks(Long userId, Integer pageNumber, Integer pageSize) {
        User user = findUser(userId);
        List<Task> taskList = (List<Task>) user.getTasks();

        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        List<TaskDTO> allTasks = taskMapper.listTaskToListTaskDTO(taskList);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), allTasks.size());
        List<TaskDTO> pageContent = allTasks.subList(start, end);

        log.info("In get tasks - tasks successfully got.");
        return new PageImpl<>(pageContent, pageRequest, allTasks.size());
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
    public TaskDTO updateStatus(Long taskId, Status status) {
        Task task = findTask(taskId);
        task.setStatus(status);
        Task savedTask = taskRepository.save(task);

        log.info("In update status - status successfully updated.");
        return taskMapper.taskToTaskDTO(savedTask);
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
