package com.kagan.starter.dto;

import jakarta.validation.constraints.NotBlank;
import jdk.jfr.DataAmount;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private Date createTime;
}
