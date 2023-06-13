package com.simpletask.repository;

import com.simpletask.model.Reward;
import com.simpletask.model.State;
import com.simpletask.model.Task;
import com.simpletask.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUserAndState(User user, State state);

    List<Task> findAllByRewardAndUser(Reward reward, User user);

    List<Task> findAllByRewardAndUserAndState(Reward reward, User user, State state);
    Optional<Task> findByIdAndUser(Long id, User user);
    List<Task> findTasksByUserAndState(User user, State state);
    Optional<Task> findOneByIdAndUser(Long id, User user);
}
