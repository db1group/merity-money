package br.com.db1.meritmoney.resources;

import br.com.db1.meritmoney.security.JWTUtil;
import br.com.db1.meritmoney.security.UserSS;
import br.com.db1.meritmoney.service.AuthService;
import br.com.db1.meritmoney.service.UserService;
import br.com.db1.meritmoney.service.dto.EmailDTO;
import br.com.db1.meritmoney.service.dto.PessoaDto;
import br.com.db1.meritmoney.service.dto.TrocaSenhaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @Autowired
    private PessoaResource pessoaResource;


    @PostMapping(value = "/refresh_token")
    public ResponseEntity<Void> refreshTolken(HttpServletResponse response) throws IOException {
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        PrintWriter writer = response.getWriter();
        writer.println("Bearer " + token);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/get_usuario")
    public ResponseEntity<PessoaDto> getUsuario() {
        UserSS user = UserService.authenticated();

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setEmail(user.getUsername());

        return pessoaResource.buscarPorEmail(emailDTO);
    }

    @GetMapping(value = "/is_valid_token")
    public ResponseEntity<Boolean> isValidToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(token.indexOf(" ")+1);
        return ResponseEntity.ok(jwtUtil.isTokenValid(token));
    }

    @PostMapping(value = "/forgot")
    public void forgotPassword(@Valid @RequestBody EmailDTO email) {
        authService.forgotPassword(email.getEmail());
    }

    @GetMapping(value = "/findEmailByHash/{hash}")
    public String findEmailByHash(@PathVariable("hash") String hash) {
        return authService.getEmailByHash(hash);
    }

    @PostMapping("/changePasswordByHash")
    public void changePasswordByHash(@RequestBody TrocaSenhaDto trocaSenhaDto) {
        authService.changePasswordByHash(trocaSenhaDto.getHash(), trocaSenhaDto.getNewPassword());
    }
}
