package com.test.taskmanager.controller;

import com.test.taskmanager.dto.task.TasksDTO;
import com.test.taskmanager.dto.user.PerformerDTO;
import com.test.taskmanager.service.interf.PerformerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
@SecurityRequirement(name = "taskmanagerapi")
public class PerformerController {

    private final PerformerService performerService;

    @Operation(
            summary = "ADD NEW PERFORMER",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "New performer created successfully",
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
                            description = "Error creating a new performer",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Performer controller"
    )
    @PostMapping(value = "/{taskId}/performers/{userId}")
    public ResponseEntity<PerformerDTO> addPerformer(@PathVariable Long taskId,
                                                     @PathVariable Long userId) {
        return new ResponseEntity<>(performerService.addPerformer(taskId, userId), HttpStatus.CREATED);
    }

    @Operation(
            summary = "GET PERFORMERS TASKS",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Performers tasks successfully got",
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
                            description = "Error receiving performers tasks",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Performer controller"
    )
    @GetMapping(value = "/performers")
    public ResponseEntity<TasksDTO> getPerformersTasks(@RequestParam String performerEmail) {
        return new ResponseEntity<>(performerService.getPerformersTasks(performerEmail), HttpStatus.OK);
    }

    @Operation(
            summary = "DELETE PERFORMER",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "204",
                            description = "Performer successfully deleted",
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
                            description = "Performer delete error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Performer controller"
    )
    @DeleteMapping(value = "/{taskId}/performers")
    public ResponseEntity<Void> removePerformer(@PathVariable Long taskId,
                                                @RequestParam String performerEmail) {
        performerService.removePerformer(taskId, performerEmail);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
