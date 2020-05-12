package com.rostenrossdev.rostenrossdev.controllers;

import com.rostenrossdev.rostenrossdev.models.entity.Post;
import com.rostenrossdev.rostenrossdev.models.service.IPostServiceImpl;
import com.rostenrossdev.rostenrossdev.util.PageRender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController{

    @Autowired
    private IPostServiceImpl poserService;

    @GetMapping("/home")
    public String index(@RequestParam(name = "page", defaultValue = "0") int page, Model model){
        Pageable pageRequest = PageRequest.of(page, 4);
        Page<Post> posts = poserService.findAll(pageRequest);
        PageRender<Post> pageRender = new PageRender<>("/home", posts);
        if (posts.isEmpty()) {
            model.addAttribute("posts","Por el momento no hay ningun proyecto para mostrar!!");
        }else {
            model.addAttribute("posts", posts);
            model.addAttribute("page", pageRender);            
        }
        
        return "index";
    }

    @GetMapping(value={"/","","/index"})
    public String redirigirHome(){

        return "redirect:/home";
    }
}