package com.example.week1.entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])(?=\\S+$).{4,10}",
            message = "아이디는 알파벳 소문자, 숫자를 입력하고 4~10자리로 구성해주세요.")
    private String userName;
    @Column(nullable = false)
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=\\S+$).{8,15}",
            message = "비밀번호는 알파벳 대소문자, 숫자를 입력하고 8~15자리로 구성해주세요.")
    private String passWord;

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public User() {}

    public User(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.passWord = user.getPassWord();
    }

    public User (String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }
}
