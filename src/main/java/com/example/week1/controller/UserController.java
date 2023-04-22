package com.example.week1.controller;


import com.example.week1.dto.UserRequestDto;
import com.example.week1.dto.UserResponseDto;
import com.example.week1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/blog/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserResponseDto signUp(@RequestBody UserRequestDto requestDto) {
        return userService.signUp(requestDto);
    }

    @PostMapping("/login")
    public UserResponseDto login(@RequestBody UserRequestDto requestDto, HttpServletResponse response) {
        return userService.login(requestDto, response);
    }
}
