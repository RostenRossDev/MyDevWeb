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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class IndexController{

    @Autowired
    private IPostServiceImpl postService;

    @GetMapping("/home")
    public String index(@RequestParam(name = "page", defaultValue = "0") int page, Model model){
        Pageable pageRequest = PageRequest.of(page, 4);
        Page<Post> posts = postService.findAll(pageRequest);
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

    @GetMapping("/nuevoPost")
    public String nuevoPost(Model model,  RedirectAttributes flash){

        
        model.addAttribute("titulo", "Crear Factura");        

        return "form_post";
    }
    @PostMapping("/guardarPost")
    public String guardarPost(@RequestAttribute (value="psot") Post post, Model model, RedirectAttributes flash){
        
        postService.save(post);
        flash.addAttribute("success","Post agregado con exito!!");
        return "redirect:/home";
    }
}