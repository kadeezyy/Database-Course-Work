package com.example.musicplatform.controller;

import com.example.musicplatform.dto.CustomUserDto;
import com.example.musicplatform.dto.JwtTokenDto;
import com.example.musicplatform.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public JwtTokenDto register(@RequestBody CustomUserDto customUserDto) {
        return userService.registerUser(customUserDto);
    }

    @PostMapping("/login")
    public JwtTokenDto login(@RequestBody CustomUserDto customUserDto) {
        return userService.authenticateUser(customUserDto);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        response.getOutputStream().write(userService.refreshToken(request, response).getBytes());
    }
}
