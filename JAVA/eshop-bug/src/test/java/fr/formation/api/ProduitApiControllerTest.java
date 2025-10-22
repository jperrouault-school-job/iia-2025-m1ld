package fr.formation.api;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.formation.model.Produit;
import fr.formation.request.CreateOrUpdateProduitRequest;
import fr.formation.service.ProduitService;

@WebMvcTest(ProduitApiController.class)
@WithMockUser
@ActiveProfiles("test")
class ProduitApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProduitService service;

    @Test
    void shouldFindAllStatusOk() throws Exception {
        //given

        // when
        this.mockMvc.perform(
            MockMvcRequestBuilders.get("/api/produit")
        )

        // then
        .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(this.service).findAll();
    }

    @Test
    void shouldAddStatusCreated() throws Exception {
        // given
        CreateOrUpdateProduitRequest request = CreateOrUpdateProduitRequest.builder()
            .nom("Produit de test")
            .prix(new BigDecimal("50"))
            .fournisseurId(2)
            .build();

        Mockito .when(this.service.save(Mockito.eq(null), Mockito.any()))
                .thenReturn(new Produit());

        // when
        this.mockMvc.perform(
            MockMvcRequestBuilders
                .post("/api/produit")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.json(request))
        )

        // then
        .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(this.service).save(Mockito.eq(null), Mockito.any());
    }

    @Test
    void shouldEditStatusOk() throws Exception {
        // given
        CreateOrUpdateProduitRequest request = CreateOrUpdateProduitRequest.builder()
            .nom("Produit de test")
            .prix(new BigDecimal("50"))
            .fournisseurId(2)
            .build();

        Mockito .when(this.service.save(Mockito.eq(2), Mockito.any()))
                .thenReturn(new Produit());

        // when
        this.mockMvc.perform(
            MockMvcRequestBuilders
                .put("/api/produit/2")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.json(request))
        )

        // then
        .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(this.service).save(Mockito.eq(2), Mockito.any());
    }

    @Test
    void shouldDeleteByIdStatusOk() throws Exception {
        // given

        // when
        this.mockMvc.perform(
            MockMvcRequestBuilders
                .delete("/api/produit/2")
                .with(SecurityMockMvcRequestPostProcessors.csrf()
            )
        )

        // then
        .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(this.service).deleteById(2);
    }

    private String json(CreateOrUpdateProduitRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(request);
    }
}
