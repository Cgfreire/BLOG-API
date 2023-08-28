package com.unisales.blogapi.controller;

import com.unisales.blogapi.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.unisales.blogapi.repository.PostRepository;


@CrossOrigin
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping(value = "/posts")
    public List<Post>lista_posts(){return postRepository.findAll();}
    
    @GetMapping(value = "/posts/{id}")
    public Post obterpostPorId(@PathVariable("id") Long id) {
    Optional<Post> postOptional = postRepository.findById(id);
    return postOptional.get();
    }
    
    @PostMapping(value = "/cadastro")
    public Post cadastrar(@RequestBody Post post){return postRepository.save(post);}

    @PutMapping(value = "/atualiza/{id}")
    public Post atualiza(@PathVariable(value = "id") Long postId, @RequestBody Post novo_post) {
        Post postExistente = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("Post não encontrado com o ID: " + postId));
        postExistente.setConteudo(novo_post.getConteudo());
        return postRepository.save(postExistente);
}

    
    @DeleteMapping(value = "/exclui/{id}")
    public String exclui(@PathVariable("id")Long id){
        if(id != null){
            postRepository.deleteById(id);
            return "Excluído";
        }
        return null;
    }
}
