package br.com.db1.meritmoney.resources;

import br.com.db1.meritmoney.service.EquipeService;
import br.com.db1.meritmoney.service.dto.EquipeDto;
import br.com.db1.meritmoney.service.mapper.EquipeMapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("equipes")
public class EquipeResource {

    private EquipeService equipeService;
    private ConversionService conversionService;
    private EquipeMapper equipeMapper;

    public EquipeResource(EquipeService equipeService, ConversionService conversionService, EquipeMapper equipeMapper) {
        this.equipeService = equipeService;
        this.conversionService = conversionService;
        this.equipeMapper = equipeMapper;
    }

    //@PreAuthorize("(hasAnyRole('ADMIN')) OR (hasAnyRole('MOD'))")
    @PostMapping("/cadastrar")
    public EquipeDto cadastrarEquipe(@RequestBody EquipeDto equipeDto) {
        return equipeMapper.toDto(equipeService
                .salvarEquipe(equipeMapper.toEntity(equipeDto)));
    }

    @PreAuthorize("(hasAnyRole('ADMIN')) OR (hasAnyRole('MOD'))")
    @PutMapping("/alterar")
    public EquipeDto alterarEquipe(@RequestBody EquipeDto equipeDto) {
        return equipeMapper.toDto(equipeService
                .salvarEquipe(equipeMapper.toEntity(equipeDto)));
    }

    @GetMapping(produces="application/json")
    @Transactional(readOnly = true)
    public @ResponseBody List<EquipeDto> listarEquipes() {
        return equipeService.buscarTodos()
                .stream()
                .map(equipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/buscar/{id}")
    @Transactional(readOnly = true)
    public @ResponseBody EquipeDto buscarPorId(@PathVariable("id") Long id) {
        return equipeMapper.toDto(equipeService.buscarPorId(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    public EquipeDto deletaEquipe(@PathVariable("id") Long id) {
        return equipeMapper.toDto(equipeService.deletarEquipe(id));
    }

}
