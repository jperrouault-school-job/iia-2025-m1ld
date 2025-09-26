package fr.formation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.formation.model.Fournisseur;

public interface FournisseurRepository extends JpaRepository<Fournisseur, String> {
    // "fetch" => forcer le chargement des produits du fournisseur
    @Query("select f from Fournisseur f left join fetch f.produits")
    public List<Fournisseur> findAllFetchingProduits();
}
