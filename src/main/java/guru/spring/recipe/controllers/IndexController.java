package guru.spring.recipe.controllers;


import guru.spring.recipe.model.Category;
import guru.spring.recipe.model.Recipe;
import guru.spring.recipe.model.UnitOfMesure;
import guru.spring.recipe.repositories.CategoryRepository;
import guru.spring.recipe.repositories.RecipeRepository;
import guru.spring.recipe.repositories.UnitOfMesureRepository;
import guru.spring.recipe.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class IndexController {

    @Autowired
    private RecipeService recipeService;


    @GetMapping({"", "index"})
    public String getIndexPage(Model model) {

        System.out.println("### Index page");

        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }



}
