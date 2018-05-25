package guru.spring.recipe.controllers;

import guru.spring.recipe.commands.RecipeCommand;
import guru.spring.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}/show")
    public String showRecipePage(@PathVariable Long id, Model model) {

        System.out.println("### Show recipe page");
        log.info("### Show recipe page");

        model.addAttribute("recipe", recipeService.getRecipeById(id));

        return "recipe/show";
    }

    @GetMapping("/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(id));

        return "recipe/recipeform";
    }

    @PostMapping("")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command){
        RecipeCommand savedCommand = recipeService.save(command);

        return "redirect:/recipe/" + savedCommand.getId() +"/show";
    }

    @GetMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable Long id) {

        recipeService.deleteById(id);
        return "redirect:/";
    }


}
