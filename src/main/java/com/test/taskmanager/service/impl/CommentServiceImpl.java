package com.test.taskmanager.service.impl;

import com.test.taskmanager.dto.comment.CommentDTO;
import com.test.taskmanager.dto.comment.CommentsDTO;
import com.test.taskmanager.dto.comment.CreateOrUpdateCommentDTO;
import com.test.taskmanager.entity.Comment;
import com.test.taskmanager.entity.Task;
import com.test.taskmanager.entity.User;
import com.test.taskmanager.exception.CommentNotFoundException;
import com.test.taskmanager.exception.TaskNotFoundException;
import com.test.taskmanager.exception.UserNotFoundException;
import com.test.taskmanager.mapper.CommentMapper;
import com.test.taskmanager.repository.CommentRepository;
import com.test.taskmanager.repository.TaskRepository;
import com.test.taskmanager.repository.UserRepository;
import com.test.taskmanager.service.interf.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDTO addComment(Long taskId, CreateOrUpdateCommentDTO properties, UserDetails userDetails) {
        Task task = findTask(taskId);
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Comment newComment = Comment.builder()
                .text(properties.getText())
                .user(user)
                .task(task)
                .build();
        commentRepository.save(newComment);

        log.info("In add comment - new comment successfully created.");
        return commentMapper.commentToCommentDTO(newComment);
    }

    @Override
    public CommentsDTO getComments(Long taskId) {
        Task task = findTask(taskId);
        List<Comment> commentList = (List<Comment>) task.getComments();
        Integer commentCount = commentList.size();

        log.info("In get comments - comments successfully got.");
        return CommentsDTO.builder()
                .count(commentCount)
                .comments(commentMapper.listCommentToListCommentDTO(commentList))
                .build();
    }

    @Override
    public CommentDTO updateComment(Long taskId, Long commentId, CreateOrUpdateCommentDTO properties) {
        Task task = findTask(taskId);
        Comment foundedComment = findComment(task, commentId);
        foundedComment.setText(properties.getText());
        commentRepository.save(foundedComment);

        log.info("In update comment - comment successfully updated.");
        return commentMapper.commentToCommentDTO(foundedComment);
    }

    @Override
    public void removeComment(Long taskId, Long commentId) {
        Task task = findTask(taskId);
        Comment foundedComment = findComment(task, commentId);
        commentRepository.delete(foundedComment);

        log.info("In remove comment - comment successfully deleted.");
    }

    private Task findTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    private Comment findComment(Task task, Long commentId) {
        return task.getComments().stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
    }

}
