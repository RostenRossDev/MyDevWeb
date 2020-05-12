package com.rostenrossdev.rostenrossdev.models.dao;

import com.rostenrossdev.rostenrossdev.models.entity.Post;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface IPostDao extends PagingAndSortingRepository<Post, Long>{

    
}