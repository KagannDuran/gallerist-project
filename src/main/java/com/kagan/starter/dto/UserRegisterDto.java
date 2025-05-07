package com.kagan.starter.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterDto {


    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
