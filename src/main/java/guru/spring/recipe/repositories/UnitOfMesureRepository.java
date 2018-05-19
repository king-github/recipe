package guru.spring.recipe.repositories;

import guru.spring.recipe.model.UnitOfMesure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitOfMesureRepository extends JpaRepository<UnitOfMesure, Long> {

    Optional<UnitOfMesure> findByUom(String uom);

}
