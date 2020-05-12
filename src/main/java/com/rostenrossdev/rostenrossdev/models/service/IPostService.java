package com.rostenrossdev.rostenrossdev.models.service;

import java.util.List;

import com.rostenrossdev.rostenrossdev.models.entity.Post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface IPostService {

    public Page<Post> findAll(Pageable pageable);

    public List<Post> findAll();

    public void save(Post post);

    public Post findOne(Long id);

    public void delete(Long id);
    
}