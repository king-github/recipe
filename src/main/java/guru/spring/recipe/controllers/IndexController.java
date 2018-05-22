package guru.spring.recipe.controllers;

import guru.spring.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {

    private RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping({"", "index"})
    public String getIndexPage(Model model) {

        System.out.println("### Index page");
        log.info("### Index page");

        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }



}
