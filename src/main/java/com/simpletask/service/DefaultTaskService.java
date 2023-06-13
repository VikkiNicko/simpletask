package com.simpletask.service;

import com.simpletask.dto.RewardDTO;
import com.simpletask.dto.TaskDTO;
import com.simpletask.exception.*;
import com.simpletask.model.Level;
import com.simpletask.model.Reward;
import com.simpletask.model.State;
import com.simpletask.model.Task;
import com.simpletask.payload.response.MarkUndoneSuccessResponse;
import com.simpletask.payload.response.Response;
import com.simpletask.payload.response.success.*;
import com.simpletask.repository.RewardRepository;
import com.simpletask.repository.TaskRepository;
import com.simpletask.security.auth.UserService;
import com.simpletask.security.user.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultTaskService implements TaskService {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultTaskService.class);
    private final TaskRepository taskRepository;

    private final RewardRepository rewardRepository;
    private final UserService userService;
    private final RewardService rewardService;

    public TaskDTO createTask(TaskDTO taskDTO, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Reward reward = getRewardIfNeeded(taskDTO, principal);
        Task task = Task.builder()
                .text(taskDTO.getText())
                .reward(reward)
                .user(user)
                .level(taskDTO.getLevel())
                .state(State.ACTIVE)
                .tsCreate(LocalDateTime.now())
                .build();
        LOG.info("User {} created task id {}", user.getUsername(), task.getId());
        taskRepository.save(task);
        return taskToTaskDTO(task);
    }

    public TaskDTO editTask(TaskDTO taskDTO, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Task task = getTaskByIdAndUser(taskDTO.getId(), user);
        if (!isTaskEditable(task)) {
            LOG.warn("User {} tried to edit task id {} but encountered IncorrectStateException", user.getUsername(), task.getId());
            throw new IncorrectStateException();
        }
        if (taskDTO.getLevel() != null) {
            if (task.getReward() != null) {
                LOG.warn("User {} tried to edit task id {} but encountered LinkedRewardFoundException", user.getUsername(), task.getId());
                throw new LinkedRewardFoundException();
            }
            task.setLevel(taskDTO.getLevel());
        }
        if (taskDTO.getText() != null) {
            task.setText(taskDTO.getText());
        }
        taskRepository.save(task);
        LOG.info("User {} edited task id {}", user.getUsername(), task.getId());
        return taskToTaskDTO(task);
    }

    public List<TaskDTO> getActiveTasksByPrincipal(Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        LOG.info("User {} viewed all active tasks", user.getUsername());
        return taskRepository.findAllByUserAndState(user, State.ACTIVE)
                .stream()
                .map(this::taskToTaskDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getArchivedTasksByPrincipal(Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        LOG.info("User {} viewed all archived tasks", user.getUsername());
        return taskRepository.findAllByUserAndState(user, State.ARCHIVED)
                .stream()
                .map(this::taskToTaskDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO getTaskByIdAndPrincipal(Long taskId, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        LOG.info("User {} viewed task id {}", user.getUsername(), taskId);
        return taskToTaskDTO(getTaskByIdAndUser(taskId, user));
    }

    public List<RewardDTO> getSuitableRewardsByTaskId(Long taskId, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Task task = getTaskByIdAndUser(taskId, user);
        Level level = task.getLevel();
        LOG.info("User {} viewed suitable rewards for task id {}", user.getUsername(), task.getId());
        return rewardService.getSuitableRewardsByLevel(level, principal);
    }

    public TaskDTO setReward(Long taskId, Long rewardId, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Task task = getTaskByIdAndUser(taskId, user);
        Reward reward = rewardService.getRewardByIdAndUser(rewardId, user);
        if (!isLevelMatch(task, reward)) {
            LOG.warn("User {} tried to set reward for task id {} but encountered LevelDontMatchException", user.getUsername(), task.getId());
            throw new LevelDontMatchException();
        }
        task.setReward(reward);
        LOG.info("User {} set new reward for task id {}", user.getUsername(), task.getId());
        taskRepository.save(task);
        return taskToTaskDTO(task);
    }

    public Response detachReward(Long taskId, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Task task = getTaskByIdAndUser(taskId, user);
        task.setReward(null);
        LOG.info("User {} detached reward from task id {}", user.getUsername(), task.getId());
        taskRepository.save(task);
        return new DetachRewardSuccessResponse();
    }

    public Response markDone(Long taskId, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Task task = getTaskByIdAndUser(taskId, user);
        if (task.getState() != State.ACTIVE) {
            LOG.warn("User {} tried to mark task id {} done but encountered IncorrectStateException", user.getUsername(), task.getId());
            throw new IncorrectStateException();
        }
        task.setState(State.DONE);
        taskRepository.save(task);
        if (task.getReward() != null) {
            LOG.info("User {} mark task id {} done", user.getUsername(), task.getId());
            return new MarkDoneSuccessResponse(task.getReward().getText());
        }
        LOG.info("User {} mark task id {} done", user.getUsername(), task.getId());
        return new MarkDoneSuccessResponse();
    }

    public Response markUndone(Long taskId, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Task task = getTaskByIdAndUser(taskId, user);
        if (task.getState() != State.DONE) {
            LOG.warn("User {} tried to mark task id {} undone but encountered IncorrectStateException", user.getUsername(), task.getId());
            throw new IncorrectStateException();
        }
        task.setState(State.ACTIVE);
        LOG.info("User {} mark task id {} undone", user.getUsername(), task.getId());
        taskRepository.save(task);
        return new MarkUndoneSuccessResponse();
    }

    public Response takeReward(Long taskId, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Task task = getTaskByIdAndUser(taskId, user);
        if (task.getState() != State.DONE) {
            LOG.warn("User {} tried to take reward for task id {} but encountered IncorrectStateException", user.getUsername(), taskId);
            throw new IncorrectStateException();
        }
        if (task.getReward() == null) {
            LOG.warn("User {} tried to take reward that was not set for task id {}", user.getUsername(), taskId);
            throw new RewardNotSetException();
        }
        task.setState(State.REWARD_TAKEN);
        taskRepository.save(task);
        LOG.info("User {} took reward for task with id {}", user.getUsername(), taskId);
        return new RewardTakenResponse(task.getReward().getText());
    }

    public Response finish(Long taskId, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Task task = getTaskByIdAndUser(taskId, user);
        if (task.getState() != State.REWARD_TAKEN && task.getState() != State.DONE) {
            LOG.warn("User {} tried to finish task id {} but encountered IncorrectStateException", user.getUsername(), task.getId());
            throw new IncorrectStateException();
        }
        if (task.getState() == State.DONE && task.getReward() != null) {
            LOG.warn("User {} had tried to finish task with id {}", user.getUsername(), taskId);
            throw new RewardNotTakenException();
        }
        task.setState(State.FINISHED);
        taskRepository.save(task);
        LOG.info("User {} had finished task with id {}", user.getUsername(), taskId);
        return new TaskFinishedResponse();
    }

    public Response sendToArchive(Long taskId, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Task task = getTaskByIdAndUser(taskId, user);
        if (task.getState() != State.FINISHED) {
            LOG.error("IncorrectStateException occurred in request from user {}", user.getUsername());
            throw new IncorrectStateException();
        }
        task.setState(State.ARCHIVED);
        taskRepository.save(task);
        LOG.info("User {} moved task with id {} to archive", user.getUsername(), taskId);
        return new SendToArchiveSuccessResponse();
    }

    public Response delete(Long taskId, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Task task = getTaskByIdAndUser(taskId, user);
       if (task.getState() == State.DELETED) {
           LOG.error("IncorrectStateException occurred in request from user {}", user.getUsername());
           throw new IncorrectStateException();
       }
       task.setState(State.DELETED);
       taskRepository.save(task);
       LOG.info("User {} deleted task with id {}", user.getUsername(), taskId);
       return new DeleteTaskSuccessResponse();
    }

    private TaskDTO taskToTaskDTO(Task task) {
        String rewardText = null;
        Long rewardId = null;
        if (task.getReward() != null) {
            rewardText = task.getReward().getText();
            rewardId = task.getReward().getId();
        }
        return TaskDTO.builder()
                .id(task.getId())
                .text(task.getText())
                .level(task.getLevel())
                .rewardText(rewardText)
                .rewardId(rewardId)
                .build();
    }

    private boolean isTaskEditable(Task task) {
        State taskState = task.getState();
        return taskState.equals(State.ACTIVE) || taskState.equals(State.DONE) || taskState.equals(State.REWARD_TAKEN);
    }

    private boolean isLevelMatch(Task task, Reward reward) {
        if (task.getLevel() == null && reward.getLevel() == null) return true;
        if (task.getLevel() == null || reward.getLevel() == null) return false;
        return task.getLevel() == reward.getLevel();
    }

    private Reward getRewardIfNeeded(TaskDTO taskDTO, Principal principal) {
        Reward reward = null;
        User user = userService.getUserByPrincipal(principal);
        if (taskDTO.getRewardText() != null && !taskDTO.getRewardText().equals("") ) {
            RewardDTO rewardDTO = RewardDTO.builder()
                    .text(taskDTO.getRewardText())
                    .level(taskDTO.getLevel())
                    .build();
            RewardDTO createdRewardDTO = rewardService.createReward(rewardDTO, principal);
            reward = rewardService.getRewardByIdAndUser(createdRewardDTO.getId(), user);
        } else if (taskDTO.getRewardId() != null) {
            reward = rewardService.getRewardByIdAndUser(taskDTO.getRewardId(), user);
        }
        return reward;
    }

    private Task getTaskByIdAndUser(Long taskId, User user) {
        return taskRepository.findOneByIdAndUser(taskId, user).orElseThrow(TaskNotFoundException::new);
    }

}
