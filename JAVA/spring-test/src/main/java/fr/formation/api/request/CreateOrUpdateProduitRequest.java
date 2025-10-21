package fr.formation.api.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class CreateOrUpdateProduitRequest {
    @NotBlank
    private String name;
    private BigDecimal price;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
}
