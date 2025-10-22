package fr.formation.api;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.model.Utilisateur;
import fr.formation.repo.UtilisateurRepository;
import fr.formation.request.SubscriptionRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UtilisateurApiController {
    private final UtilisateurRepository repository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/subscribe")
    public String subscribe(@Valid @RequestBody SubscriptionRequest request) {
        Utilisateur utilisateur = new Utilisateur();

        BeanUtils.copyProperties(request, utilisateur);

        utilisateur.setPassword(this.passwordEncoder.encode(request.getPassword()));

        this.repository.save(utilisateur);

        return utilisateur.getId();
    }
}
