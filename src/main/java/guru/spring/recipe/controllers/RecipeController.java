package guru.spring.recipe.controllers;

import guru.spring.recipe.commands.CategoryCommand;
import guru.spring.recipe.commands.RecipeCommand;
import guru.spring.recipe.exceptions.ResourceNotFoundException;
import guru.spring.recipe.services.CategoryService;
import guru.spring.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("recipe")
public class RecipeController {

    private RecipeService recipeService;
    private CategoryService categoryService;

    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String getIndexPage(Model model) {

        log.info("### Index page");

        model.addAttribute("recipes", recipeService.getRecipes());

        return "recipe/index";
    }

    @GetMapping("/{id}/show")
    public String showRecipePage(@PathVariable Long id, Model model) {

        log.info("### Show recipe page");

        model.addAttribute("recipe", recipeService.getRecipeById(id));

        return "recipe/show";
    }

    @GetMapping("/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        model.addAttribute("categories", categoryService.getAllCategoryCommand());

        return "recipe/recipeform";
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model){

        model.addAttribute("recipe", recipeService.findCommandById(id));
        model.addAttribute("categories", categoryService.getAllCategoryCommand());

        return "recipe/recipeform";
    }

    @PostMapping("")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe")  RecipeCommand command,
                               BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategoryCommand());
            return "recipe/recipeform";
        }

        RecipeCommand savedCommand = recipeService.save(command);

        return "redirect:/recipe/" + savedCommand.getId() +"/show";
    }

    @GetMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable Long id) {

        recipeService.deleteById(id);
        return "redirect:/";
    }


}
