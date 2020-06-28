package br.com.db1.meritmoney.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PasswordGenerator {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Random random = new Random();

    public PasswordGenerator(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean matches(String oldPassword, String senha) {
        return bCryptPasswordEncoder.matches(oldPassword, senha);
    }

    public String encode(String newPassword) {
        return bCryptPasswordEncoder.encode(newPassword);
    }

    public String newEncodedPassword() {
        return bCryptPasswordEncoder.encode(newPassword());
    }

    private String newPassword() {
        char[] pass = new char[10];

        for (int i = 0; i < 10; i++) {
            pass[i] = randomChar();
        }
        return new String(pass);
    }

    private char randomChar() {
        switch (random.nextInt(3)) {
            case 0: {
                return (char) (random.nextInt(10) + 48);
            }
            case 1: {
                return (char) (random.nextInt(26) + 65);
            }
            case 2: {
                return (char) (random.nextInt(26) + 97);
            }
        }
        return (char) 40;
    }

}
