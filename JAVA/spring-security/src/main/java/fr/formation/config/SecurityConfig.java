package fr.formation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Configuration des accès
        http.authorizeHttpRequests(authorize -> {
            // authorize.requestMatchers("/api/produit").hasRole("ADMIN");
            authorize.requestMatchers("/api/produit").hasAuthority("ROLE_ADMIN");

            authorize.requestMatchers("**").authenticated();
        });

        // Authentification par formulaire de connexion
        http.formLogin(Customizer.withDefaults());

        // Authentification par Http Basic
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    UserDetailsService inMemoryUsers(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager inMemory = new InMemoryUserDetailsManager();

        inMemory.createUser(User.withUsername("jeremy")
            // {noop} Pas d'encodeur
            // {bcrypt} Blowfish
            // .password("{noop}123456$")
            // .password(passwordEncoder.encode("123456$"))
            .password("$2a$10$FteyG/CJdvLE4uPv2nwwU.pK59nQAWCaUh9YkB1YErQPRFlU8puN2")
            .roles("ADMIN")
            .build()
        );

        inMemory.createUser(User.withUsername("user")
            .password(passwordEncoder.encode("123456$"))
            .roles("USER")
            .build()
        );

        return inMemory;
    }

}
