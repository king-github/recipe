package guru.spring.recipe.repositories;

import guru.spring.recipe.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;



public interface IngredientRepository extends JpaRepository<Ingredient, Long> {



}
