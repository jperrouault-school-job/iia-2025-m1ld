package fr.formation.api.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateCommandeRequest {
    @NotBlank
    private String clientId;

    @Valid
    private List<CreateCommandeDetailRequest> details;
}
