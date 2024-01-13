package com.test.taskmanager.service.interf;

import com.test.taskmanager.dto.task.TaskDTO;
import com.test.taskmanager.dto.user.PerformerDTO;
import com.test.taskmanager.entity.Performer;
import com.test.taskmanager.entity.Task;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PerformerService {

    List<Performer> addPerformerToNewTask(Task task, Long userId);

    PerformerDTO addPerformer(Long taskId, Long userId);

    Page<TaskDTO> getPerformersTasks(String performerEmail, Integer pageNumber, Integer pageSize);

    void removePerformer(Long taskId, String performerEmail);

}
