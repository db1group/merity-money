package br.com.db1.meritmoney.resources;

import br.com.db1.meritmoney.domain.Pessoa;
import br.com.db1.meritmoney.domain.Transacao;
import br.com.db1.meritmoney.exceptions.DestinatarioInvalidoException;
import br.com.db1.meritmoney.exceptions.RemetenteInvalidoException;
import br.com.db1.meritmoney.exceptions.SaldoInsuficienteException;
import br.com.db1.meritmoney.repository.TransacaoRepository;
import br.com.db1.meritmoney.service.PessoaService;
import br.com.db1.meritmoney.service.TransacaoService;
import br.com.db1.meritmoney.service.UserService;
import br.com.db1.meritmoney.service.dto.PessoaDto;
import br.com.db1.meritmoney.service.dto.TransacaoDto;
import br.com.db1.meritmoney.service.dto.TransacaoInfosDto;
import br.com.db1.meritmoney.service.mapper.TransacaoMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional
@RequestMapping("/transacoes")
public class TransacaoResource {

    private TransacaoService transacaoService;
    private PessoaResource pessoaResource;
    private TransacaoMapper transacaoMapper;
    private PessoaService pessoaService;
    private TransacaoRepository transacaoRepository;

    public TransacaoResource(TransacaoService transacaoService,
                             PessoaResource pessoaResource,
                             TransacaoMapper transacaoMapper,
                             PessoaService pessoaService,
                             TransacaoRepository transacaoRepository) {
        this.transacaoService = transacaoService;
        this.pessoaResource = pessoaResource;
        this.transacaoMapper = transacaoMapper;
        this.pessoaService = pessoaService;
        this.transacaoRepository = transacaoRepository;
    }

    @GetMapping(produces="application/json") @Transactional(readOnly = true)
    public @ResponseBody ResponseEntity<List<TransacaoDto>> listarTransacoes() {
        List<Transacao> transacoes = transacaoService.listarTransacoes();
        return ResponseEntity.ok(transacoes.stream()
                .map(this::toDto)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/buscar/{id}") @Transactional(readOnly = true)
    public @ResponseBody ResponseEntity<TransacaoDto> buscarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(toDto(transacaoService.buscarPorId(id)));
    }

    @PostMapping("/nova-transacao")
    public ResponseEntity<TransacaoDto> enviarMoney(@RequestBody TransacaoDto transacaoDto) throws MessagingException {
        if (transacaoDto.getRemetente().getId().equals(transacaoDto.getDestinatario().getId())) {
            throw new DestinatarioInvalidoException();
        }
        if (!transacaoDto.getRemetente().getEmail().equals(UserService.authenticated().getUsername())) {
            throw new RemetenteInvalidoException();
        }
        BigDecimal saldo = pessoaResource.calcularSaldoPorId(transacaoDto.getRemetente().getId());
        if (saldo.compareTo(transacaoDto.getValor()) < 0) {
            throw new SaldoInsuficienteException();
        }

        transacaoDto.setDateTime(new Timestamp(new Date().getTime()));
        return ResponseEntity.ok(toDto(transacaoService.salvarTransacao(toEntity(transacaoDto))));

    }

    @GetMapping(value = "/total/{id}")
    public @ResponseBody ResponseEntity<List<TransacaoDto>> buscarPorPessoaId(
            @PathVariable("id") Long id
    ) {
        List<Transacao> transacoes = transacaoService.buscarRemetentePorId(id);
        transacoes.addAll(transacaoService.buscarDestinatarioPorId(id));
        return ResponseEntity.ok(transacoes.stream()
                .map(transacao -> toDto(transacao))
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/envios/{id}/{page}/{size}")
    public @ResponseBody ResponseEntity<Page<TransacaoDto>> buscarPorRemetenteId(
            @PathVariable("id") Long id,
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size
    ) {
        return ResponseEntity.ok(transacaoService.buscarRemetentePorId(id, page, size)
                .map(transacao -> toDto(transacao)));
    }

    @GetMapping(value = "/recebidos/{id}/{page}/{size}")
    public @ResponseBody ResponseEntity<Page<TransacaoDto>> buscarPorDestinatarioId(
            @PathVariable("id") Long id,
            @PathVariable("page") Integer page,
            @PathVariable("size") Integer size
    ) {
        return ResponseEntity.ok(transacaoService.buscarDestinatarioPorId(id, page, size)
                .map(transacao -> toDto(transacao)));
    }

    @GetMapping(value = "/infos/{id}")
    public @ResponseBody ResponseEntity<TransacaoInfosDto> buscarInfos(@PathVariable("id") Long id) {
        TransacaoInfosDto transacaoInfosDto = new TransacaoInfosDto();
        Pessoa pessoa = pessoaService.buscarPorId(id);
        transacaoInfosDto.setEnvios(transacaoService.contarPorRemetente(pessoa));
        transacaoInfosDto.setRecebidos(transacaoService.contarPorDestinatario(pessoa));
        transacaoInfosDto.setUltimoEnvio(transacaoService.buscarUltimoEnvio(pessoa));
        transacaoInfosDto.setUltimoRecebido(transacaoService.buscarUltimoRecebido(pessoa));
        transacaoInfosDto.setTotal(transacaoInfosDto.getEnvios() + transacaoInfosDto.getRecebidos());

        return ResponseEntity.ok(transacaoInfosDto);
    }

    private PessoaDto setConta(PessoaDto pessoa) {
        Long id = pessoa.getId();

        pessoa.setSaldo(pessoaResource.calcularSaldoPorId(id));
        pessoa.setDebito(pessoaResource.calcularDebitoPorId(id));
        pessoa.setCredito(pessoaResource.calcularCreditoPorId(id));

        return pessoa;
    }

    private TransacaoDto toDto(Transacao transacao) {
        TransacaoDto transacaoDto = transacaoMapper.toDto(transacao);
        PessoaDto destinatario = setConta(transacaoDto.getDestinatario());
        PessoaDto remetente = setConta(transacaoDto.getRemetente());

        transacaoDto.setDestinatario(destinatario);
        transacaoDto.setRemetente(remetente);

        return transacaoDto;
    }

    private Transacao toEntity(TransacaoDto transacaoDto) {
        Transacao transacao = transacaoMapper.toEntity(transacaoDto);
        transacao.setMensagem(transacaoDto.getMensagem());
        transacao.setDestinatario(pessoaService.buscarPorId(transacaoDto.getDestinatario().getId()));
        transacao.setRemetente(pessoaService.buscarPorId(transacaoDto.getRemetente().getId()));
        transacao.setValor(transacaoDto.getValor());
        transacao.setDateTime(transacaoDto.getDateTime());
        return transacao;
    }

}
