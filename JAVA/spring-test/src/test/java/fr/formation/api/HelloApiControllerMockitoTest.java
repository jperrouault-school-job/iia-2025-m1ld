package fr.formation.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fr.formation.repository.ProduitRepository;

@ExtendWith(MockitoExtension.class)
public class HelloApiControllerMockitoTest {
    private MockMvc mockMvc;

    @Mock
    private ProduitRepository produitRepository;

    @InjectMocks
    private HelloApiController ctrl;

    @BeforeEach
    void beforeEach() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.ctrl).build();
    }

    @Test
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
