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
        return this.produitRepository.findAll();
    }
}
