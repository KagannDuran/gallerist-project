package com.kagan.starter.service.abstracts;

import com.kagan.starter.dto.AuthResponse;
import com.kagan.starter.dto.RefreshTokenDto;
import com.kagan.starter.dto.UserDto;
import com.kagan.starter.dto.UserRegisterDto;
import com.kagan.starter.entity.RefreshToken;

public interface IRegisterService {

    public UserDto register(UserRegisterDto input);
    public AuthResponse authenticate(UserRegisterDto input);
    public AuthResponse refreshToken(RefreshTokenDto input);
}
