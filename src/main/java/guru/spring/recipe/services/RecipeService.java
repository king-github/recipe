package guru.spring.recipe.services;

import guru.spring.recipe.model.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeService {

    List<Recipe> getRecipes();
    Optional<Recipe> getRecipeById(Long id);
}
