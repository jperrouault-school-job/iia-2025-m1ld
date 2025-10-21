package fr.formation.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
            .perform(MockMvcRequestBuilders.get("/api/produit"))

        // then
            .andExpect(MockMvcResultMatchers.status().isOk())
        ;

        Mockito.verify(this.repository).findAll();
    }
}
