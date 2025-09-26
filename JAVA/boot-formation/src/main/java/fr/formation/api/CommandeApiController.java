package fr.formation.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.api.request.CreateCommandeDetailRequest;
import fr.formation.api.request.CreateCommandeRequest;
import fr.formation.api.response.CommandeByProduitIdResponse;
import fr.formation.exception.ProduitNotFoundException;
import fr.formation.model.Client;
import fr.formation.model.Commande;
import fr.formation.model.CommandeDetail;
import fr.formation.model.Produit;
import fr.formation.repo.ClientRepository;
import fr.formation.repo.CommandeRepository;
import fr.formation.repo.ProduitRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/commande")
@RequiredArgsConstructor
public class CommandeApiController {
    private final CommandeRepository repository;
    // private final CommandeDetailRepository commandeDetailRepository;
    private final ClientRepository clientRepository;
    private final ProduitRepository produitRepository;

    @GetMapping("/by-produit/{produitId}")
    public List<?> findByProduitId(@PathVariable String produitId) {
        return this.repository.findAllByDetailsProduitId(produitId).stream()
            .map(c -> {
                CommandeByProduitIdResponse resp = new CommandeByProduitIdResponse();

                BeanUtils.copyProperties(c, resp);

                return resp;
            })
            .toList()
        ;
    }

    // @PostMapping
    // @ResponseStatus(code = HttpStatus.CREATED)
    // @Transactional
    // public String create(@Valid @RequestBody CreateCommandeRequest request) {
    //     Commande commande = new Commande();
    //     Client client = this.clientRepository.getReferenceById(request.getClientId());
    //     BigDecimal total = BigDecimal.ZERO;

    //     commande.setDateTime(LocalDateTime.now());
    //     commande.setClient(client);
    //     commande.setDetails(new ArrayList<>());

    //     for (CreateCommandeDetailRequest d : request.getDetails()) {
    //         CommandeDetail detail = new CommandeDetail();
    //         Produit produit = this.produitRepository.findById(d.getProduitId()).orElseThrow(ProduitNotFoundException::new);

    //         total = total.add(produit.getPrice().multiply(new BigDecimal(d.getQuantity())));

    //         detail.setCommande(commande);
    //         detail.setQuantity(d.getQuantity());
    //         detail.setProduit(this.produitRepository.getReferenceById(d.getProduitId()));

    //         commande.getDetails().add(detail);
    //         // this.commandeDetailRepository.save(detail);
    //     }

    //     commande.setTotal(total);
    //     this.repository.save(commande);

    //     this.commandeDetailRepository.saveAll(commande.getDetails());

    //     return commande.getId();
    // }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Transactional
    public String create(@Valid @RequestBody CreateCommandeRequest request) {
        Commande commande = new Commande();
        Client client = this.clientRepository.getReferenceById(request.getClientId());
        BigDecimal total = BigDecimal.ZERO;

        commande.setDateTime(LocalDateTime.now());
        commande.setClient(client);
        commande.setDetails(new ArrayList<>());

        for (CreateCommandeDetailRequest d : request.getDetails()) {
            CommandeDetail detail = new CommandeDetail();
            Produit produit = this.produitRepository.findById(d.getProduitId()).orElseThrow(ProduitNotFoundException::new);

            total = total.add(produit.getPrice().multiply(new BigDecimal(d.getQuantity())));

            detail.setCommande(commande);
            detail.setQuantity(d.getQuantity());
            detail.setProduit(this.produitRepository.getReferenceById(d.getProduitId()));

            commande.getDetails().add(detail);
        }

        commande.setTotal(total);
        this.repository.save(commande);

        // PAS DE SAVEALL / NI DE SAVE sur CommandeDetail SI et seulement SI @Cascade est là sur la relation Commande -> CommandeDetail
        // this.commandeDetailRepository.saveAll(commande.getDetails());

        return commande.getId();
    }
}
