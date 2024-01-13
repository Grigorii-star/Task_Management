package com.test.taskmanager.controller;

import com.test.taskmanager.dto.task.CreateOrUpdateTaskDTO;
import com.test.taskmanager.dto.task.TaskDTO;
import com.test.taskmanager.enums.Status;
import com.test.taskmanager.service.interf.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
@SecurityRequirement(name = "taskmanagerapi")
public class TaskController {

    private final TaskService taskService;

    @Operation(
            summary = "ADD NEW TASK",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Examples of new task fields",
                    content = @Content(
                            schema = @Schema(implementation = CreateOrUpdateTaskDTO.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "title: Task 1"
                                    ),
                                    @ExampleObject(
                                            name = "description: Description"
                                    ),
                                    @ExampleObject(
                                            name = "status: IN_PROGRESS"
                                    ),
                                    @ExampleObject(
                                            name = "priority: AVERAGE"
                                    ),
                                    @ExampleObject(
                                            name = "performerId: 1"
                                    )
                            }
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "New task created successfully",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "Authorisation Error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Incorrect data provided",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Error creating a new task",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Task controller"
    )
    @PostMapping(value = "/{performerId}")
    public ResponseEntity<TaskDTO> addTask(@RequestBody CreateOrUpdateTaskDTO properties,
                                           @PathVariable Long performerId,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(taskService.addTask(properties, performerId, userDetails), HttpStatus.CREATED);
    }

    @Operation(
            summary = "GET TASK",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Task successfully got",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "Authorisation Error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Incorrect data provided",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Task receiving error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Task controller"
    )
    @GetMapping(value = "/{taskId}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long taskId) {
        return new ResponseEntity<>(taskService.getTask(taskId), HttpStatus.OK);
    }

    @Operation(
            summary = "GET USERS TASKS",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Users tasks successfully got",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "Authorisation Error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Incorrect data provided",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Error receiving users tasks",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Task controller"
    )
    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<Page<TaskDTO>> getTasks(@PathVariable Long userId,
                                                  @RequestParam (defaultValue = "0") Integer page,
                                                  @RequestParam (defaultValue = "5") Integer size) {
        Page<TaskDTO> taskDTOPage = taskService.getTasks(userId, page, size);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Page-Number", String.valueOf(taskDTOPage.getNumber()));
        headers.add("X-Page-Size", String.valueOf(taskDTOPage.getSize()));

        return ResponseEntity.ok()
                .headers(headers)
                .body(taskDTOPage);
    }

    @Operation(
            summary = "UPDATE TASK",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Examples of fields for an updated task",
                    content = @Content(
                            schema = @Schema(implementation = CreateOrUpdateTaskDTO.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "title: New title"
                                    ),
                                    @ExampleObject(
                                            name = "description: New description"
                                    ),
                                    @ExampleObject(
                                            name = "status: COMPLETED"
                                    ),
                                    @ExampleObject(
                                            name = "priority: LOW"
                                    ),
                                    @ExampleObject(
                                            name = "performerId: 1"
                                    )
                            }
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Task successfully updated",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "Authorisation Error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Incorrect data provided",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Task update error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Task controller"
    )
    @PatchMapping(value = "/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long taskId,
                                              @RequestBody CreateOrUpdateTaskDTO properties) {
        return new ResponseEntity<>(taskService.updateTask(taskId, properties), HttpStatus.OK);
    }

    @Operation(
            summary = "UPDATE STATUS",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Examples of field for an updated status",
                    content = @Content(
                            schema = @Schema(implementation = Status.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "PENDING"
                                    ),
                                    @ExampleObject(
                                            name = "IN_PROGRESS"
                                    ),
                                    @ExampleObject(
                                            name = "COMPLETED"
                                    )
                            }
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Status successfully updated",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "Authorisation Error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Incorrect data provided",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Status update error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Task controller"
    )
    @PatchMapping(value = "/{taskId}/status")
    public ResponseEntity<TaskDTO> updateStatus(@PathVariable Long taskId,
                                                @RequestBody Status status) {
        return new ResponseEntity<>(taskService.updateStatus(taskId, status), HttpStatus.OK);
    }

    @Operation(
            summary = "DELETE TASK",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "204",
                            description = "Task successfully deleted",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "403",
                            description = "Authorisation Error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Incorrect data provided",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Task delete error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Task controller"
    )
    @DeleteMapping(value = "/{taskId}")
    public ResponseEntity<Void> removeTask(@PathVariable Long taskId) {
        taskService.removeTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
