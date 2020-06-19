package br.com.db1.meritmoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "br.com.db1.meritmoney")
public class MeritMoneyDb1globalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeritMoneyDb1globalApplication.class, args);
	}

}