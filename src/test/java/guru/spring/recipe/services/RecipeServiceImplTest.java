package guru.spring.recipe.services;

import guru.spring.recipe.converters.RecipeCommandToRecipe;
import guru.spring.recipe.converters.RecipeToRecipeCommand;
import guru.spring.recipe.model.Recipe;
import guru.spring.recipe.repositories.RecipeRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;
    RecipeCommandToRecipe recipeCommandToRecipe;
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp () {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipes() {

        Recipe recipe = new Recipe();
        List<Recipe> data = new ArrayList<>();
        data.add(recipe);

        when(recipeService.getRecipes()).thenReturn(data);

        List<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }
}