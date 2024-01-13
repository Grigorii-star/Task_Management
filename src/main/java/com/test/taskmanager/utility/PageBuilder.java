package com.test.taskmanager.utility;

import com.test.taskmanager.dto.task.TaskDTO;
import com.test.taskmanager.entity.Task;
import com.test.taskmanager.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PageBuilder {

    private final TaskMapper taskMapper;

    public PageImpl<TaskDTO> createPage(List<Task> list, Integer page, Integer size) {
        Pageable pageRequest = PageRequest.of(page, size);
        List<TaskDTO> allTasks = taskMapper.listTaskToListTaskDTO(list);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), allTasks.size());
        List<TaskDTO> pageContent = allTasks.subList(start, end);

        return new PageImpl<>(pageContent, pageRequest, allTasks.size());
    }

}
