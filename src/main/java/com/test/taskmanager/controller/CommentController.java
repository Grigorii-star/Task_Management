package com.test.taskmanager.controller;

import com.test.taskmanager.dto.comment.CommentDTO;
import com.test.taskmanager.dto.comment.CommentsDTO;
import com.test.taskmanager.dto.comment.CreateOrUpdateCommentDTO;
import com.test.taskmanager.service.interf.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
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
public class CommentController {

    private final CommentService commentService;

    @Operation(
            summary = "ADD NEW COMMENT",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Example of new comment field",
                    content = @Content(
                            schema = @Schema(implementation = CreateOrUpdateCommentDTO.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "text: Comment"
                                    )
                            }
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "New comment created successfully",
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
                            description = "Error creating a new comment",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Comment controller"
    )
    @PostMapping(value = "/{taskId}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Long taskId,
                                                 @RequestBody CreateOrUpdateCommentDTO properties,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(commentService.addComment(taskId, properties, userDetails), HttpStatus.CREATED);
    }

    @Operation(
            summary = "GET TASKS COMMENTS",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Tasks comments successfully got",
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
                            description = "Error receiving tasks comments",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Comment controller"
    )
    @GetMapping(value = "/{taskId}/comments")
    public ResponseEntity<CommentsDTO> getComments(@PathVariable Long taskId) {
        return new ResponseEntity<>(commentService.getComments(taskId), HttpStatus.OK);
    }

    @Operation(
            summary = "UPDATE COMMENT",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Example of field for an updated COMMENT",
                    content = @Content(
                            schema = @Schema(implementation = CreateOrUpdateCommentDTO.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "text: New comment"
                                    )
                            }
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Comment updated successfully",
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
                            description = "Comment update error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Comment controller"
    )
    @PatchMapping(value = "/{taskId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long taskId,
                                                    @PathVariable Long commentId,
                                                    @RequestBody CreateOrUpdateCommentDTO properties) {
        return new ResponseEntity<>(commentService.updateComment(taskId, commentId, properties), HttpStatus.OK);
    }

    @Operation(
            summary = "DELETE COMMENT",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "204",
                            description = "Comment successfully deleted",
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
                            description = "Comment delete error",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Comment controller"
    )
    @DeleteMapping(value = "/{taskId}/comments/{commentId}")
    public ResponseEntity<Void> removeComment(@PathVariable Long taskId,
                                              @PathVariable Long commentId) {
        commentService.removeComment(taskId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
