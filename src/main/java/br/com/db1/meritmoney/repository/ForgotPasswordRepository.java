package br.com.db1.meritmoney.repository;

import br.com.db1.meritmoney.domain.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {

    ForgotPassword findByHash(String hash);
}
