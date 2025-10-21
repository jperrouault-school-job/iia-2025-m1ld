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
public class HelloApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProduitRepository produitRepository;

    @Test
    @WithMockUser
    void shouldHelloStatusOk() throws Exception {
        // given

        // when
        this.mockMvc
            .perform(MockMvcRequestBuilders.get("/hello/demo?username=Test&age=5"))

        // then
            .andExpect(MockMvcResultMatchers.status().isOk())
        ;

        Mockito.verify(this.produitRepository).findAll();
    }
}
