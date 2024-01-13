package com.test.taskmanager.service.impl;

import com.test.taskmanager.dto.task.TaskDTO;
import com.test.taskmanager.dto.user.PerformerDTO;
import com.test.taskmanager.entity.Performer;
import com.test.taskmanager.entity.Task;
import com.test.taskmanager.entity.User;
import com.test.taskmanager.mapper.UserMapper;
import com.test.taskmanager.repository.PerformerRepository;
import com.test.taskmanager.service.interf.PerformerService;
import com.test.taskmanager.utility.FindEntity;
import com.test.taskmanager.utility.PageBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerformerServiceImpl implements PerformerService {

    private final PerformerRepository performerRepository;
    private final FindEntity findEntity;
    private final UserMapper userMapper;
    private final PageBuilder pageBuilder;

    @Override
    public List<Performer> addPerformerToNewTask(Task task, Long userId) {
        User taskPerformer = findEntity.findUser(userId);
        Performer performer = createPerformer(task, taskPerformer);
        Performer savedPerformer = performerRepository.save(performer);
        List<Performer> performerList = new ArrayList<>();
        performerList.add(savedPerformer);

        log.info("In add performer to new task - performer successfully added.");
        return performerList;
    }

    @Override
    public PerformerDTO addPerformer(Long taskId, Long userId) {
        Task task = findEntity.findTask(taskId);
        User user = findEntity.findUser(userId);
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

        log.info("In get performers tasks - tasks successfully got.");
        return pageBuilder.createPage(taskList, pageNumber, pageSize);
    }

    @Override
    public void removePerformer(Long taskId, String performerEmail) {
        Task task = findEntity.findTask(taskId);
        List<Performer> performerList = task.getPerformers().stream()
                .filter(performer -> performer.getEmail().equals(performerEmail))
                .toList();
        performerRepository.deleteAll(performerList);

        log.info("In remove performer - performer successfully deleted.");
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
