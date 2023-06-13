package com.simpletask.service;

import com.simpletask.dto.RewardDTO;
import com.simpletask.dto.TaskDTO;
import com.simpletask.payload.response.Response;

import java.security.Principal;
import java.util.List;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO, Principal principal);
    TaskDTO editTask(TaskDTO taskDTO, Principal principal);
    List<TaskDTO> getActiveTasksByPrincipal(Principal principal);
    List<RewardDTO> getSuitableRewardsByTaskId(Long taskId, Principal principal);
    TaskDTO setReward(Long taskId, Long rewardId, Principal principal);
    Response detachReward(Long taskId, Principal principal);
    Response markDone(Long taskId, Principal principal);
    Response markUndone(Long taskId, Principal principal);
    Response takeReward(Long taskId, Principal principal);
    Response finish(Long taskId, Principal principal);
    Response sendToArchive(Long taskId, Principal principal);
    Response delete(Long taskId, Principal principal);
    TaskDTO getTaskByIdAndPrincipal(Long taskId, Principal principal);
    List<TaskDTO> getArchivedTasksByPrincipal(Principal principal);
}
