package com.simpletask.dto;

import com.simpletask.model.Level;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TaskDTO {
    private Long id;
    @Size(min=1, message = "Task text cannot be blank")
    private String text;
    private Level level;
    private String rewardText;
    private Long rewardId;
}
