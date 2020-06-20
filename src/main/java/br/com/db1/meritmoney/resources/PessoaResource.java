package br.com.db1.meritmoney.resources;

import br.com.db1.meritmoney.domain.Pessoa;
import br.com.db1.meritmoney.domain.Transacao;
import br.com.db1.meritmoney.exceptions.AuthorizationException;
import br.com.db1.meritmoney.security.UserSS;
import br.com.db1.meritmoney.service.PessoaService;
import br.com.db1.meritmoney.service.TransacaoService;
import br.com.db1.meritmoney.service.UserService;
import br.com.db1.meritmoney.service.dto.EmailDTO;
import br.com.db1.meritmoney.service.dto.EquipeDto;
import br.com.db1.meritmoney.service.dto.PessoaDto;
import br.com.db1.meritmoney.service.dto.UserDto;
import br.com.db1.meritmoney.service.mapper.PessoaMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@Transactional
@RequestMapping("/pessoas")
public class PessoaResource {

    private PessoaService pessoaService;
    private TransacaoService transacaoService;
    private PessoaMapper pessoaMapper;

    public PessoaResource(PessoaService pessoaService, TransacaoService transacaoService, PessoaMapper pessoaMapper) {
        this.pessoaService = pessoaService;
        this.transacaoService = transacaoService;
        this.pessoaMapper = pessoaMapper;
    }

    @PostMapping("/cadastrar-por-email")
    public PessoaDto cadastrarPorEmail(@RequestBody EmailDTO emailDTO) {
        return toDto(pessoaService.cadastrar(emailDTO.getEmail()));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/cadastrar")
    public ResponseEntity<PessoaDto> cadastraPessoa(@RequestBody PessoaDto pessoa) {
        return ResponseEntity.ok(toDto(pessoaService
                        .salvarPessoa(toEntity(pessoa))));
    }

    @PutMapping("/alterar")
    public ResponseEntity<PessoaDto> alterarPessoa(@RequestBody PessoaDto pessoaDto) {
        UserSS user = UserService.authenticated();
        Pessoa pessoa = pessoaService.buscarPorEmail(pessoaDto.getEmail());

        if (user == null || !pessoa.getId().equals(user.getId())) {
            throw new AuthorizationException();
        }

        return ResponseEntity.ok(toDto(pessoaService
                .salvarPessoa(toEntity(pessoaDto))));
    }

    @GetMapping(produces="application/json", value = "/buscar/{page}/{size}") @Transactional(readOnly = true)
    public ResponseEntity<Page<PessoaDto>> listarPessoas(
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size
    ) {
        return ResponseEntity.ok(pessoaService.buscarTodos(page, size)
                .map(pessoa -> toDto(pessoa)));
    }

    @GetMapping(produces="application/json", value = "/buscar-por-equipe/{id}/{page}/{size}") @Transactional(readOnly = true)
    public ResponseEntity<Page<PessoaDto>> listarPessoasPorEquipe(
            @PathVariable("id") Long id,
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size
    ) {
        return ResponseEntity.ok(pessoaService.buscarTodosPorEquipeId(id, page, size)
                .map(pessoa -> {
                    return toDto(pessoa);
                }));
    }

    @PostMapping(value = "/buscar-por-email") @Transactional(readOnly = true)
    public ResponseEntity<PessoaDto> buscarPorEmail(@RequestBody EmailDTO emailDTO) {
        return ResponseEntity.ok(toDto(pessoaService.buscarPorEmail(emailDTO.getEmail())));
    }

    @PostMapping(value = "/trocar-senha")
    public ResponseEntity<PessoaDto> trocarSenha(@Valid @RequestBody UserDto userDto){
        UserSS user = UserService.authenticated();
        Pessoa pessoa = pessoaService.buscarPorEmail(userDto.getEmail());

        if (user == null || !pessoa.getId().equals(user.getId())) {
            throw new AuthorizationException();
        }

        return ResponseEntity.ok(toDto(pessoaService.changePassword(pessoa, userDto.getSenha())));
    }

    @PostMapping(value = "/trocar-foto")
    public ResponseEntity<String> uploadFoto(@RequestParam("file") MultipartFile foto) throws IOException {
        return ResponseEntity.ok(pessoaService.trocarFoto(foto));
    }

    @GetMapping(value = "/{id}") @Transactional(readOnly = true)
    public @ResponseBody ResponseEntity<PessoaDto> buscarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(toDto(pessoaService.buscarPorId(id)));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<PessoaDto> deletarPessoa(@PathVariable("id") Long id) {
        return ResponseEntity.ok(pessoaMapper.toDto(pessoaService.deletarPessoa(id)));
    }

    @GetMapping(value = "/busca/{keyword}")
    public @ResponseBody ResponseEntity<List<PessoaDto>> buscarPorKeyword(@PathVariable("keyword") String keyword) {
        return ResponseEntity.ok(pessoaService.buscarPorKeyword("%" + keyword.toUpperCase() + "%")
                .stream()
                .map(pessoa -> toDto(pessoa))
                .collect(Collectors.toList()));
    }

    public BigDecimal calcularSaldoPorId(Long id) {
        BigDecimal valorDebito = calcularDebitoPorId(id);
        BigDecimal valorCredito = calcularCreditoPorId(id);
        return BigDecimal.valueOf(100).add(valorCredito.subtract(valorDebito));
    }

    public BigDecimal calcularCreditoPorId(Long id) {
        return calcularValor(transacaoService.buscarDestinatarioPorId(id));
    }

    public BigDecimal calcularDebitoPorId(Long id) {
        return calcularValor(transacaoService.buscarRemetentePorId(id));
    }

    private BigDecimal calcularValor(List<Transacao> lista) {
        return lista
                .stream()
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, (anterior, atual) -> atual.add(anterior));
    }

    public PessoaDto toDto(Pessoa pessoa) {
        PessoaDto pessoaDto = pessoaMapper.toDto(pessoa);
        Long id = pessoaDto.getId();

        if (pessoaDto.getEquipe() == null) {
            EquipeDto equipe = getEquipeVazia();
            pessoaDto.setEquipe(equipe);
        }

        pessoaDto.setLinkedin(pessoa.getLinkedin());
        pessoaDto.setCredito(calcularCreditoPorId(id));
        pessoaDto.setDebito(calcularDebitoPorId(id));
        pessoaDto.setSaldo(calcularSaldoPorId(id));

        return pessoaDto;
    }

    private Pessoa toEntity(PessoaDto pessoaDto) {
        Pessoa pessoa = pessoaService.buscarPorId(pessoaDto.getId());

        pessoa.setNome(pessoaDto.getNome());
        pessoa.setEmail(pessoaDto.getEmail());
        pessoa.setLinkedin(pessoaDto.getLinkedin());

        return pessoa;
    }

    private EquipeDto getEquipeVazia() {
        EquipeDto equipe = new EquipeDto();
        equipe.setId((long) 0);
        equipe.setNome("Sem equipe");
        equipe.setDescricao("Sem equipe");
        return equipe;
    }
}
