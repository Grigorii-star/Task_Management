package com.test.taskmanager.controller;

import com.test.taskmanager.dto.task.TasksDTO;
import com.test.taskmanager.dto.user.PerformerDTO;
import com.test.taskmanager.dto.user.PerformerEmailDTO;
import com.test.taskmanager.service.interf.PerformerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class PerformerController {

    private final PerformerService performerService;

    @PostMapping(value = "/{taskId}/performers/{userId}")
    public ResponseEntity<PerformerDTO> addPerformer(@PathVariable Long taskId,
                                                     @PathVariable Long userId) {
        return new ResponseEntity<>(performerService.addPerformer(taskId, userId), HttpStatus.CREATED);
    }

    @GetMapping(value = "/performers")
    public ResponseEntity<TasksDTO> getPerformersTasks(@RequestBody PerformerEmailDTO emailDTO) {
        return new ResponseEntity<>(performerService.getPerformersTasks(emailDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{taskId}/performers")
    public ResponseEntity<Void> removePerformer(@PathVariable Long taskId,
                                                @RequestBody PerformerEmailDTO emailDTO) {
        performerService.removePerformer(taskId, emailDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
