package fr.formation.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.model.Fournisseur;
import fr.formation.repository.FournisseurRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fournisseur")
@RequiredArgsConstructor
public class FournisseurApiController {
    private final FournisseurRepository repository;

    @GetMapping
    @Transactional // On utiliser cette annotation pour garder la session Hibernate ouverte le temps de charger les produits du fournisseur
    public List<Fournisseur> findAll() {
        List<Fournisseur> fournisseurs = this.repository.findAll();

        for (Fournisseur f : fournisseurs) {
            System.out.println(f.getProduits().size());
        }

        return fournisseurs;
    }

    @GetMapping("/fetch")
    public List<Fournisseur> findAllFetching() {
        List<Fournisseur> fournisseurs = this.repository.findAllFetchingProduits();

        for (Fournisseur f : fournisseurs) {
            System.out.println(f.getProduits().size());
        }

        return fournisseurs;
    }
}
