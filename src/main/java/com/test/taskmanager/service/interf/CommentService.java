package com.test.taskmanager.service.interf;

import com.test.taskmanager.dto.comment.CommentDTO;
import com.test.taskmanager.dto.comment.CommentsDTO;
import com.test.taskmanager.dto.comment.CreateOrUpdateCommentDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface CommentService {

    CommentDTO addComment(Long taskId, CreateOrUpdateCommentDTO properties, UserDetails userDetails);
    CommentsDTO getComments(Long taskId);
    CommentDTO updateComment(Long taskId, Long commentId, CreateOrUpdateCommentDTO properties);
    void removeComment(Long taskId, Long commentId);

}
