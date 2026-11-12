package br.com.verve.verve.usuario;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.verve.verve.exception.EmailJaCadastradoException;

@Service 
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean emailJaCadastrado(String email){
        return usuarioRepository.existsByEmail(email); 
    }

    public void cadastrarUsuario(Usuario usuario){
     if (emailJaCadastrado(usuario.getEmail())) {
        throw new EmailJaCadastradoException("E-mail já cadastrado!");
     }   
     usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
     usuarioRepository.save(usuario);
    }

    

}
