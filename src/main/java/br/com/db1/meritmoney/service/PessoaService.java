package br.com.db1.meritmoney.service;

import br.com.db1.meritmoney.domain.Pessoa;
import br.com.db1.meritmoney.email.NewUserEmailService;
import br.com.db1.meritmoney.enums.Perfil;
import br.com.db1.meritmoney.exceptions.AuthorizationException;
import br.com.db1.meritmoney.exceptions.EmailJaCadastradoException;
import br.com.db1.meritmoney.repository.PessoaRepository;
import br.com.db1.meritmoney.security.UserSS;
import br.com.db1.meritmoney.storage.EImagesNames;
import br.com.db1.meritmoney.storage.ImageFileVO;
import br.com.db1.meritmoney.storage.ImagesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final ImagesService imagesService;
    private final PasswordGenerator passwordGenerator;
    private final NewUserEmailService newUserEmailService;

    public PessoaService(PessoaRepository pessoaRepository, ImagesService imagesService,
                         PasswordGenerator passwordGenerator,
                         NewUserEmailService newUserEmailService) {
        this.pessoaRepository = pessoaRepository;
        this.imagesService = imagesService;
        this.passwordGenerator = passwordGenerator;
        this.newUserEmailService = newUserEmailService;
    }

    public Pessoa salvarPessoa(Pessoa pessoa) throws AuthorizationException {
        UserSS user = UserContextUtil.authenticated();
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
        String senha = passwordGenerator.newEncodedPassword();
        pessoa.setSenha(senha);

        newUserEmailService.emailHTMLSenderFromPessoa(pessoa, senha);
        return pessoaRepository.save(pessoa);
    }

    public Page<Pessoa> buscarTodos(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        return pessoaRepository.findAll(pageRequest);
    }


    public Pessoa changePassword(Pessoa pessoa, String password) {
        pessoa.setSenha(passwordGenerator.encode(password));
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

    public String trocarFoto(MultipartFile foto) {
        Pessoa pessoa = pessoaRepository.findByEmail(UserContextUtil.authenticated().getUsername());
        ImageFileVO saved = imagesService.salvarFoto(foto, pessoa.getId().toString(), EImagesNames.PROFILE_PHOTO);

        pessoa.setPathFoto(saved.getUrl());

        pessoaRepository.save(pessoa);

        return saved.getUrl();
    }

    public Integer getNumeroDecolaboradoresPorEquipeId(Long id) {
        return pessoaRepository.countByEquipeId(id);
    }
}
