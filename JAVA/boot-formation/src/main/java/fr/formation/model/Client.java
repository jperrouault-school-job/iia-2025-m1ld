package fr.formation.model;

import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Client {
    @Id
    @UuidGenerator
    private String id;

    private String name;

    @OneToMany(mappedBy = "client")
    private List<Commande> commandes;
}
