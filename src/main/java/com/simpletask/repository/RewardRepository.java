package com.simpletask.repository;

import com.simpletask.model.Level;
import com.simpletask.model.Reward;
import com.simpletask.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
    List<Reward> findAllByUser(User user);
    List<Reward> findAllByUserAndLevel(User user, Level level);
    Optional<Reward> findByIdAndUser(Long rewardId, User user);


}
