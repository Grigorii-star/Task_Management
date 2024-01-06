package com.test.taskmanager.service.impl;

import com.test.taskmanager.dto.task.TasksDTO;
import com.test.taskmanager.dto.user.PerformerDTO;
import com.test.taskmanager.dto.user.PerformerEmailDTO;
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
    public TasksDTO getPerformersTasks(PerformerEmailDTO emailDTO) {
        List<Performer> performerList = performerRepository.findByEmail(emailDTO.getPerformerEmail());
        List<Task> tasks = performerList.stream()
                .map(Performer::getTask)
                .distinct()
                .toList();
        Integer tasksCount = tasks.size();

        log.info("In get performers tasks - tasks successfully got.");
        return TasksDTO.builder()
                .count(tasksCount)
                .tasks(taskMapper.listTaskToListTaskDTO(tasks))
                .build();
    }

    @Override
    public void removePerformer(Long taskId, PerformerEmailDTO emailDTO) {
        Task task = findTask(taskId);
        List<Performer> performerList = task.getPerformers().stream()
                .filter(performer -> performer.getEmail().equals(emailDTO.getPerformerEmail()))
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
