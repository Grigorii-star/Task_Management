package com.test.taskmanager.dto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TasksDTO {

    private Long count;
    private List<TaskDTO> tasks;

}
