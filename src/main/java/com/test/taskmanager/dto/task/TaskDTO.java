package com.test.taskmanager.dto.task;

import com.test.taskmanager.dto.comment.CommentDTO;
import com.test.taskmanager.dto.user.PerformerDTO;
import com.test.taskmanager.enums.Priority;
import com.test.taskmanager.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private Long authorId;
    private String authorFirstName;
    private String authorLastName;
    private Long taskId;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private List<CommentDTO> comments;
    private Set<PerformerDTO> performers;

}
