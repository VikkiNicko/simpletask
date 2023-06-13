package com.simpletask.security.auth;

import com.simpletask.validation.annotation.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@PasswordMatches
public class RegisterRequest {
    private String username;
    private String nickname;
    @Length(min = 4)
    private String password;
    private String confirmPassword;
}
