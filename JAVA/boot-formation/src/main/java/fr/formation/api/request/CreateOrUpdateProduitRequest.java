package fr.formation.api.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateOrUpdateProduitRequest {
    private String name;
    private BigDecimal price;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
}
