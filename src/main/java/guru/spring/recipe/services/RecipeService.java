package guru.spring.recipe.services;

import guru.spring.recipe.commands.RecipeCommand;
import guru.spring.recipe.model.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> getRecipes();
    Recipe getRecipeById(Long id);

    RecipeCommand save(RecipeCommand recipeCommand);

    RecipeCommand findCommandById(Long id);

    void deleteById(Long id);

}
