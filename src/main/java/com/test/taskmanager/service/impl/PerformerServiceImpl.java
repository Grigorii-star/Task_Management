package com.test.taskmanager.service.impl;

import com.test.taskmanager.dto.task.TaskDTO;
import com.test.taskmanager.dto.user.PerformerDTO;
import com.test.taskmanager.entity.Performer;
import com.test.taskmanager.entity.Task;
import com.test.taskmanager.entity.User;
import com.test.taskmanager.exception.TaskNotFoundException;
import com.test.taskmanager.exception.UserNotFoundException;
import com.test.taskmanager.mapper.TaskMapper;
import com.test.taskmanager.mapper.UserMapper;
import com.test.taskmanager.repository.PerformerRepository;
import com.test.taskmanager.repository.TaskRepository;
import com.test.taskmanager.repository.UserRepository;
import com.test.taskmanager.service.interf.PerformerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerformerServiceImpl implements PerformerService {

    private final PerformerRepository performerRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    @Override
    public List<Performer> addPerformerToNewTask(Task task, Long userId) {
        User taskPerformer = findUser(userId);
        Performer performer = createPerformer(task, taskPerformer);
        Performer savedPerformer = performerRepository.save(performer);
        List<Performer> performerList = new ArrayList<>();
        performerList.add(savedPerformer);

        log.info("In add performer to new task - performer successfully added.");
        return performerList;
    }

    @Override
    public PerformerDTO addPerformer(Long taskId, Long userId) {
        Task task = findTask(taskId);
        User user = findUser(userId);
        Performer performer = createPerformer(task, user);
        performerRepository.save(performer);

        log.info("In add performer - performer successfully added.");
        return userMapper.userToUserDto(user);
    }

    @Override
    public Page<TaskDTO> getPerformersTasks(String performerEmail, Integer pageNumber, Integer pageSize) {
        List<Performer> performerList = performerRepository.findByEmail(performerEmail);
        List<Task> taskList = performerList.stream()
                .map(Performer::getTask)
                .distinct()
                .toList();

        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        List<TaskDTO> allTasks = taskMapper.listTaskToListTaskDTO(taskList);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), allTasks.size());
        List<TaskDTO> pageContent = allTasks.subList(start, end);

        log.info("In get performers tasks - tasks successfully got.");
        return new PageImpl<>(pageContent, pageRequest, allTasks.size());
    }

    @Override
    public void removePerformer(Long taskId, String performerEmail) {
        Task task = findTask(taskId);
        List<Performer> performerList = task.getPerformers().stream()
                .filter(performer -> performer.getEmail().equals(performerEmail))
                .toList();
        performerRepository.deleteAll(performerList);

        log.info("In remove performer - performer successfully deleted.");
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private Task findTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    private Performer createPerformer(Task task, User user) {
        return Performer.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .task(task)
                .build();
    }

}
