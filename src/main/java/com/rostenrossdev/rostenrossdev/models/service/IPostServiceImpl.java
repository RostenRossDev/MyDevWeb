package com.rostenrossdev.rostenrossdev.models.service;

import java.util.List;

import com.rostenrossdev.rostenrossdev.models.dao.IPostDao;
import com.rostenrossdev.rostenrossdev.models.entity.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IPostServiceImpl implements IPostService {

    @Autowired
    private IPostDao postDao;

    @Override
    @Transactional(readOnly = true)
    public Page<Post> findAll(Pageable pageable) {
        return postDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findAll() {
        
        return (List<Post>) postDao.findAll();
    }

    @Override
    @Transactional
    public void save(Post post) {

        postDao.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Post findOne(Long id) {
        // TODO Auto-generated method stub        
        return postDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // TODO Auto-generated method stub
        postDao.deleteById(id);
    }
    
}