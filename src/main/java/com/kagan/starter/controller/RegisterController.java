package com.kagan.starter.controller;

import com.kagan.starter.dto.AuthResponse;
import com.kagan.starter.dto.RefreshTokenDto;
import com.kagan.starter.dto.UserDto;
import com.kagan.starter.dto.UserRegisterDto;
import com.kagan.starter.entity.AppUser;
import com.kagan.starter.mapper.UserMapper;
import com.kagan.starter.service.abstracts.IRegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private final IRegisterService registerService;


    public RegisterController(IRegisterService registerService) {
        this.registerService = registerService;

    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody  UserRegisterDto input)
    {
        UserDto userDto = registerService.register(input);

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody UserRegisterDto input )
    {
        AuthResponse authResponse = registerService.authenticate(input);
        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenDto input)
    {
        AuthResponse authResponse = registerService.refreshToken(input);
        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }
}
