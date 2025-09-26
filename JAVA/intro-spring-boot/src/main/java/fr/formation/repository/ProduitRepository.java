package fr.formation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.formation.model.Produit;

public interface ProduitRepository extends JpaRepository<Produit, String> {
    public boolean existsByName(String name);
    public Optional<Produit> findByName(String name);
    public List<Produit> findAllByNameContains(String name);

    // @Query("select p from Produit p where p.name = :leParamName")
    // public Optional<Produit> findByQueryName(@Param("leParamName") String name);

    @Query("select p from Produit p where p.name = ?1 order by p.id asc")
    public Optional<Produit> findByQueryName(String name);
}
