package uin;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    Clock clockConfiguration() {
        return Clock.systemUTC();
    }

    Jackson2ObjectMapperBuilderCustomizer jacksonCostumier() {
        return builder -> builder
                .propertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy())
                .build();
    }
}
