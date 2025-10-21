package fr.formation.api;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.formation.api.request.CreateOrUpdateProduitRequest;
import fr.formation.model.Produit;
import fr.formation.repository.ProduitRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ProduitApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProduitRepository repository;

    @Test
    void shouldFindAllStatusUnauthorizedIfAnonymous() throws Exception {
        // given

        // when
        this.mockMvc
            .perform(MockMvcRequestBuilders.get("/api/produit"))

        // then
            .andExpect(MockMvcResultMatchers.status().isUnauthorized())
        ;

        Mockito.verify(this.repository, Mockito.times(0)).findAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldFindAllStatusForbiddenIfNotAdmin() throws Exception {
        // given

        // when
        this.mockMvc
            .perform(MockMvcRequestBuilders.get("/api/produit"))

        // then
            .andExpect(MockMvcResultMatchers.status().isForbidden())
        ;

        Mockito.verify(this.repository, Mockito.never()).findAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldFindAllStatusOkIfAdmin() throws Exception {
        // given

        // when
        this.mockMvc
            .perform(MockMvcRequestBuilders
                .get("/api/produit")
                // .with(
                //     SecurityMockMvcRequestPostProcessors.user("test").roles("USER")
                // )
            )

        // then
            .andExpect(MockMvcResultMatchers.status().isOk())
        ;

        Mockito.verify(this.repository).findAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    void shouldCreateStatusForbiddenIfNotAdmin() throws Exception {
        // given
        CreateOrUpdateProduitRequest request = CreateOrUpdateProduitRequest.builder()
            .name("Le nom")
            .price(new BigDecimal("50"))
            .build()
        ;

        ObjectMapper mapper = new ObjectMapper();

        // when
        this.mockMvc
            .perform(MockMvcRequestBuilders
                .post("/api/produit")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request))
            )

        // then
            .andExpect(MockMvcResultMatchers.status().isForbidden())
        ;

        Mockito.verify(this.repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateStatusCreatedIfAdmin() throws Exception {
        // given
        CreateOrUpdateProduitRequest request = CreateOrUpdateProduitRequest.builder()
            .name("Le nom")
            .price(new BigDecimal("50"))
            .build()
        ;

        ObjectMapper mapper = new ObjectMapper();
        ArgumentCaptor<Produit> produitCaptor = ArgumentCaptor.captor();

        // when
        this.mockMvc
            .perform(MockMvcRequestBuilders
                .post("/api/produit")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request))
            )

        // then
            .andExpect(MockMvcResultMatchers.status().isCreated())
        ;

        // Mockito.verify(this.repository).save(Mockito.any());
        Mockito.verify(this.repository).save(produitCaptor.capture());

        Produit produit = produitCaptor.getValue();

        Assertions.assertNotNull(produit);
        Assertions.assertEquals(request.getName(), produit.getName());
        Assertions.assertEquals(request.getPrice(), produit.getPrice());
    }

    @ParameterizedTest
    @WithMockUser(roles = "ADMIN")
    @ValueSource(strings = { "", " ", "       " })
    @NullSource
    void shouldCreateStatusBadRequestIfRequestMalformed(String nom) throws Exception {
        // given
        CreateOrUpdateProduitRequest request = CreateOrUpdateProduitRequest.builder()
            .name(nom)
            .price(new BigDecimal("50"))
            .build()
        ;

        ObjectMapper mapper = new ObjectMapper();

        // when
        this.mockMvc
            .perform(MockMvcRequestBuilders
                .post("/api/produit")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request))
            )

        // then
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
        ;

        Mockito.verify(this.repository, Mockito.never()).save(Mockito.any());
    }
}
