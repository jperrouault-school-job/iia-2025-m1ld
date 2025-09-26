package fr.formation.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateCommandeDetailRequest {
    @Positive
    private int quantity;

    @NotBlank
    private String produitId;
}
