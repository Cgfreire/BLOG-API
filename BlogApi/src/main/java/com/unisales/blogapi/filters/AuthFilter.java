package com.unisales.blogapi.filters;

import com.unisales.blogapi.util.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * cria um componente de filtro para tratar as requisições autenticadas
 */
@Component
public class AuthFilter implements Filter {


//injeta automaticamente a classe de utilidades do JWT
 @Autowired
 private JWTUtil jwtUtil;


 /**
  * esse método recebe todas as requisições e direciona corretamente a resposta
  *
  */
 @Override
 public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
         throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    String caminho = ((HttpServletRequest) request).getRequestURI();
    String token = jwtUtil.recuperaTokenRequisicao(request);
    String nomeUsuario = jwtUtil.getUsuarioNoToken(token);
    Boolean tokenValido = jwtUtil.validaToken(token);
    
    if (nomeUsuario != null && tokenValido) {
     Claims claims = jwtUtil.extraiTodosDados(token);
     //verifica se tem acesso a tarefas
     Boolean acessoTarefa = (Boolean) claims.getOrDefault("TAREFA", false);
     if (acessoTarefa) {
      filterChain.doFilter(request, response);
     }
     
    } else {
     //caso não tenha usuário autenticado, lança uma exceção
     throw new RuntimeException("Token inválido ou inexistente");
    }
 }


}
