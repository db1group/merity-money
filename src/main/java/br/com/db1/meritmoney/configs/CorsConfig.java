package br.com.db1.meritmoney.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Value("#{environment['cors.allow.origins']?:''.split(',')}")
    private List<String> allowOrigin = new ArrayList<>();

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "Content-Length", "X-Requested-With"));
        configuration.setMaxAge(3600L);
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(getListAllowedOrigins());
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private List<String> getListAllowedOrigins() {
        List<String> alloweds = new ArrayList<>();
        if(allowOrigin.isEmpty() || allowOrigin.get(0).isEmpty()) {
               alloweds.add("*");
        }
        if(!allowOrigin.isEmpty() && allowOrigin.get(0).isEmpty()) {
            alloweds.addAll(allowOrigin);
        }
        return alloweds;
    }

}