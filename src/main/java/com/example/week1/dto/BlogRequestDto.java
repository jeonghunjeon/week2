package com.example.week1.dto;

import lombok.Getter;

@Getter
public class BlogRequestDto {
    private String title;
    private String userName;
    private String content;

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

