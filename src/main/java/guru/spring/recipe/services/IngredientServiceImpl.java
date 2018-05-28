package guru.spring.recipe.services;

import guru.spring.recipe.commands.IngredientCommand;
import guru.spring.recipe.controllers.ResourceNotFoundExcception;
import guru.spring.recipe.converters.IngredientCommandToIngredient;
import guru.spring.recipe.converters.IngredientToIngredientCommand;

import guru.spring.recipe.model.Ingredient;
import guru.spring.recipe.model.Recipe;
import guru.spring.recipe.model.UnitOfMeasure;
import guru.spring.recipe.repositories.IngredientRepository;
import guru.spring.recipe.repositories.RecipeRepository;
import guru.spring.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private RecipeRepository recipeRepository;
    private IngredientRepository ingredientRepository;
    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientCommandToIngredient ingredientCommandToIngredient;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository,
                                 IngredientRepository ingredientRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    public IngredientCommand findByRecipeAndId(Long idRecipe, Long id) {

        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(
                recipeRepository.findById(idRecipe).orElseThrow(ResourceNotFoundExcception.of("Recipe"))
                .getIngredients().stream().filter(ingredient -> ingredient.getId().equals(id))
                .findFirst().orElseThrow(ResourceNotFoundExcception.of("Ingredient")));

        ingredientCommand.setRecipeId(idRecipe);
        return ingredientCommand;
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

        log.debug("### "+ ingredientCommand.getRecipeId());

        Recipe recipe = recipeRepository.findById(ingredientCommand.getRecipeId())
                .orElseThrow(ResourceNotFoundExcception.of("Recipe"));

        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();

        Ingredient savedIngredient = ingredientOptional.orElseGet(() -> {
            Ingredient convert = ingredientCommandToIngredient.convert(ingredientCommand);
            convert.setRecipe(recipe);
            ingredientRepository.save(convert);
            recipe.addIngredient(convert);
            return convert;
        });


        UnitOfMeasure uom = unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId())
                .orElseThrow(ResourceNotFoundExcception.of("UnitOfMeasure"));


        savedIngredient.setAmount(ingredientCommand.getAmount());
        savedIngredient.setDescription(ingredientCommand.getDescription());
        savedIngredient.setUnitOfMeasure(uom);

        recipeRepository.save(recipe);


        IngredientCommand converted = ingredientToIngredientCommand.convert(savedIngredient);

        converted.setRecipeId(ingredientCommand.getRecipeId());

        log.debug("### ingredient: "+converted);

        return converted;
    }


    @Override
    public void deleteIngredient(Long recipeId, Long id) {

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(ResourceNotFoundExcception.of("Recipe"));

        Ingredient ingredient = recipe.getIngredients().stream()
                .filter(i -> i.getId().equals(id))
                .findFirst().orElseThrow(ResourceNotFoundExcception.of("Ingredient"))
                ;

        recipe.getIngredients().remove(ingredient);

        recipeRepository.save(recipe);
    }


}
