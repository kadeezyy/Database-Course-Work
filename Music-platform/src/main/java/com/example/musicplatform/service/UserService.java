package com.example.musicplatform.service;

import com.example.musicplatform.dto.CustomUserDto;
import com.example.musicplatform.dto.JwtTokenDto;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;
    private final AuthenticationManager authenticationManager;
    private final JwtRepository jwtTokenRepository;
    private final JwtService jwtService;

    @Autowired
    public UserService(
            UserRepository userRepository,
            UserConverter userConverter,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            JwtRepository jwtTokenRepository,
            DSLContext jooq) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.jooq = jooq;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.jwtTokenRepository = jwtTokenRepository;
    }

    public JwtTokenDto registerUser(CustomUser CustomUser) {
        CustomUserDto user = CustomUserDto.builder()
                .username(CustomUser.getUsername())
                .password(passwordEncoder.encode(CustomUser.getPassword())).build();
        if (userRepository.findUserByUsername(user.getUsername()).isPresent())
            throw new NotUniqueUsernameException(user.getUsername());

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);
        saveUserToken(user, jwtToken);
        return JwtTokenDto.builder()
                .access_token(jwtToken)
                .refresh_token(refreshToken).build();
    }

    public JwtTokenDto authenticateUser(CustomUser CustomUser) throws AuthenticationException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        CustomUser.getUsername(),
                        CustomUser.getPassword()
                )
        );
        var user = userRepository.findUserByUsername(CustomUser.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
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
            var user = userRepository.findUserByUsername(username).orElseThrow(
                    () -> new UsernameNotFoundException("No user found")
            );
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
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
        var validUserTokens = jwtTokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        jwtTokenRepository.saveAll(validUserTokens);
    }

    public CustomUser findUserById(int id) {
        Optional<CustomUser> user = userRepository.findUserById(id);
        return user.map(value -> userConverter.userEntityToDto(value)).orElse(null);
    }

    public List<CustomUser> findAllUsersByUsername(String name) {
        var users = userRepository.findAllUsersByUsername(name);
        if (users.isEmpty())
            throw new UsernameNotFoundException("No users with such username: " + name);
        List<CustomUser> resultUsers = new ArrayList<>();
        for (var user : users)
            user.ifPresent(value -> resultUsers.add(userConverter.userEntityToDto(value)));
        return resultUsers;
    }

    private void saveUserToken(CustomUser user, String jwtToken) {
        jwtTokenRepository.save(jwtToken, user);
    }
}
