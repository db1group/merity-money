package br.com.db1.meritmoneydb1global.service;

import br.com.db1.meritmoneydb1global.domain.Pessoa;
import br.com.db1.meritmoneydb1global.email.NewUserEmailService;
import br.com.db1.meritmoneydb1global.enums.Perfil;
import br.com.db1.meritmoneydb1global.exceptions.AuthorizationException;
import br.com.db1.meritmoneydb1global.repository.PessoaRepository;
import br.com.db1.meritmoneydb1global.security.UserSS;
import br.com.db1.meritmoneydb1global.storage.Disco;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.processing.FilerException;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@Transactional
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private Disco disco;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthService authService;

    @Autowired
    private NewUserEmailService newUserEmailService;

    public Pessoa salvarPessoa(Pessoa pessoa) throws AuthorizationException {
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !pessoa.getId().equals(user.getId())) {
            throw new AuthorizationException();
        }

        return pessoaRepository.save(pessoa);
    }

    public Pessoa cadastrar(String email) {
        if (pessoaRepository.findByEmail(email) != null) {
            throw new RuntimeException("Já existe uma pessoa cadastrada com o email '" + email + "'.");
        }

        Pessoa pessoa = new Pessoa();

        pessoa.setNome(email.substring(0,email.indexOf("@")));
        pessoa.setEmail(email);
        String senha = authService.newPassword();
        pessoa.setSenha(bCryptPasswordEncoder.encode(senha));

        newUserEmailService.emailHTMLSenderFromPessoa(pessoa, senha);
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> buscarTodos() {
        return pessoaRepository.findAll();
    }


    public Pessoa changePassword(Pessoa pessoa, String password) {
        pessoa.setSenha(bCryptPasswordEncoder.encode(password));
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> buscarTodosPorEquipeId(Long id) {
        return pessoaRepository.findAllByEquipeId(id);
    }

    public Pessoa buscarPorId(Long id) {
        return pessoaRepository.getOne(id);
    }

    public List<Pessoa> buscarPorKeyword(String keyword) {
        return pessoaRepository.findByNameContendo(keyword);
    }

    public Pessoa buscarPorEmail(String email) {
        return pessoaRepository.findByEmail(email);
    }

    public Pessoa deletarPessoa(Long id) {
        Pessoa pessoa = pessoaRepository.getOne(id);
        pessoaRepository.deleteById(id);
        return pessoa;
    }

    public String trocarFoto(MultipartFile foto) throws IOException {
        Pessoa pessoa = pessoaRepository.findByEmail(UserService.authenticated().getUsername());
        String path = disco.salvarFoto(foto);

        byte[] imgContent = FileUtils.readFileToByteArray(new File(path));
        String encodedString = "data:image.jpg;base64," + Base64.getEncoder().encodeToString(imgContent);

        pessoa.setPathFoto(encodedString);

        pessoaRepository.save(pessoa);

        return encodedString;
    }
}
