package com.simpletask.controller;

import com.simpletask.dto.RewardDTO;
import com.simpletask.payload.response.Response;
import com.simpletask.service.DefaultRewardService;
import com.simpletask.service.RewardService;
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
@RequestMapping("/api/reward")
public class RewardController {
    private final RewardService rewardService;

    @PostMapping("/create")
    public ResponseEntity<RewardDTO> createReward(@Valid @RequestBody RewardDTO rewardDTO, Principal principal) {
       return new ResponseEntity<>(rewardService.createReward(rewardDTO, principal), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<RewardDTO> editReward(@Valid @RequestBody RewardDTO rewardDTO, Principal principal, BindingResult result) {
        return new ResponseEntity<>(rewardService.editReward(rewardDTO, principal), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RewardDTO>> getAllRewards(Principal principal) {
        return new ResponseEntity<>(rewardService.getAllRewardDTOByPrincipal(principal), HttpStatus.OK);
    }

    @DeleteMapping("{rewardId}/delete")
    public Response deleteReward(@PathVariable String rewardId, Principal principal) {
        return rewardService.deleteReward(Long.parseLong(rewardId), principal);
    }

}
