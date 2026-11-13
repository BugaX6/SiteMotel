package br.com.verve.verve.admin;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.verve.verve.exception.EmailJaCadastradoException;
import br.com.verve.verve.exception.UsuarioNaoEncontradoException;

@Service 
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(
        AdminRepository adminRepository,
        PasswordEncoder passwordEncoder) {

            this.adminRepository = adminRepository;
            this.passwordEncoder = passwordEncoder;
        }

    
  public boolean emailJaCadastrado(String email) {
        return adminRepository.existsByEmail(email);
    }

        public void cadastrarAdmin(Admin admin) {
        if (emailJaCadastrado(admin.getEmail())) {
            throw new EmailJaCadastradoException("E-mail já cadastrado!");
        }

        admin.setSenha(passwordEncoder.encode(admin.getSenha()));
        adminRepository.save(admin);
    }

       public Admin buscarPorEmail(String email) {
        return adminRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsuarioNaoEncontradoException("Usuário não encontrado!")
                );
    } 

    
}
