package fr.formation.model;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "commande_detail")
@Getter @Setter
public class CommandeDetail {
    @Id
    @UuidGenerator
    private String id;

    private int quantity;

    @ManyToOne
    private Produit produit;

    @ManyToOne
    private Commande commande;
}
