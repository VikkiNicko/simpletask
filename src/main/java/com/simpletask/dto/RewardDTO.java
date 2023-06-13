package com.simpletask.dto;

import com.simpletask.model.Level;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RewardDTO {
    private Long id;
    @Size(min=1, message = "Reward text cannot be blank")
    private String text;
    
    private Level level;
}
