package fr.formation.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.formation.model.Utilisateur;
import fr.formation.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final UtilisateurRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = this.repository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("L'utilisateur n'existe pas"))
        ;

        // return User.withUsername(username)
        //     // {noop} Pas d'encodeur
        //     // {bcrypt} Blowfish
        //     // .password("{noop}123456$")
        //     // .password(passwordEncoder.encode("123456$"))
        //     .password(utilisateur.getPassword())
        //     .roles(utilisateur.getRole())
        //     .build()
        // ;

        return new UserPrincipal(utilisateur);
    }
}
