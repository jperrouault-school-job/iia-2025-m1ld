package fr.formation.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import fr.formation.model.Produit;

@DataJpaTest
public class ProduitRepositoryTest {
    @Autowired
    private ProduitRepository repository;

    @Test
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, statements = "INSERT INTO produit (pro_id, pro_name, price) VALUES ('id3', 'test', 50)")
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/script-test/delete-produit.sql")
    void shouldFindAllSizeOk() {
        // given

        // when
        List<Produit> result = this.repository.findAll();

        // then
        Assertions.assertEquals(3, result.size());
    }

    @Test
    void shouldFindByQueryNameReturnProduit() {
        // given

        // when
        Optional<Produit> optProduit = this.repository.findByQueryName("Parachute");

        // then
        Assertions.assertTrue(optProduit.isPresent());
    }

    @Test
    void shouldFindByQueryNameDontReturnProduit() {
        // given

        // when
        Optional<Produit> optProduit = this.repository.findByQueryName("Nom qui existe pas");

        // then
        Assertions.assertFalse(optProduit.isPresent());
    }

    @ParameterizedTest
    @CsvSource({
        "Parachute,true",
        "Autre,false"
    })
    void shouldFindByQueryOk(String nom, boolean isPresent) {
        // given

        // when
        Optional<Produit> optProduit = this.repository.findByQueryName(nom);

        // then
        Assertions.assertEquals(isPresent, optProduit.isPresent());
    }

    @Test
    void shouldFindByPriceBetweenOk() {
        // given

        // when
        List<Produit> result = this.repository
            .findAllByPriceBetween(new BigDecimal("400"), new BigDecimal("600"))
        ;

        // then
        Assertions.assertEquals(1, result.size());
    }
}
