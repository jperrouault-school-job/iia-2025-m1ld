package fr.formation.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.formation.model.Fournisseur;
import fr.formation.repo.FournisseurRepository;
import fr.formation.request.CreateFournisseurRequest;
import fr.formation.response.FournisseurResponse;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/founisseur")
@RequiredArgsConstructor
public class FournisseurApiController {
    private FournisseurRepository repository;

    @GetMapping
    public List<FournisseurResponse> findAll() {
        return this.repository.findAll()
            .stream()
            .map(f -> FournisseurResponse.builder()
                .id(f.getId())
                .nom(f.getNom())
                .produitsCount(f.getProduits().size())
                .build()
            )
            .toList();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIM')")
    @ResponseStatus(HttpStatus.CREATED)
    public FournisseurResponse create(@RequestBody CreateFournisseurRequest request) {
        Fournisseur fournisseur = new Fournisseur();

        fournisseur.setNom(request.getNom());

        this.repository.save(fournisseur);

        return FournisseurResponse.builder()
            .id(fournisseur.getId())
            .nom(fournisseur.getNom())
            .build();
    }
}
