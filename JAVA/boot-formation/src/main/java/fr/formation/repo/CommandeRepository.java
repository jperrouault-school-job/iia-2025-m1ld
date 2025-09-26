package fr.formation.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.formation.model.Commande;

public interface CommandeRepository extends JpaRepository<Commande, String> {
    public List<Commande> findAllByDetailsProduitId(String produitId);

    @Query("select c from Commande c left join c.details d where d.produit.id = ?1")
    public List<Commande> findAllByDetailsProduitIdQuery(String produitId);
}
