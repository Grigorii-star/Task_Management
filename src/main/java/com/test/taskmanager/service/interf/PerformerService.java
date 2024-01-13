package com.test.taskmanager.service.interf;

import com.test.taskmanager.dto.task.TasksDTO;
import com.test.taskmanager.dto.user.PerformerDTO;
import com.test.taskmanager.entity.Performer;
import com.test.taskmanager.entity.Task;

import java.util.List;

public interface PerformerService {

    List<Performer> addPerformerToNewTask(Task task, Long userId);

    PerformerDTO addPerformer(Long taskId, Long userId);

    TasksDTO getPerformersTasks(String performerEmail);

    void removePerformer(Long taskId, String performerEmail);

}
