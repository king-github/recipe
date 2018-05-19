package guru.spring.recipe.services;

import guru.spring.recipe.model.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> getRecipes();
}
