package com.test.taskmanager.service.interf;

import com.test.taskmanager.dto.comment.CommentDTO;
import com.test.taskmanager.dto.comment.CommentsDTO;
import com.test.taskmanager.dto.comment.CreateOrUpdateCommentDTO;

public interface CommentService {

    CommentDTO addComment(Long taskId, CreateOrUpdateCommentDTO properties);
    CommentsDTO getComments(Long taskId);
    CommentDTO updateComment(Long taskId, Long commentId, CreateOrUpdateCommentDTO properties);
    void removeComment(Long taskId, Long commentId);

}
