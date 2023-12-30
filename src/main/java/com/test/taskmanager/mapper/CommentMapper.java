package com.test.taskmanager.mapper;

import com.test.taskmanager.dto.comment.CommentDTO;
import com.test.taskmanager.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "user.id", target = "authorId")
    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.lastName", target = "authorLastName")
    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "id", target = "commentId")
    CommentDTO commentToCommentDTO(Comment comment);

    List<CommentDTO> listCommentToListCommentDTO(List<Comment> commentList);

}
