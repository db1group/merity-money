package br.com.db1.meritmoney.repository;

import br.com.db1.meritmoney.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Pessoa findByEmail(String email);

    List<Pessoa> findAllByEquipeId(Long id);

    @Query("SELECT a FROM Pessoa a WHERE UPPER(nome) LIKE ?1")
    List<Pessoa> findByNameContendo(String nome);
}
