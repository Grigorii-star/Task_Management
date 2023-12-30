package com.test.taskmanager.dto.task;

import com.test.taskmanager.enums.Priority;
import com.test.taskmanager.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateTaskDTO {

    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private String performerFirstName;
    private String performerLastName;

}
