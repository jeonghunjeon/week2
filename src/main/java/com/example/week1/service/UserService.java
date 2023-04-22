package com.example.week1.service;


import com.example.week1.Jwt.JwtUtils;
import com.example.week1.dto.UserRequestDto;
import com.example.week1.dto.UserResponseDto;
import com.example.week1.entity.User;
import com.example.week1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserService(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    public UserResponseDto signUp (UserRequestDto userRequestDto) {
        // 중복된 아이디가 없으면 저장.
        if (userRepository.findByUserName(userRequestDto.getUserName()).isPresent()) {
            throw new IllegalArgumentException("중복된 아이디가 존재합니다.");
        } else {
            User user = new User(userRequestDto.getUserName(), userRequestDto.getPassWord());
            userRepository.save(user);
        }
        return new UserResponseDto("회원가입 성공", 200);
    }

    public UserResponseDto login (UserRequestDto userRequestDto, HttpServletResponse response) {
        // 아이디 비번 체크하고 토큰 헤더에 추가.
        User user = userRepository.findByUserName(userRequestDto.getUserName()).orElseThrow(
                () -> new IllegalArgumentException("아이디를 확인해주세요")
        );
        if (!user.getPassWord().equals(userRequestDto.getPassWord())) {
            throw new IllegalArgumentException("비밀번호를 확인해주세요.");
        }
        response.addHeader(JwtUtils.AUTHORIZATION_HEADER, jwtUtils.createToken(user.getUserName()));
        return new UserResponseDto("로그인 성공", 200);
    }
}