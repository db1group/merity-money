package br.com.db1.meritmoney.service;

import br.com.db1.meritmoney.domain.ForgotPassword;
import br.com.db1.meritmoney.domain.Pessoa;
import br.com.db1.meritmoney.email.ForgotPasswordEmailService;
import br.com.db1.meritmoney.exceptions.SenhaInvalidaException;
import br.com.db1.meritmoney.repository.ForgotPasswordRepository;
import br.com.db1.meritmoney.repository.PessoaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class AuthService {

    private final PessoaRepository pessoaRepository;
    private final PessoaService pessoaService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ForgotPasswordEmailService forgotPasswordEmailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final Random random = new Random();

    public AuthService(PessoaRepository pessoaRepository, PessoaService pessoaService,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       ForgotPasswordEmailService forgotPasswordEmailService,
                       ForgotPasswordRepository forgotPasswordRepository) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaService = pessoaService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.forgotPasswordEmailService = forgotPasswordEmailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
    }

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

    public void changePasswordByOldPassword(String email, String oldPassword, String newPassword) {
        Pessoa pessoa = pessoaRepository.findByEmail(email);

        if (!bCryptPasswordEncoder.matches(oldPassword, pessoa.getSenha())) {
            throw new SenhaInvalidaException();
        }

        pessoa.setSenha(bCryptPasswordEncoder.encode(newPassword));
        pessoaRepository.save(pessoa);
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
