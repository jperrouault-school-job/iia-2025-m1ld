package fr.formation.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "commande")
@Getter @Setter
public class Commande {
    @Id
    @UuidGenerator
    private String id;

    private LocalDateTime dateTime;
    private BigDecimal total;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "commande")
    @Cascade(CascadeType.ALL)
    private List<CommandeDetail> details;
}
