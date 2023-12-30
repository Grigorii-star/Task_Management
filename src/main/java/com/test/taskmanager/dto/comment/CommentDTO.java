package com.test.taskmanager.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Long authorId;
    private String authorFirstName;
    private String authorLastName;
    private Long taskId;
    private Long commentId;
    private String text;

}
