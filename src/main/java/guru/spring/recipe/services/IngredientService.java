package guru.spring.recipe.services;

import guru.spring.recipe.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeAndId(Long idRecipe, Long id);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteIngredient(Long recipeId, Long id);

}
