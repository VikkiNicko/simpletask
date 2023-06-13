package com.simpletask.controller;

import com.simpletask.dto.RewardDTO;
import com.simpletask.dto.TaskDTO;
import com.simpletask.payload.response.Response;
import com.simpletask.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO, Principal principal, BindingResult result) {
        return new ResponseEntity<>(taskService.createTask(taskDTO,principal), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<TaskDTO> editTask(@Valid @RequestBody TaskDTO taskDTO, Principal principal) {
        return new ResponseEntity<>(taskService.editTask(taskDTO,principal), HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<TaskDTO>> getActiveTasks(Principal principal) {
        return new ResponseEntity<>(taskService.getActiveTasksByPrincipal(principal), HttpStatus.OK);
    }

    @GetMapping("/archive")
    public ResponseEntity<List<TaskDTO>> getArchivedTasks(Principal principal) {
        return new ResponseEntity<>(taskService.getArchivedTasksByPrincipal(principal), HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable String taskId, Principal principal) {
        return new ResponseEntity<>(taskService.getTaskByIdAndPrincipal(Long.parseLong(taskId), principal), HttpStatus.OK);
    }

    @GetMapping("/{taskId}/findReward")
    public ResponseEntity<List<RewardDTO>> getSuitableRewards(@PathVariable String taskId, Principal principal) {
        return new ResponseEntity<>(taskService.getSuitableRewardsByTaskId(Long.parseLong(taskId), principal), HttpStatus.OK);
    }

    @PutMapping("{taskId}/setReward/{rewardId}")
    public ResponseEntity<TaskDTO> setReward(@PathVariable String taskId,
                                             @PathVariable String rewardId,
                                             Principal principal) {
        TaskDTO taskDTO = taskService.setReward(Long.parseLong(taskId), Long.parseLong(rewardId), principal);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @PutMapping("{taskId}/detachReward")
    public Response detachReward(@PathVariable String taskId, Principal principal) {
        return taskService.detachReward(Long.parseLong(taskId), principal);
    }

    @PutMapping("{taskId}/markDone")
    public Response markDone(@PathVariable String taskId, Principal principal) {
        return taskService.markDone(Long.parseLong(taskId), principal);
    }

    @PutMapping("{taskId}/markUndone")
    public Response markUndone(@PathVariable String taskId, Principal principal) {
        return taskService.markUndone(Long.parseLong(taskId), principal);
    }

    @PutMapping("{taskId}/takeReward")
    public Response takeReward(@PathVariable String taskId, Principal principal) {
        return taskService.takeReward(Long.parseLong(taskId), principal);
    }

    @PutMapping("{taskId}/finish")
    public Response finishTask(@PathVariable String taskId, Principal principal) {
        return taskService.finish(Long.parseLong(taskId), principal);
    }

    @PutMapping("{taskId}/sendToArchive")
    public Response sendToArchive(@PathVariable String taskId, Principal principal) {
        return taskService.sendToArchive(Long.parseLong(taskId), principal);
    }

    @DeleteMapping(value = "{taskId}/delete", produces = "application/json")
    public Response delete(@PathVariable String taskId, Principal principal) {
        return taskService.delete(Long.parseLong(taskId), principal);
    }
}
