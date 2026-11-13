package br.com.verve.verve.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/admin") 
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    
    @PostMapping 
    public ResponseEntity<Void> cadastrarAdmin(
        @Valid @RequestBody Admin admin){

            adminService.cadastrarAdmin(admin);

            return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping ("/me2")
    public ResponseEntity<AdminResposta> buscaAdminAutenticado(
        Authentication authentication) {
            Admin admin = adminService.buscarPorEmail(
                authentication.getName()
            );
            AdminResposta resposta = new AdminResposta (
                admin.getNome(),
                admin.getEmail()

            );
            return ResponseEntity.ok(resposta);
        }

        public record AdminResposta(
            String nome,
            String email
        ) {}

    
}
