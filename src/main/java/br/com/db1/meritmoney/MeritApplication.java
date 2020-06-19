package br.com.db1.meritmoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.db1.meritmoney")
public class MeritApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeritApplication.class, args);
	}

}