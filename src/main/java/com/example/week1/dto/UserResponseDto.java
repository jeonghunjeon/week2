package com.example.week1.dto;

public class UserResponseDto {

    private String msg;
    private int statusCode;

    public String getMsg() {
        return msg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public UserResponseDto () {};
    public UserResponseDto(String msg, int statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
