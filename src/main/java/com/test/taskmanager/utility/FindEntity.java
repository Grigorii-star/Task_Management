package com.test.taskmanager.utility;

import com.test.taskmanager.entity.Task;
import com.test.taskmanager.entity.User;
import com.test.taskmanager.exception.TaskNotFoundException;
import com.test.taskmanager.exception.UserNotFoundException;
import com.test.taskmanager.repository.TaskRepository;
import com.test.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindEntity {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public Task findTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

}
