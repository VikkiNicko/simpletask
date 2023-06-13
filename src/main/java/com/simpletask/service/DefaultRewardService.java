package com.simpletask.service;

import com.simpletask.dto.RewardDTO;
import com.simpletask.exception.LinkedTasksFoundException;
import com.simpletask.exception.RewardNotFoundException;
import com.simpletask.model.Level;
import com.simpletask.model.Reward;
import com.simpletask.model.State;
import com.simpletask.model.Task;
import com.simpletask.payload.response.Response;
import com.simpletask.payload.response.success.RewardDeletedResponse;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultRewardService implements RewardService{
    private final RewardRepository rewardRepository;
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final Logger LOG = LoggerFactory.getLogger(DefaultRewardService.class);
    public RewardDTO createReward(RewardDTO rewardDTO, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Reward reward = Reward.builder()
                .text(rewardDTO.getText())
                .user(user)
                .level(rewardDTO.getLevel())
                .tsCreate(LocalDateTime.now())
                .build();
        rewardRepository.save(reward);
        LOG.info("User {} created new reward id {}", user.getId(), reward.getId());
        return rewardToRewardDTO(reward);
    }

    public RewardDTO editReward(RewardDTO rewardDTO, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Reward reward = getRewardByIdAndUser(rewardDTO.getId(), user);
        if (hasLinkedTasks(reward, user)) {
            LOG.warn("User {} tried to edit reward {}, but encountered LinkedTasksFoundException", user.getUsername(), reward.getId());
            throw new LinkedTasksFoundException();
        }
        if (rewardDTO.getLevel() != null) {
            reward.setLevel(rewardDTO.getLevel());
        }
        if (rewardDTO.getText() != null) {
            reward.setText(rewardDTO.getText());
        }
        LOG.info("User {} edited reward {}", user.getUsername(), reward.getId());
        rewardRepository.save(reward);
        return rewardToRewardDTO(reward);
    }

    public Response deleteReward(Long rewardId, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Reward reward = getRewardByIdAndUser(rewardId, user);
        if (hasLinkedTasks(reward, user)) {
            LOG.warn("User {} tried to delete reward {}, but encountered LinkedTasksFoundException", user.getUsername(), reward.getId());
            throw new LinkedTasksFoundException();
        }
        LOG.info("User {} deleted reward id {}", user.getUsername(), reward.getId());
        rewardRepository.delete(reward);
        return new RewardDeletedResponse();
    }

    public List<RewardDTO> getAllRewardDTOByPrincipal(Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        LOG.info("User {} viewed all rewards", user.getUsername());
        return rewardRepository.findAllByUser(user)
                .stream()
                .map(this::rewardToRewardDTO)
                .collect(Collectors.toList());
    }

    public List<RewardDTO> getSuitableRewardsByLevel(Level level, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        return rewardRepository.findAllByUserAndLevel(user, level)
                .stream()
                .map(this::rewardToRewardDTO)
                .collect(Collectors.toList());
    }

    public Reward getRewardByIdAndUser(Long id, User owner) {
        return rewardRepository.findByIdAndUser(id, owner).orElseThrow(RewardNotFoundException::new);
    }

    private boolean hasLinkedTasks(Reward reward, User user) {
        Set<State> notEditableStates = new HashSet<>();
        notEditableStates.add(State.ACTIVE);
        notEditableStates.add(State.DONE);
        notEditableStates.add(State.REWARD_TAKEN);
        for (State state : notEditableStates) {
            if (!findTasksByRewardAndUserAndState(reward, user, state).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private List<Task> findTasksByRewardAndUserAndState(Reward reward, User user, State state) {
        return taskRepository.findAllByRewardAndUserAndState(reward, user, state);
    }

    private RewardDTO rewardToRewardDTO(Reward reward) {
        return RewardDTO.builder()
                .id(reward.getId())
                .text(reward.getText())
                .level(reward.getLevel())
                .build();
    }
}
