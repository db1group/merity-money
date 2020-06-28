package br.com.db1.meritmoney.service;

import br.com.db1.meritmoney.domain.Equipe;
import br.com.db1.meritmoney.repository.EquipeRepository;
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
public class EquipeService {

    private final EquipeRepository equipeRepository;
    private final ImagesService imagesService;
    private final PessoaService pessoaService;

    public EquipeService(EquipeRepository equipeRepository, ImagesService imagesService, PessoaService pessoaService) {
        this.equipeRepository = equipeRepository;
        this.imagesService = imagesService;
        this.pessoaService = pessoaService;
    }

    public Equipe salvarEquipe(Equipe equipe) {
        return equipeRepository.save(equipe);
    }

    public Page<Equipe> buscarTodos(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        return equipeRepository.findAll(pageRequest);
    }

    public List<Equipe> buscarTodos() {
        return equipeRepository.findAll();
    }

    public Equipe buscarPorId(Long id) {
        return equipeRepository.getOne(id);
    }

    public Equipe deletarEquipe(Long id) {
        Equipe equipe = equipeRepository.getOne(id);
        equipeRepository.deleteById(id);
        return equipe;
    }

    public String trocarFoto(MultipartFile foto, Long equipeId) {
        Equipe equipe = equipeRepository.getOne(equipeId);
        ImageFileVO saved = imagesService.salvarFoto(foto, equipeId.toString(), EImagesNames.TEAM_PHOTO);

        equipe.setPathFoto(saved.getUrl());
        equipeRepository.save(equipe);

        return saved.getUrl();
    }

    public Integer getNumeroDeColaboradoresPorId(Long id) {
        return pessoaService.getNumeroDecolaboradoresPorEquipeId(id);
    }

    public Page<Equipe> buscarPorNome(String keyword, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        return equipeRepository.findByNameContendo("%" + keyword.toUpperCase() + "%", pageRequest);
    }
}
