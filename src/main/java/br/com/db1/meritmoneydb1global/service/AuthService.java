package br.com.db1.meritmoneydb1global.service;

import br.com.db1.meritmoneydb1global.domain.ForgotPassword;
import br.com.db1.meritmoneydb1global.domain.Pessoa;
import br.com.db1.meritmoneydb1global.email.ForgotPasswordEmailService;
import br.com.db1.meritmoneydb1global.repository.ForgotPasswordRepository;
import br.com.db1.meritmoneydb1global.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ForgotPasswordEmailService forgotPasswordEmailService;

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    private Random random = new Random();

    public void forgotPassword(String email) {
        Pessoa pessoa = pessoaRepository.findByEmail(email);

        if (pessoa == null) {
            throw new RuntimeException("Pessoa não encontrada.");
        }

        String hash = UUID.randomUUID().toString().replaceAll("-", "");

        ForgotPassword forgotPassword = new ForgotPassword(pessoa, hash);
        forgotPasswordRepository.save(forgotPassword);

        forgotPasswordEmailService.sendChangePasswordHTMLEmail(pessoa, hash);
    }

    public String getEmailByHash(String hash) {
        ForgotPassword forgotPassword = forgotPasswordRepository.findByHash(hash);
        if (forgotPassword.isUsed()) {
            throw new RuntimeException("Hash já utilizado!");
        }

        Date dataAtual = new Date(System.currentTimeMillis());
        long expiration = (dataAtual.getTime() - forgotPassword.getDateTime().getTime())/3600000;
        if (expiration > 24) {
            throw new RuntimeException("Hash expirado!");
        }
        return forgotPassword.getPessoa().getEmail();
    }

    public void changePasswordByHash(String hash, String password) {
        ForgotPassword forgotPassword = forgotPasswordRepository.findByHash(hash);
        Pessoa pessoa = pessoaRepository.findByEmail(getEmailByHash(hash));
        pessoaService.changePassword(pessoa, password);
        forgotPassword.setUsed(true);
        forgotPasswordRepository.save(forgotPassword);
    }

    public String newPassword() {
        char[] pass = new char[10];

        for (int i = 0; i < 10; i++) {
            pass[i] = randomChar();
        }
        return new String(pass);
    }

    private char randomChar() {
        switch (random.nextInt(3)) {
            case 0: {
                return (char) (random.nextInt(10) + 48);
            }
            case 1: {
                return (char) (random.nextInt(26) + 65);
            }
            case 2: {
                return (char) (random.nextInt(26) + 97);
            }
        }
        return (char) 40;
    }
}
