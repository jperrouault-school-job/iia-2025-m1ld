package fr.formation.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "produit")
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class Produit {
    @Id
    @UuidGenerator
    @Column(name = "pro_id", length = 40)
    private String id;
    
    @Column(name = "pro_name", length = 75, nullable = false)
    private String name;

    @Column(name = "pro_price", nullable = false)
    private BigDecimal price;

    @Column(name = "pro_date", nullable = false)
    private LocalDate date;
}
