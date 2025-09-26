package fr.formation.repo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.formation.model.Produit;

public interface ProduitRepository extends JpaRepository<Produit, String> {
    public List<Produit> findAllByPriceBetween(BigDecimal priceA, BigDecimal priceB);

    @Query("select p from Produit p where p.price between ?1 and ?2")
    public List<Produit> findAllByPriceBetweenWithQuery(BigDecimal priceA, BigDecimal priceB);
}
