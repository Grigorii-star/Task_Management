package com.test.taskmanager.service.impl;

import com.test.taskmanager.dto.comment.CommentDTO;
import com.test.taskmanager.dto.comment.CommentsDTO;
import com.test.taskmanager.dto.comment.CreateOrUpdateCommentDTO;
import com.test.taskmanager.service.interf.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Override
    public CommentDTO addComment(Long taskId, CreateOrUpdateCommentDTO properties) {
        return null;
    }

    @Override
    public CommentsDTO getComments(Long taskId) {
        return null;
    }

    @Override
    public CommentDTO updateComment(Long taskId, Long commentId, CreateOrUpdateCommentDTO properties) {
        return null;
    }

    @Override
    public void removeComment(Long taskId, Long commentId) {

    }

}
