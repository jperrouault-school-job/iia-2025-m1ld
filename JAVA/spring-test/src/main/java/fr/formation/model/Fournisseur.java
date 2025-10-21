package fr.formation.model;

import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fournisseur")
@Getter @Setter
public class Fournisseur {
    @Id
    @UuidGenerator
    private String id;

    // @Transient // Rendre non persistant, car par défaut tous les attributs le sont
    private String name;

    // ToMany, la stratégie de chargement par défaut = LAZY
    // @OneToMany(fetch = FetchType.EAGER, mappedBy = "fournisseur") // Optionel
    @OneToMany(mappedBy = "fournisseur") // Optionel
    @JsonIgnore
    private List<Produit> produits;
}
