package fr.formation.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.model.Produit;
import fr.formation.request.CreateOrUpdateProduitRequest;
import fr.formation.response.ProduitResponse;
import fr.formation.service.ProduitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produit")
@RequiredArgsConstructor
public class ProduitApiController {
    private ProduitService service = new ProduitService();
    
    @GetMapping
    public List<ProduitResponse> findAll() {
        return this.service.findAll()
            .stream()
            .map(p -> ProduitResponse.builder()
                .nom(p.getNom())
                .prix(p.getPrix())
                .fournisseurId(p.getFournisseur().getId())
                .fournisseurNom(p.getFournisseur().getNom())
                .build())
            .toList();
    }
    
    @GetMapping("/id")
    public ProduitResponse findById(@PathVariable int id) {
        Produit produit = this.service.findById(id);

        return ProduitResponse.builder()
            .id(produit.getId())
            .nom(produit.getNom())
            .prix(produit.getPrix())
            .fournisseurId(produit.getFournisseur().getId())
            .fournisseurNom(produit.getFournisseur().getNom())
            .build();
    }

    @PostMapping
    public Integer create(@Valid @RequestBody CreateOrUpdateProduitRequest request) {
        return this.service.save(null, request).getId();
    }

    @PostMapping("/{id}")
    public Integer update(@PathVariable int id, @Valid @RequestBody CreateOrUpdateProduitRequest request) {
        return this.service.save(id, request).getId();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        this.service.deleteById(id);
    }
}
