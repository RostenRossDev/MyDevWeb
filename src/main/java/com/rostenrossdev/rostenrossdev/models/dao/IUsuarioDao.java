package com.rostenrossdev.rostenrossdev.models.dao;


import com.rostenrossdev.rostenrossdev.models.entity.Usuario;

import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{

    public Usuario findByUsername(String username);
}