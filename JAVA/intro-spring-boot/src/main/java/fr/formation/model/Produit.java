package fr.formation.model;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @UuidGenerator
    @Column(name = "pro_id", length = 40, nullable = false)
    private String id;
    
    @Column(name = "pro_name", length = 75, nullable = false)
    private String name;

    // ToOne, la stratégie de chargment par défaut = EAGER
    @ManyToOne(fetch = FetchType.LAZY)
    // @ManyToOne // Relation maitresse, obligatoire
    // @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "pro_fournisseur_id", nullable = true) // Optionel, comme @Column
    @JsonIgnore
    private Fournisseur fournisseur;
}
