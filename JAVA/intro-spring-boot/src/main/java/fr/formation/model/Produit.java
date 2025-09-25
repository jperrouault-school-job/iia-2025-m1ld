package fr.formation.model;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
}
