package com.example.week1.service;

import com.example.week1.Jwt.JwtUtils;
import com.example.week1.dto.BlogRequestDto;
import com.example.week1.dto.BlogResponseDto;
import com.example.week1.dto.UserResponseDto;
import com.example.week1.entity.Blog;
import com.example.week1.entity.Timestamped;
import com.example.week1.entity.User;
import com.example.week1.repository.BlogRepository;
import com.example.week1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final JwtUtils jwtUtils;

    @Autowired
    public BlogService(BlogRepository blogRepository, JwtUtils jwtUtils, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.jwtUtils = jwtUtils;
    }

    public BlogResponseDto createBlog(BlogRequestDto requestDto, HttpServletRequest request) {
        // checkToken을 통해서 token유효성 검사 후 나머지 작업.
        // blogRepository.save()의 반환값 Entity를 바로 BlogResponseDto 생성자에 넣어서 반환.
        User user = jwtUtils.checkToken(request);
        if (user != null) {
            requestDto.setUserName(user.getUserName());
            Blog blog = new Blog(requestDto);
            return new BlogResponseDto(blogRepository.save(blog));
        }
        throw new IllegalArgumentException("잘못된 토큰입니다.");
    }

    public List<BlogResponseDto> getBlogList() {
        // 테이블에 저장되어있는 모든 게시글 목록을 조회
        return blogRepository.findAll().stream().sorted(Comparator.comparing(Timestamped::getCreatedAt).reversed())
                .map(BlogResponseDto::new).collect(Collectors.toList());
    }

    public BlogResponseDto getBlog(Long id) {
        // 조회하기 위해 받아온 Blog 의 id를 사용해서 해당 Blog 인스턴스가 테이블에 존재하는지 확인하고 가져옵니다.
        Blog blog = checkBlog(id);
        return new BlogResponseDto(blog);
    }

    @Transactional // blog.update를 반영하기 위해서
    public BlogResponseDto updateBlog(Long id, BlogRequestDto requestDto, HttpServletRequest request) {
        // checkToken으로 토큰 확인 후 게시글 확인하고 작성자 비교하고 업데이트.
        User user = jwtUtils.checkToken(request);
        Blog blog = checkBlog(id);
        if (user != null) {
            if (user.getUserName().equals(blog.getUserName())) {
                // update를 하면서 인자로 requestDto 하나만 넣고 싶어서 setUserName을 사용했는데 나쁜 방법인지.
                // 나쁘다면 requestDto 하나만 넣는 방법이 어떤게 있을지.
                requestDto.setUserName(user.getUserName());
                blog.update(requestDto);
            } else {
                throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
            }
            return new BlogResponseDto(blog);
        }
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
    }

    public UserResponseDto deleteBlog(Long id, HttpServletRequest request) {
        // 토큰 확인하고 게시글 작성자 확인하고 삭제.
        User user = jwtUtils.checkToken(request);
        Blog blog = checkBlog(id);
        if (user != null) {
            if (user.getUserName().equals(blog.getUserName())) {
                blogRepository.delete(blog);
                return new UserResponseDto("삭제에 성공했습니다.", 200);
            } else {
                throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
            }
        }
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
    }

    public BlogResponseDto getBlogByTitle(String title) {
        Blog blog = blogRepository.findByTitle(title).orElseThrow(
                () -> new IllegalArgumentException("해당하는 제목의 게시글이 없습니다.")
                // 원래 NullPointerException을 사용했지만 IllegalArgumentException 이 상황에 더 알맞아서 변경.
        );
        return new BlogResponseDto(blog);
    }

    private Blog checkBlog(Long id) {
        return blogRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("일치하는 ID가 없습니다.")
                // 원래 NullPointerException을 사용했지만 IllegalArgumentException 이 상황에 더 알맞아서 변경.
        );
    }

}
