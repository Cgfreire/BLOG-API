package com.unisales.blogapi.control;

import com.unisales.blogapi.util.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


/**
 *
 * controle responsável pelo endpoint de login na aplicação tendo um usuário e senha válidos, gera
 * um token válido
 */
@RestController
public class LoginController {
//injeta automaticamente a classe de utilidades do JWT


 @Autowired
 private JWTUtil jwtUtil;


 /**
  * recebe um usuario e senha via POST valida se for: 1 - usuario = usuario 2 - senha = 1234
  *
  * pode-se usar um cadastro em banco de dados e assim melhorar a aplicação, por exemplo
  *
  * é usada a anotação @Operation para formatar a entrada de dados na documentação
  *
  * @param usuario
  * @param senha
  * @return
  */
 @PostMapping("/login")
 @Operation(parameters = {
  @Parameter(name = "usuario", schema = @Schema(type = "string")),
  @Parameter(name = "senha", schema = @Schema(type = "string", format = "password"))
 })
 public String logar(String usuario, String senha) {
  if (usuario != null && !usuario.isEmpty() && senha != null && !senha.isEmpty()) {
   if (usuario.equals("usuario") && senha.equals("1234")) {
    //adiciona o acesso (ROLE) TAREFA
    Map acessos = new HashMap();
    acessos.put("TAREFA", true);
    String token = jwtUtil.geraTokenUsuario(usuario, acessos);
    return token;
   } else {
    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "USUÁRIO OU SENHA INVÁLIDOS");
   }
  } else {
   throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "CREDENCIAIS INVÁLIDAS");
  }
 }


}
