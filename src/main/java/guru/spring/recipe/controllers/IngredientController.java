package guru.spring.recipe.controllers;

import guru.spring.recipe.commands.IngredientCommand;
import guru.spring.recipe.commands.RecipeCommand;
import guru.spring.recipe.commands.UnitOfMeasureCommand;
import guru.spring.recipe.model.Recipe;
import guru.spring.recipe.services.IngredientService;
import guru.spring.recipe.services.RecipeService;
import guru.spring.recipe.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class IngredientController {

    private RecipeService recipeService;
    private IngredientService ingredientService;
    private UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String getIngredient(@PathVariable Long id, Model model) {

        log.debug("### get ingredient list for recipe with id: " + id);

        RecipeCommand recipe = recipeService.findCommandById(id);
        model.addAttribute("recipe", recipe);

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{idRecipe}/ingredient/{id}/show")
    public String getIngredient(@PathVariable Long idRecipe, @PathVariable Long id, Model model) {

        log.debug("### get ingredient id: " + id + " for recipe with id: " + idRecipe);

        IngredientCommand ingredient = ingredientService.findByRecipeAndId(idRecipe, id);
        model.addAttribute("ingredient", ingredient);

        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{idRecipe}/ingredient/{id}/update")
    public String updateIngredient(@PathVariable Long idRecipe, @PathVariable Long id, Model model) {

        log.debug("### update ingredient id: " + id + " for recipe with id: " + idRecipe);

        ingredientService.findByRecipeAndId(idRecipe, id);

        model.addAttribute("ingredient", ingredientService.findByRecipeAndId(idRecipe, id));
        model.addAttribute("uomList", unitOfMeasureService.getAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("/recipe/{idRecipe}/ingredient")
    public String saveIngredient(@PathVariable Long idRecipe, @ModelAttribute IngredientCommand ingredientCommand) {

        ingredientCommand.setRecipeId(idRecipe);
        IngredientCommand sevedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        return "redirect:/recipe/" + sevedIngredientCommand.getRecipeId() + "/ingredient/" + sevedIngredientCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{idRecipe}/ingredient/new")
    public String addNewIngredient(@PathVariable Long idRecipe, Model model) {

        log.debug("### add new ingredient for recipe with id: " + idRecipe);

        IngredientCommand newIngredientCommand = new IngredientCommand();
        newIngredientCommand.setRecipeId(idRecipe);
        newIngredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());

        model.addAttribute("ingredient", newIngredientCommand);
        model.addAttribute("uomList", unitOfMeasureService.getAllUoms());

        return "recipe/ingredient/ingredientform";
    }
}