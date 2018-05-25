package guru.spring.recipe.controllers;

import guru.spring.recipe.commands.IngredientCommand;
import guru.spring.recipe.commands.RecipeCommand;
import guru.spring.recipe.model.Recipe;
import guru.spring.recipe.services.IngredientService;
import guru.spring.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class IngredientController {

    private RecipeService recipeService;
    private IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String getIngredients(@PathVariable Long id, Model model) {

        log.debug("### get ingredient list for recipe with id: " + id);

        RecipeCommand recipe = recipeService.findCommandById(id);
        model.addAttribute("recipe", recipe);

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{idRecipe}/ingredient/{id}/show")
    public String getIngredients(@PathVariable Long idRecipe, @PathVariable Long id, Model model) {

        log.debug("### get ingredient id: "+ id +" for recipe with id: " + idRecipe);

        IngredientCommand ingredient = ingredientService.findByRecipeAndId(idRecipe, id);
        model.addAttribute("ingredient", ingredient);

        return "recipe/ingredient/show";
    }


}
