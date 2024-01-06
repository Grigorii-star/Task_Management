package com.test.taskmanager.controller;

import com.test.taskmanager.dto.comment.CommentDTO;
import com.test.taskmanager.dto.comment.CommentsDTO;
import com.test.taskmanager.dto.comment.CreateOrUpdateCommentDTO;
import com.test.taskmanager.service.interf.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{taskId}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Long taskId,
                                                 @RequestBody CreateOrUpdateCommentDTO properties,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(commentService.addComment(taskId, properties, userDetails), HttpStatus.CREATED);
    }

    @GetMapping("/{taskId}/comments")
    public ResponseEntity<CommentsDTO> getComments(@PathVariable Long taskId) {
        return new ResponseEntity<>(commentService.getComments(taskId), HttpStatus.OK);
    }

    @PatchMapping("/{taskId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long taskId,
                                                    @PathVariable Long commentId,
                                                    @RequestBody CreateOrUpdateCommentDTO properties) {
        return new ResponseEntity<>(commentService.updateComment(taskId, commentId, properties), HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}/comments/{commentId}")
    public ResponseEntity<Void> removeComment(@PathVariable Long taskId,
                                              @PathVariable Long commentId) {
        commentService.removeComment(taskId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
