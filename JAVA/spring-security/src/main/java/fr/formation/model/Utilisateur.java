package fr.formation.model;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Utilisateur {
    @Id
    @UuidGenerator
    private String id;

    private String username;

    @Column(length = 300)
    private String password;

    private String role;
}
