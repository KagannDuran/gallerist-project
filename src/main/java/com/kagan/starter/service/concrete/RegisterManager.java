package com.kagan.starter.service.concrete;

import com.kagan.starter.dto.AuthResponse;
import com.kagan.starter.dto.RefreshTokenDto;
import com.kagan.starter.dto.UserDto;
import com.kagan.starter.dto.UserRegisterDto;
import com.kagan.starter.entity.AppUser;
import com.kagan.starter.entity.RefreshToken;
import com.kagan.starter.exception.BaseException;
import com.kagan.starter.exception.ErrorMessage;
import com.kagan.starter.exception.MessageType;
import com.kagan.starter.jwt.JWTService;
import com.kagan.starter.mapper.UserMapper;
import com.kagan.starter.repository.RefreshTokenRepository;
import com.kagan.starter.repository.UserRepository;
import com.kagan.starter.service.abstracts.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegisterManager implements IRegisterService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationProvider provider;
    private final JWTService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;


    public RegisterManager(UserRepository userRepository, UserMapper userMapper, AuthenticationProvider provider, JWTService jwtService,RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.provider = provider;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
    }


    private RefreshToken createRefreshToken(AppUser user)
    {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCreateTime(new Date());
        refreshToken.setExpiredDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4 ));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        return refreshToken;
    }

    @Override
    public UserDto register(UserRegisterDto input) {
        if (userRepository.findByUsername(input.getUsername()).isPresent()) {
            throw new BaseException(new ErrorMessage(MessageType.ALREADY_EXISTS, null));
        }
        AppUser user = userMapper.RegistertoEntity(input);
        userRepository.save(user);
        return userMapper.toDtoUser(user);
    }

    @Override
    public AuthResponse authenticate(UserRegisterDto input) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(input.getUsername(),input.getPassword());
            provider.authenticate(authenticationToken);

            Optional<AppUser> optUser = userRepository.findByUsername(input.getUsername());

            String accessToken = jwtService.generateToken(optUser.get());

            RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(optUser.get()));

            return new AuthResponse(accessToken,savedRefreshToken.getRefreshToken());

        }catch (Exception e)
        {
            throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID,e.getMessage()));
        }
    }


    public boolean isValidRefreshToken(Date expiredDate)
    {
        return new Date().before(expiredDate);
    }


    @Override
    public AuthResponse refreshToken(RefreshTokenDto input) {
        Optional<RefreshToken> optRefreshToken = refreshTokenRepository.findByRefreshToken(input.getRefreshToken());
        if(optRefreshToken.isEmpty())
        {
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_NOT_FOUND,input.getRefreshToken()));
        }
        if(!isValidRefreshToken(optRefreshToken.get().getExpiredDate()))
        {
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_IS_EXPIRED,input.getRefreshToken()));
        }

        AppUser user = optRefreshToken.get().getUser();
        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = createRefreshToken(user);
        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);

        return new AuthResponse(accessToken,savedRefreshToken.getRefreshToken());
    }
}
