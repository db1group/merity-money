package br.com.db1.meritmoney.repository;

import br.com.db1.meritmoney.domain.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Page<Pessoa> findAll(Pageable pageable);

    Pessoa findByEmail(String email);

    Page<Pessoa> findAllByEquipeId(Long id, Pageable pageable);

    Integer countByEquipeId(Long id);

    @Query("SELECT a FROM Pessoa a WHERE UPPER(nome) LIKE ?1")
    List<Pessoa> findByNameContendo(String nome);
}
