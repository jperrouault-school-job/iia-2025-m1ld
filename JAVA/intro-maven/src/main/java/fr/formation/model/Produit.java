package fr.formation.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Produit {
    private String id;
    private String name;
}
