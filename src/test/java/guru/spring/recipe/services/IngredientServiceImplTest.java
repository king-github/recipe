package guru.spring.recipe.services;

import guru.spring.recipe.commands.IngredientCommand;
import guru.spring.recipe.exceptions.ResourceNotFoundException;
import guru.spring.recipe.converters.IngredientCommandToIngredient;
import guru.spring.recipe.converters.IngredientToIngredientCommand;
import guru.spring.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.spring.recipe.model.Ingredient;
import guru.spring.recipe.model.Recipe;
import guru.spring.recipe.repositories.IngredientRepository;
import guru.spring.recipe.repositories.RecipeRepository;
import guru.spring.recipe.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {


    private IngredientService ingredientService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    private IngredientCommandToIngredient ingredientCommandToIngredient;
    private IngredientToIngredientCommand ingredientToIngredientCommand;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientService = new IngredientServiceImpl(recipeRepository, ingredientRepository,
                ingredientToIngredientCommand, ingredientCommandToIngredient, unitOfMeasureRepository);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.getIngredients().add(ingredient1);
        recipe.getIngredients().add(ingredient2);

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

    }

    @Test
    public void findByRecipeAndId() {

        IngredientCommand ingredientCommand = ingredientService.findByRecipeAndId(1L, 2L);
        assertEquals(Long.valueOf(2L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void throw404WhenNotFoundIngredient() {

        ingredientService.findByRecipeAndId(1L, 333L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void throw404WhenNotFoundReceipe() {

        ingredientService.findByRecipeAndId(333L, 1L);
    }



    @Test
    public void testDeleteById() throws Exception {
        //given
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        ingredientService.deleteIngredient(1L, 3L);

        //then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

}