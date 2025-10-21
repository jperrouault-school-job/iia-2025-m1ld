package fr.formation.repository;

import java.util.List;

import fr.formation.model.Produit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

// @Repository
public class DemoProduitRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Produit> findAll() {
        return this.em.createQuery("select p from Produit p", Produit.class).getResultList();
    }

    public Produit save(Produit produit) {
        this.em.getTransaction().begin();
        this.em.persist(produit);
        this.em.getTransaction().commit();

        return produit;
    }
}
