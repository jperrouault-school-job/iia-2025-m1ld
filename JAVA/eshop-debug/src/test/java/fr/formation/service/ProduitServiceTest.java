package fr.formation.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import fr.formation.model.Produit;
import fr.formation.repo.ProduitRepository;
import fr.formation.request.CreateOrUpdateProduitRequest;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest(classes = ProduitService.class)
@ContextConfiguration(classes = ValidationAutoConfiguration.class)
class ProduitServiceTest {
    @MockitoBean
    private ProduitRepository repository;

    @Autowired
    private ProduitService service;

    @Test
    void shouldReturnProduitById() throws Exception {
        // given
        Mockito.when(this.repository.findById(1)).thenReturn(Optional.of(new Produit()));

        // when / then
        Assertions.assertNotNull(this.service.findById(1));

        // then
        Mockito.verify(this.repository, Mockito.times(1)).findById(1);
    }

    @Test
    void shouldThrowExceptionOnFindById() {
        // given

        // when / then
        Assertions.assertThrows(ConstraintViolationException.class, () -> this.service.findById(0));

        // then
        Mockito.verify(this.repository, Mockito.never()).findById(0);
    }

    @Test
    void shouldReturnProduitByNom() throws Exception {
        // given
        Mockito.when(this.repository.findByNom(Mockito.anyString())).thenReturn(Optional.of(new Produit()));

        // when / then
        Assertions.assertNotNull(this.service.findByNom("test"));

        // then
        Mockito.verify(this.repository, Mockito.times(1)).findByNom(Mockito.anyString());
    }

    @Test
    void shouldThrowExceptionOnFindByNom() {
        // given

        // when / then
        Assertions.assertThrows(ConstraintViolationException.class, () -> this.service.findByNom(null));
        Assertions.assertThrows(ConstraintViolationException.class, () -> this.service.findByNom(""));

        // then
        Mockito.verify(this.repository, Mockito.never()).findById(0);
    }

    @Test
    void shouldThrowExceptionOnSaveWhenNullOrEmptyNom() {
        // given
        CreateOrUpdateProduitRequest request = this.generateProduit();

        request.setNom(null);

        // when / then
        Assertions.assertThrows(ConstraintViolationException.class, () -> this.service.save(null, request));

        // given
        request.setNom("");

        // when / then
        Assertions.assertThrows(ConstraintViolationException.class, () -> this.service.save(null, request));

        // then
        Mockito.verify(this.repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldThrowExceptionOnSaveWhenWrongPrix() {
        // give
        CreateOrUpdateProduitRequest request = this.generateProduit();

        request.setPrix(new BigDecimal("-1"));

        // when / then
        Assertions.assertThrows(ConstraintViolationException.class, () -> this.service.save(null, request));

        // then
        Mockito.verify(this.repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldThrowExceptionOnSaveWhenNullOrEmptyFournisseur() {
        // given
        CreateOrUpdateProduitRequest request = this.generateProduit();

        request.setFournisseurId(0);

        // when / then
        Assertions.assertThrows(ConstraintViolationException.class, () -> this.service.save(null, request));

        // then
        Mockito.verify(this.repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldSave() {
        // given
        CreateOrUpdateProduitRequest request = this.generateProduit();

        // when / then
        Assertions.assertDoesNotThrow(() -> this.service.save(null, request));

        // then
        Mockito.verify(this.repository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void shouldReturnList() {
        // given
        List<Produit> laListe = new ArrayList<>();

        laListe.add(new Produit());

        Mockito.when(this.repository.findAll()).thenReturn(laListe);

        // when / then
        Assertions.assertEquals(this.service.findAll(), laListe);

        // then
        Mockito.verify(this.repository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldReturnListIfNull() {
        // given
        Mockito.when(this.repository.findAll()).thenReturn(null);

        // when / then
        Assertions.assertNotNull(this.service.findAll());

        // then
        Mockito.verify(this.repository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldDeleteById() throws Exception {
        // given

        // when
        this.repository.deleteById(1);

        // then
        Mockito.verify(this.repository, Mockito.times(1)).deleteById(1);
    }

    @Test
    void shouldThrowExceptionOnDeleteById() {
        // give

        // when / then
        Assertions.assertThrows(ConstraintViolationException.class, () -> this.service.deleteById(0));

        // then
        Mockito.verify(this.repository, Mockito.never()).deleteById(0);
    }

    private CreateOrUpdateProduitRequest generateProduit() {
        CreateOrUpdateProduitRequest request = new CreateOrUpdateProduitRequest();

        request.setNom("un nom");
        request.setPrix(new BigDecimal("1"));
        request.setFournisseurId(1);

        return request;
    }
}
