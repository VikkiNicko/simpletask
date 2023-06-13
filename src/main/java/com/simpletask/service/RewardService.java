package com.simpletask.service;

import com.simpletask.dto.RewardDTO;
import com.simpletask.model.Level;
import com.simpletask.model.Reward;
import com.simpletask.model.State;
import com.simpletask.model.Task;
import com.simpletask.payload.response.Response;
import com.simpletask.security.user.User;

import java.security.Principal;
import java.util.List;

public interface RewardService {
    RewardDTO createReward(RewardDTO rewardDTO, Principal principal);
    RewardDTO editReward(RewardDTO rewardDTO, Principal principal);
    Response deleteReward(Long rewardId, Principal principal);
    List<RewardDTO> getAllRewardDTOByPrincipal(Principal principal);
    List<RewardDTO> getSuitableRewardsByLevel(Level level, Principal principal);
    Reward getRewardByIdAndUser(Long id, User owner);
}
