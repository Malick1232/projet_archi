package com.siteactualites.config;

import com.siteactualites.repository.UtilisateurRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/", "/article/**", "/categorie/**", "/error",
        "/css/**", "/js/**", "/images/**")
                .permitAll()

                .requestMatchers("/editeur/**")
                .hasAnyRole("EDITEUR", "ADMIN")

                .requestMatchers("/admin/**")
                .hasRole("ADMIN")

                .anyRequest()
                .authenticated()

            )

            .formLogin(Customizer.withDefaults())

            .logout(logout -> logout
                    .logoutSuccessUrl("/")
                    .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Authentification basée sur la table utilisateur :
     * Spring compare le mot de passe saisi au hash BCrypt stocké en base
     * et attribue à la session le rôle de l'utilisateur (ROLE_ADMIN / ROLE_EDITEUR).
     */
    @Bean
    public UserDetailsService userDetailsService(UtilisateurRepository utilisateurRepository) {
        return login -> utilisateurRepository.findByLogin(login)
                .map(u -> User.withUsername(u.getLogin())
                        .password(u.getMotDePasse())
                        .roles(u.getRole().name())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Login inconnu : " + login));
    }

}