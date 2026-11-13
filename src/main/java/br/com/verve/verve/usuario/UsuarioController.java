
package br.com.verve.verve.usuario;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrarUsuario(
            @Valid @RequestBody Usuario usuario) {

        usuarioService.cadastrarUsuario(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResposta> buscarUsuarioAutenticado(
            Authentication authentication) {

        Usuario usuario = usuarioService.buscarPorEmail(
                authentication.getName()
        );

        UsuarioResposta resposta = new UsuarioResposta(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );

        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/me")
    public ResponseEntity<UsuarioResposta> atualizarUsuarioAutenticado(
            Authentication authentication,
            @Valid @RequestBody UsuarioAtualizacaoRequest dados) {

        Usuario usuario = usuarioService.atualizarUsuario(
                authentication.getName(),
                dados
        );

        UsuarioResposta resposta = new UsuarioResposta(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );

        return ResponseEntity.ok(resposta);
    }

    public record UsuarioResposta(
            Long id,
            String nome,
            String email
    ) {}
}
