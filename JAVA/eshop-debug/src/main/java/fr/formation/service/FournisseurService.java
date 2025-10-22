package fr.formation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import fr.formation.exception.EntityNotDeletedException;
import fr.formation.exception.EntityNotFoundException;
import fr.formation.exception.EntityNotPersistedException;
import fr.formation.model.Fournisseur;
import fr.formation.repo.FournisseurRepository;
import fr.formation.request.CreateFournisseurRequest;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class FournisseurService {
    private final FournisseurRepository repository;

    public List<Fournisseur> findAll() {
        return Optional
                .ofNullable(this.repository.findAll())
                .orElse(new ArrayList<>());
    }

    public Fournisseur findById(@Valid @Positive int id) {
        return this.repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Fournisseur findDetailedById(@Valid @Positive int id) {
        return this.repository.findByIdFetchingProduits(id).orElseThrow(EntityNotFoundException::new);
    }

    public Fournisseur save(@Nullable Integer id, @Valid CreateFournisseurRequest request) {
        Fournisseur fournisseur = new Fournisseur();

        if (id != null) {
            fournisseur.setId(id);
        }

        fournisseur.setNom(request.getNom());

        return this.save(fournisseur);
    }

    public void deleteById(@Valid @Positive int id) {
        try {
            this.repository.deleteById(id);
        }

        catch (Exception e) {
            throw new EntityNotDeletedException();
        }
    }

    private Fournisseur save(@Valid Fournisseur fournisseur) {
        try {
            return this.repository.save(fournisseur);
        }

        catch (Exception e) {
            throw new EntityNotPersistedException();
        }
    }
}
