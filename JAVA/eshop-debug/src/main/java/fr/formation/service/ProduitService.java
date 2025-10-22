package fr.formation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import fr.formation.exception.EntityNotDeletedException;
import fr.formation.exception.EntityNotFoundException;
import fr.formation.exception.EntityNotPersistedException;
import fr.formation.model.Fournisseur;
import fr.formation.model.Produit;
import fr.formation.repo.ProduitRepository;
import fr.formation.request.CreateOrUpdateProduitRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Service
@Validated
public class ProduitService {
    @Autowired
    private ProduitRepository repository;

    public List<Produit> findAll() {
        return Optional
                .ofNullable(this.repository.findAll())
                .orElse(new ArrayList<>());
    }

    public Produit findById(@Positive Integer id) {
        return this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Produit findByNom(@Valid @NotBlank String nom) {
        return this.repository.findByNom(nom).orElseThrow(EntityNotFoundException::new);
    }

    public Produit save(@Nullable Integer id, @Valid CreateOrUpdateProduitRequest produitRequest) {
        Produit produit = (id != null) ? this.findById(id) : new Produit();

        produit.setNom(produitRequest.getNom());
        produit.setPrix(produitRequest.getPrix());

        if (produitRequest.getFournisseurId() != null) {
            produit.setFournisseur(new Fournisseur());
            produit.getFournisseur().setId(produitRequest.getFournisseurId());
        }

        return this.save(produit);
    }

    public void deleteById(@Valid @Positive int id) {
        try {
            this.repository.deleteById(id);
        }

        catch (Exception e) {
            throw new EntityNotDeletedException();
        }
    }

    private Produit save(Produit produit) {
        try {
            return this.repository.save(produit);
        }

        catch (Exception e) {
            throw new EntityNotPersistedException();
        }
    }
}
