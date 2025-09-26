package fr.formation.api;

import java.util.List;

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
import fr.formation.repo.ProduitRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produit")
@RequiredArgsConstructor
public class ProduitApiController {
    private final ProduitRepository repository;

    @GetMapping
    public List<ProduitResponse> findAll() {
        return this.repository.findAll().stream()
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

        this.repository.save(produit);

        return produit.getId();
    }

    @PutMapping("/{id}")
    public String updateById(@PathVariable String id, @RequestBody CreateOrUpdateProduitRequest request) {
        Produit produit = this.repository.findById(id).orElseThrow(ProduitNotFoundException::new);

        BeanUtils.copyProperties(request, produit);

        return id;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        if (this.repository.existsById(id)) { // Pas obligé
            this.repository.deleteById(id);
        }
    }
}
