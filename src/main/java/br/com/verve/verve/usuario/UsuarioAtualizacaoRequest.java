package br.com.verve.verve.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioAtualizacaoRequest(
     @NotBlank 
    String nome,

    @NotBlank 
    @Email 
    String email,

    @NotBlank
    String senha
) {}
