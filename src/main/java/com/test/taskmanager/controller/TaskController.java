package com.test.taskmanager.controller;

import com.test.taskmanager.dto.task.CreateOrUpdateTaskDTO;
import com.test.taskmanager.dto.task.TaskDTO;
import com.test.taskmanager.dto.task.TasksDTO;
import com.test.taskmanager.service.interf.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> addTask(@RequestBody CreateOrUpdateTaskDTO properties) {
        return new ResponseEntity<>(taskService.addTask(properties), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{taskId}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long taskId) {
        return new ResponseEntity<>(taskService.getTask(taskId), HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<TasksDTO> getTasks(@PathVariable Long userId) {
        return new ResponseEntity<>(taskService.getTasks(userId), HttpStatus.OK);
    }

    @PatchMapping(value = "/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long taskId,
                                              @RequestBody CreateOrUpdateTaskDTO properties) {
        return new ResponseEntity<>(taskService.updateTask(taskId, properties), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{taskId}")
    public ResponseEntity<Void> removeTask(@PathVariable Long taskId) {
        taskService.removeTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
