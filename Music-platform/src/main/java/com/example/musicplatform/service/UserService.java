package com.example.musicplatform.service;

import com.example.musicplatform.dto.CustomUserDto;
import com.example.musicplatform.dto.JwtTokenDto;
import com.example.musicplatform.exception.NotFoundException;
import com.example.musicplatform.exception.enums.DataAccessMessages;
import com.example.musicplatform.model.pojos.CustomUser;
import com.example.musicplatform.repository.JwtRepository;
import com.example.musicplatform.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtRepository jwtTokenRepository;
    private final JwtService jwtService;

    @Autowired
    public UserService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            JwtRepository jwtTokenRepository,
            DSLContext jooq, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.jwtTokenRepository = jwtTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public JwtTokenDto registerUser(CustomUserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.findUserByUsername(user.getUsername()) != null)
            throw new NotFoundException(DataAccessMessages.NOT_UNIQUE_OBJECT.name());
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);
        saveUserToken(savedUser.getId(), jwtToken);
        return JwtTokenDto.builder()
                .access_token(jwtToken)
                .refresh_token(refreshToken).build();
    }

    public JwtTokenDto authenticateUser(CustomUserDto customUser) throws AuthenticationException {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    customUser.getUsername(),
                    customUser.getPassword()
            )
        );
        var user = userRepository.findUserByUsername(customUser.getUsername());
        if (user == null)
            throw new UsernameNotFoundException("User not found");
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user.getId(), jwtToken);
        return JwtTokenDto.builder()
                .access_token(jwtToken)
                .refresh_token(refreshToken)
                .build();
    }

    public String refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken, username;
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return null;
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = userRepository.findUserByUsername(username);
            if (user == null)
                throw new UsernameNotFoundException("No user found");

            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user.getId(), accessToken);
                var authenticationResponse = JwtTokenDto.builder()
                        .access_token(accessToken)
                        .refresh_token(refreshToken).build();
                new ObjectMapper().writeValue(response.getOutputStream(), authenticationResponse);
                return accessToken;
            }
        }
        return null;
    }

    private void revokeAllUserTokens(CustomUser user) {
        var validUserTokens = jwtTokenRepository.findAllValidTokenByUser(user);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        jwtTokenRepository.saveAll(validUserTokens);
    }

    public CustomUser findUserById(UUID id) {
        return userRepository.findUserById(id);
    }

//    public List<CustomUser> findAllUsersByUsername(String name) {
//        var users = userRepository.findAllUsersByUsername(name);
//        if (users.isEmpty())
//            throw new UsernameNotFoundException("No users with such username: " + name);
//        List<CustomUser> resultUsers = new ArrayList<>();
//        for (var user : users)
//            user.ifPresent(value -> resultUsers.add(userConverter.userEntityToDto(value)));
//        return resultUsers;
//    }

    private void saveUserToken(UUID userId, String jwtToken) {
        jwtTokenRepository.save(jwtToken, userId);
    }
}
