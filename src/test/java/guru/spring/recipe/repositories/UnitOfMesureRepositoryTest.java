package guru.spring.recipe.repositories;

import guru.spring.recipe.model.UnitOfMesure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMesureRepositoryTest {

    @Autowired
    UnitOfMesureRepository unitOfMesureRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByUom() {

        Optional<UnitOfMesure> teaspoon = unitOfMesureRepository.findByUom("Teaspoon");

        assertEquals("Teaspoon", teaspoon.get().getUom());

    }
}