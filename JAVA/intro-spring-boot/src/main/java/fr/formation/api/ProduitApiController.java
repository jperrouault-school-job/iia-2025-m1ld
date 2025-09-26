package fr.formation.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.model.Produit;
import fr.formation.repository.ProduitRepository;

@RestController
@RequestMapping("/api/produit")
public class ProduitApiController {
    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping
    public List<Produit> findAll() {
        List<Produit> produits = this.produitRepository.findAll();

        produits.forEach(p -> {
            System.out.println(p.getFournisseur().getId());
        });

        return produits;
    }
}
