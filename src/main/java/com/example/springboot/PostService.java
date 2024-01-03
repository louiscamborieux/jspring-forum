package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<PostRestreint> getAllPostsRestreints() {
        List<Post> posts = (List<Post>) postRepository.findAll();
        return posts.stream()
                .map(Post::toRestreint)
                .collect(Collectors.toList());
    }
}
