package br.com.db1.meritmoney.service;

import br.com.db1.meritmoney.domain.Pessoa;
import br.com.db1.meritmoney.email.NewUserEmailService;
import br.com.db1.meritmoney.enums.Perfil;
import br.com.db1.meritmoney.exceptions.AuthorizationException;
import br.com.db1.meritmoney.exceptions.EmailJaCadastradoException;
import br.com.db1.meritmoney.repository.PessoaRepository;
import br.com.db1.meritmoney.security.UserSS;
import br.com.db1.meritmoney.storage.Disco;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
            throw new EmailJaCadastradoException("Já existe uma pessoa cadastrada com o email '" + email + "'.");
        }

        Pessoa pessoa = new Pessoa();

        pessoa.setNome(email.substring(0,email.indexOf("@")));
        pessoa.setEmail(email);
        String senha = authService.newPassword();
        pessoa.setSenha(bCryptPasswordEncoder.encode(senha));

        newUserEmailService.emailHTMLSenderFromPessoa(pessoa, senha);
        return pessoaRepository.save(pessoa);
    }

    public Page<Pessoa> buscarTodos(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        return pessoaRepository.findAll(pageRequest);
    }


    public Pessoa changePassword(Pessoa pessoa, String password) {
        pessoa.setSenha(bCryptPasswordEncoder.encode(password));
        return pessoaRepository.save(pessoa);
    }

    public Page<Pessoa> buscarTodosPorEquipeId(Long id, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        return pessoaRepository.findAllByEquipeId(id, pageRequest);
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

    public Integer getNumeroDecolaboradoresPorEquipeId(Long id) {
        return pessoaRepository.countByEquipeId(id);
    }
}
