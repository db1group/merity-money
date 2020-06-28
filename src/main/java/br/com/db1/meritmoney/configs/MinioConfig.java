package br.com.db1.meritmoney.configs;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//todo, dessa forma a criaçao do client se liga com o start do projeto, vai gerar uma amarracao
//o projeto só sobe se tiver um minio server online
//verificar formas de contornar
@Configuration
public class MinioConfig {

    @Value("#{environment['application.minio.host']?:'http://localhost:39000'}")
    private String minioHost;

    @Value("#{environment['application.minio.accessKey']?:'accessKey'}")
    private String minioAccess;

    @Value("#{environment['application.minio.secretKey']?:'secretKey'}")
    private String minioSecret;

    @Bean
    MinioClient minioClientBean() throws InvalidPortException, InvalidEndpointException {
        MinioClient minioClient = new MinioClient(minioHost, minioAccess, minioSecret);
        return minioClient;
    }


}