package br.com.db1.meritmoney.repository;

import br.com.db1.meritmoney.domain.Equipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EquipeRepository extends JpaRepository<Equipe, Long> {

    Page<Equipe> findAll(Pageable pageable);

    @Query("SELECT a FROM Equipe a WHERE UPPER(nome) LIKE ?1")
    Page<Equipe> findByNameContendo(String nome, Pageable pageable);
}
