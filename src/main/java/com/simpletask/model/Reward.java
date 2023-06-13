package com.simpletask.model;

import com.simpletask.security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "rewards")
@NoArgsConstructor
@AllArgsConstructor
public class Reward {
    @Id
    @GeneratedValue
    private Long id;
    private String text;
    private LocalDateTime tsCreate;
    @ManyToOne
    private User user;
    @Enumerated(value = EnumType.STRING)
    private Level level;
}
