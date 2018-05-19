package guru.spring.recipe.repositories;

import guru.spring.recipe.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByDescription(String desription);

}
