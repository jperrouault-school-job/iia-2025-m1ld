package fr.formation.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.api.request.CreateOrUpdateProduitRequest;
import fr.formation.api.response.ProduitResponse;
import fr.formation.exception.ProduitNotFoundException;
import fr.formation.model.Produit;

@RestController
@RequestMapping("/api/produit")
public class ProduitApiController {
    private List<Produit> produits = new ArrayList<>();

    public ProduitApiController() {
        produits.add(Produit.builder()
            .id("abc")
            .name("Parachute")
            .price(new BigDecimal("4999.85"))
            .date(LocalDate.of(2025, 1, 15))
            .build())
        ;

        produits.add(Produit.builder()
            .id("def")
            .name("Moto")
            .price(new BigDecimal("14999"))
            .date(LocalDate.now())
            .build())
        ;
    }

    @GetMapping
    public List<ProduitResponse> findAll() {
        return this.produits.stream()
            .map(p -> {
                // return ProduitResponse.builder()
                //     .id(p.getId())
                //     .name(p.getName())
                //     .price(p.getPrice())
                //     .date(p.getDate())
                //    .build()
                // ;

                ProduitResponse resp = new ProduitResponse();

                BeanUtils.copyProperties(p, resp);

                return resp;
            })
            .toList()
        ;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public String create(@RequestBody CreateOrUpdateProduitRequest request) {
        Produit produit = new Produit();

        BeanUtils.copyProperties(request, produit);

        produit.setId(UUID.randomUUID().toString());

        this.produits.add(produit);

        return produit.getId();
    }

    @PutMapping("/{id}")
    public String updateById(@PathVariable String id, @RequestBody CreateOrUpdateProduitRequest request) {
        Produit produit = this.produits.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst()
            .orElseThrow(ProduitNotFoundException::new);
        ;

        BeanUtils.copyProperties(request, produit);
        produit.setId(id);

        return id;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        this.produits.removeIf(p -> p.getId().equals(id));
    }
}
