package guru.spring.recipe.controllers;

import guru.spring.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("recipe")
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping("")
    public String getIndexPage(Model model) {

        System.out.println("### Index page");
        log.info("### Index page");

        model.addAttribute("recipes", recipeService.getRecipes());

        return "recipe/index";
    }

    @GetMapping("/show/{id}")
    public String showRecipePage(@PathVariable Long id, Model model) {

        System.out.println("### Show recipe page");
        log.info("### Show recipe page");

        model.addAttribute("recipe", recipeService.getRecipeById(id).orElseThrow(() -> new ResourceNotFoundExcception("Recipe not found")));

        return "recipe/show";
    }


}
