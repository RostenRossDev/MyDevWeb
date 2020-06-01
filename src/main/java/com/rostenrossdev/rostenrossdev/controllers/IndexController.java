package com.rostenrossdev.rostenrossdev.controllers;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.rostenrossdev.rostenrossdev.models.entity.Post;
import com.rostenrossdev.rostenrossdev.models.service.IPostServiceImpl;
import com.rostenrossdev.rostenrossdev.models.service.IUploadFileService;
import com.rostenrossdev.rostenrossdev.util.PageRender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class IndexController{

    @Autowired
    private IPostServiceImpl postService;

     @Autowired
    private IUploadFileService uploadFileService;


    @GetMapping(value={"/uploads/{filename:.+}"})
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

        Resource recurso=null;
        try {
            recurso = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+recurso.getFilename()+"\"")
                .body(recurso);
    }


    @GetMapping("/home")
    public String index(@RequestParam(name = "page", defaultValue = "0") int page, Model model, SessionLocaleResolver localeResolver, HttpServletRequest request ){
        Pageable pageRequest = PageRequest.of(page, 4);
        Page<Post> postLista = postService.findAll(pageRequest);
        PageRender<Post> pageRender = new PageRender<>("/home", postLista);
        if (postLista.isEmpty()) {
            model.addAttribute("posts","Por el momento no hay ningun proyecto para mostrar!!");
            }else {
            String idioma;
            idioma= localeResolver.resolveLocale(request).getLanguage();

            model.addAttribute("idioma", idioma);
            model.addAttribute("postLista", postLista);            
            model.addAttribute("page", pageRender);            
        }
        
        return "index";
    }

    @GetMapping(value={"/","","/index"})
    public String redirigirHome(){

        return "redirect:/home";
    }

    @Secured("ADMIN")
    @PostMapping("/form")
    public String guardarPost(@Valid Post post, @RequestParam("file") MultipartFile foto,
         Model model,  RedirectAttributes flash, BindingResult result, SessionStatus status){

         if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario del Post");
            return "form_post";
        }
         if (!foto.isEmpty()) {
            if (post.getId() != null && post.getFoto() != null && post.getFoto().length()>0) {
               uploadFileService.delete(post.getFoto());
                
            }
            String uniqueFileName =null;
            try {
                uniqueFileName = uploadFileService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }
            flash.addFlashAttribute("info", "Has subido correctamente '"+uniqueFileName+"'!!");

            post.setFoto(uniqueFileName);
        }

        String mensajeFlash=(post.getId() != null)? "Cliente editado con éxito!!" : "Cliente creado con éxito!!";
        postService.save(post);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/home";

    }

    @Secured("ADMIN")
    @GetMapping("/nuevoPost")
    public String nuevoPost(Model model){
        Post post = new Post();
        model.addAttribute("post", post);
        model.addAttribute("titulo", "Formulario del Post");
        return "form_post";
    }

    @Secured("ADMIN")
    @RequestMapping(value="/form/{id}")
    public String editar(@PathVariable(value="id")Long id, Model model,
                          RedirectAttributes flash){
        Post post=null;
        if(id > 0){
            post= postService.findOne(id);
            if (post == null) {
                flash.addFlashAttribute("error","El ID del post no existe en la base de datos!!");
                return "redirect:/home";
            }
        }else {
            flash.addFlashAttribute("error","El ID del post no puede ser cero!!");
            return "redirect:/home";
        }
        model.addAttribute("post", post);
        model.addAttribute("titulo", "Formulario de Cliente");
        return "form_post";
    }

    @Secured("ADMIN")
    @RequestMapping(value="/eliminar/{id}")
    public String eliminar(@PathVariable (value="id") Long id,  RedirectAttributes flash){
        if (id>0) {
            Post post=postService.findOne(id);
            postService.delete(id);
            flash.addFlashAttribute("success", "Cliente eliminado con éxito!!");
            if (uploadFileService.delete(post.getFoto())) {
                flash.addFlashAttribute("info", "Foto: "+post.getFoto()+" eliminada con exito!!");
            }
        }
        return "redirect:/home";
    }

    @GetMapping("/about")
    public String us(){

        return "about";
    }

    @GetMapping ("/ver/{id}")
    public String ver(@PathVariable(value="id") Long id, Model model, RedirectAttributes flash, SessionLocaleResolver localeResolver, HttpServletRequest request){

        Post post = postService.findOne(id);
        if (post==null) {
            flash.addFlashAttribute("error", "El post no existe en la base de datos!!");
            return "redirect:/home";
        }

        String idioma;
        idioma= localeResolver.resolveLocale(request).getLanguage();

        model.addAttribute("idioma", idioma);
        model.addAttribute("post", post);

        return "ver_post";
    }
}