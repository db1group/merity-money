package br.com.db1.meritmoneydb1global.service;

import br.com.db1.meritmoneydb1global.domain.Equipe;
import br.com.db1.meritmoneydb1global.repository.EquipeRepository;
import br.com.db1.meritmoneydb1global.service.mapper.EquipeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EquipeService {

    private EquipeRepository equipeRepository;

    public EquipeService(EquipeRepository equipeRepository, EquipeMapper equipeMapper) {
        this.equipeRepository = equipeRepository;
    }

    public Equipe salvarEquipe(Equipe equipe) {
        return equipeRepository.save(equipe);
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
}
