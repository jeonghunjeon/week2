package com.example.week1.controller;

import com.example.week1.dto.BlogRequestDto;
import com.example.week1.dto.BlogResponseDto;
import com.example.week1.dto.UserResponseDto;
import com.example.week1.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/create")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto, HttpServletRequest request) {
        return blogService.createBlog(requestDto, request);
    }

    @GetMapping("/list")
    public List<BlogResponseDto> getBlogList() {
        return blogService.getBlogList();
    }

    @GetMapping("/{id}")
    public BlogResponseDto getBlog(@PathVariable Long id) {
        return blogService.getBlog(id);
    }

    @PutMapping("/update/{id}")
    public BlogResponseDto updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto, HttpServletRequest request) {
        return blogService.updateBlog(id, requestDto, request);
    }

    @DeleteMapping("/delete/{id}")
    public UserResponseDto deleteBlog(@PathVariable Long id, HttpServletRequest request) {
        return blogService.deleteBlog(id, request);
    }

    @GetMapping("/title/{title}")
    public BlogResponseDto getBlogByTitle(@PathVariable String title) {
        return blogService.getBlogByTitle(title);
    }
}

