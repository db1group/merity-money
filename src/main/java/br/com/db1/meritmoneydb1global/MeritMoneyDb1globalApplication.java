package br.com.db1.meritmoneydb1global;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("br.com.db1.meritmoneydb1global")
public class MeritMoneyDb1globalApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeritMoneyDb1globalApplication.class, args);
	}

}