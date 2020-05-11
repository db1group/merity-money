package br.com.db1.meritmoneydb1global.repository;

import br.com.db1.meritmoneydb1global.domain.Pessoa;
import br.com.db1.meritmoneydb1global.domain.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findAllByDestinatarioIdOrderByDateTimeDesc(Long id);

    List<Transacao> findAllByRemetenteIdOrderByDateTimeDesc(Long id);

    @Query("SELECT count(a) FROM Transacao a WHERE remetente = ?1")
    Integer countByRemetente(Pessoa pessoa);

    @Query("SELECT count(a) FROM Transacao a WHERE destinatario = ?1")
    Integer countByDestinatario(Pessoa pessoa);

    @Query("SELECT max(dateTime) FROM Transacao a WHERE remetente = ?1")
    Timestamp findLastByRemetente(Pessoa pessoa);

    @Query("SELECT max(dateTime) FROM Transacao a WHERE destinatario = ?1")
    Timestamp findLastByDestinatario(Pessoa pessoa);
}
