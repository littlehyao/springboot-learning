package com.hyao.learning.validation.model;

import com.hyao.learning.validation.valid.annotation.PasswordMatch;
import com.hyao.learning.validation.valid.annotation.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@PasswordMatch
public class UserRegister {
    @NotBlank
    @UniqueUsername
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
}
