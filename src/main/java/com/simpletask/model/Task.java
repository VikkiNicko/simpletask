package com.simpletask.model;

import com.simpletask.security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    private String text;
    private LocalDateTime tsCreate;

    @ManyToOne
    private Reward reward;
    @ManyToOne
    private User user;

    @Enumerated(value =  EnumType.STRING)
    private State state;

    @Enumerated(value = EnumType.STRING)
    private Level level;

}
