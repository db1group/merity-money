package br.com.db1.meritmoney.configs;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {

    @Test
    void generateEncriptPass() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertEquals("$2a$10$qpkGqLcWvhJ4pxcmldsC1ejSlN.IvMGd7RgDHSTVjKF19vFSXwB9u", encoder.encode("12345"));
    }
}