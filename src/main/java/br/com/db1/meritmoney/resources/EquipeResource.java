package br.com.db1.meritmoney.resources;

import br.com.db1.meritmoney.domain.Equipe;
import br.com.db1.meritmoney.service.EquipeService;
import br.com.db1.meritmoney.service.dto.EquipeDto;
import br.com.db1.meritmoney.service.mapper.EquipeMapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PreAuthorize("(hasAnyRole('ADMIN')) OR (hasAnyRole('MOD'))")
    @PostMapping("/cadastrar")
    public ResponseEntity<EquipeDto> cadastrarEquipe(@RequestBody EquipeDto equipeDto) {
        return ResponseEntity.ok(toDto(equipeService
                .salvarEquipe(equipeMapper.toEntity(equipeDto))));
    }

    @PreAuthorize("(hasAnyRole('ADMIN')) OR (hasAnyRole('MOD'))")
    @PutMapping("/alterar")
    public ResponseEntity<EquipeDto> alterarEquipe(@RequestBody EquipeDto equipeDto) {
        return ResponseEntity.ok(toDto(equipeService
                .salvarEquipe(equipeMapper.toEntity(equipeDto))));
    }

    @GetMapping(produces="application/json", value = "/buscar/{page}/{size}")
    @Transactional(readOnly = true)
    public @ResponseBody ResponseEntity<Page<EquipeDto>> listarEquipes(
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size
    ) {
        return ResponseEntity.ok(equipeService.buscarTodos(page, size)
                .map(equipe -> toDto(equipe)));
    }

    @GetMapping(produces="application/json")
    @Transactional(readOnly = true)
    public @ResponseBody ResponseEntity<List<EquipeDto>> buscarPorNome() {
        return ResponseEntity.ok(equipeService.buscarTodos()
                .stream()
                .map(equipe -> toDto(equipe))
                .collect(Collectors.toList())
        );
    }

    @GetMapping(produces="application/json", value = "/buscar/{keyword}/{page}/{size}")
    @Transactional(readOnly = true)
    public @ResponseBody ResponseEntity<Page<EquipeDto>> buscarPorNome(
            @PathVariable("keyword") String keyword,
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size
    ) {
        return ResponseEntity.ok(equipeService.buscarPorNome(keyword, page, size)
                .map(equipe -> toDto(equipe))
        );
    }

    @GetMapping(value = "/buscar/{id}")
    @Transactional(readOnly = true)
    public @ResponseBody ResponseEntity<EquipeDto> buscarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(toDto(equipeService.buscarPorId(id)));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<EquipeDto> deletaEquipe(@PathVariable("id") Long id) {
        return ResponseEntity.ok(toDto(equipeService.deletarEquipe(id)));
    }

    @PostMapping(value = "/trocar-foto")
    public ResponseEntity<String> uploadFoto(@RequestParam("file") MultipartFile foto, Long equipeId) throws IOException {
        return ResponseEntity.ok(equipeService.trocarFoto(foto, equipeId));
    }

    private EquipeDto toDto(Equipe equipe) {
        EquipeDto equipeDto = equipeMapper.toDto(equipe);
        equipeDto.setNumeroDeColaboradores(equipeService.getNumeroDeColaboradoresPorId(equipe.getId()));
        return equipeDto;
    }

}
