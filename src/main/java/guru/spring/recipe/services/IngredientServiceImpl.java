package guru.spring.recipe.services;

import guru.spring.recipe.commands.IngredientCommand;
import guru.spring.recipe.controllers.ResourceNotFoundExcception;
import guru.spring.recipe.converters.IngredientToIngredientCommand;

import guru.spring.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;


@Service
public class IngredientServiceImpl implements IngredientService {

    //IngredientRepository ingredientRepository;
    RecipeRepository recipeRepository;
    IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    public IngredientCommand findByRecipeAndId(Long idRecipe, Long id) {

        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(
                recipeRepository.findById(idRecipe).orElseThrow(() -> new ResourceNotFoundExcception("Receipe not found"))
                .getIngredients().stream().filter(ingredient -> ingredient.getId() == id)
                .findFirst().orElseThrow(() -> new ResourceNotFoundExcception("Ingredient not found")));

        ingredientCommand.setRecipeId(idRecipe);
        return ingredientCommand;
    }

}
