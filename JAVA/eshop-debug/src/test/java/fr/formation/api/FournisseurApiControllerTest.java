package fr.formation.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.formation.repo.FournisseurRepository;
import fr.formation.request.CreateFournisseurRequest;

@WebMvcTest(FournisseurApiController.class)
@WithMockUser
@ActiveProfiles("test")
@EnableMethodSecurity(prePostEnabled = true)
class FournisseurApiControllerTest {
    private static final String FOURNISSEUR_NAME = "Nom de test";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FournisseurRepository repository;

    @Test
    void shouldFindAllStatusOk() throws Exception {
        //given

        // when
        this.mockMvc.perform(
            MockMvcRequestBuilders.get("/api/fournisseur")
        )

        // then
        .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(this.repository).findAll();
    }

    @Test
    void shouldAddStatusForbidden() throws Exception {
        // given
        CreateFournisseurRequest request = CreateFournisseurRequest.builder().nom(FOURNISSEUR_NAME).build();

        // when
        this.mockMvc.perform(
            MockMvcRequestBuilders
                .post("/api/fournisseur")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.json(request))
        )

        // then
        .andExpect(MockMvcResultMatchers.status().isForbidden());

        Mockito.verify(this.repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldAddStatusOk() throws Exception {
        // given
        CreateFournisseurRequest request = CreateFournisseurRequest.builder().nom(FOURNISSEUR_NAME).build();

        // when
        this.mockMvc.perform(
            MockMvcRequestBuilders
                .post("/api/fournisseur")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.json(request))
        )

        // then
        .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(this.repository).save(Mockito.any());
    }

    private String json(CreateFournisseurRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(request);
    }
}
