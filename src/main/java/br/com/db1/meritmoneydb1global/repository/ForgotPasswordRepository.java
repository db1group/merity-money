package br.com.db1.meritmoneydb1global.repository;

import br.com.db1.meritmoneydb1global.domain.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {

    ForgotPassword findByHash(String hash);
}
