package uin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import uin.AccountRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final int STRENGTH = 10;

    @Bean
    UserDetailsService userDetailsService(AccountRepository repository) {
        return username -> repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not exits"));
    }

    @Bean
    SecurityFilterChain basicAuth(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(req ->
                        req.anyRequest().permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(STRENGTH);
    }

    @Bean
    AuthenticationManager authenticationManagerConfig(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
