
package br.com.verve.verve.usuario;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.verve.verve.exception.EmailJaCadastradoException;
import br.com.verve.verve.exception.UsuarioNaoEncontradoException;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean emailJaCadastrado(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public void cadastrarUsuario(Usuario usuario) {
        if (emailJaCadastrado(usuario.getEmail())) {
            throw new EmailJaCadastradoException("E-mail já cadastrado!");
        }

        usuario.setPerfil(PerfilUsuario.CLIENTE);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsuarioNaoEncontradoException("Usuário não encontrado!")
                );
    }

    public Usuario atualizarUsuario(
            String emailAtual,
            UsuarioAtualizacaoRequest dados) {

        Usuario usuario = buscarPorEmail(emailAtual);

        if (!usuario.getEmail().equals(dados.email())
                && emailJaCadastrado(dados.email())) {

            throw new EmailJaCadastradoException("E-mail já cadastrado!");
        }

        usuario.setNome(dados.nome());
        usuario.setEmail(dados.email());
        usuario.setSenha(passwordEncoder.encode(dados.senha()));

        return usuarioRepository.save(usuario);
    }
}
