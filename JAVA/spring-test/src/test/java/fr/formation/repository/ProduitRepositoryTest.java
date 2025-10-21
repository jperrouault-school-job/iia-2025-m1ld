package fr.formation.repository;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
}
