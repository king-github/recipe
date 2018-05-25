package guru.spring.recipe.services;


import guru.spring.recipe.commands.RecipeCommand;
import guru.spring.recipe.converters.RecipeCommandToRecipe;
import guru.spring.recipe.converters.RecipeToRecipeCommand;
import guru.spring.recipe.model.Recipe;
import guru.spring.recipe.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {

    public static final String NEW_DESCRIPTION = "New description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Transactional
    @Test
    public void testSaveOfDescription(){

        // given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        // when
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand saved = recipeService.save(testRecipeCommand);

        // then
        assertEquals(NEW_DESCRIPTION, saved.getDescription());
        assertEquals(testRecipe.getId(), saved.getId());
        assertEquals(testRecipe.getCategories().size(), saved.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), saved.getIngredients().size());

    }


}
