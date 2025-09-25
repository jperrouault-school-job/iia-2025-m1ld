package fr.formation.api.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class ProduitResponse {
    private String id;
    private String name;
    private BigDecimal price;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
}
