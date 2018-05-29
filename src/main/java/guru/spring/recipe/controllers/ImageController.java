package guru.spring.recipe.controllers;

import guru.spring.recipe.services.ImageService;
import guru.spring.recipe.services.RecipeService;

public class ImageController {

    private ImageService imageService;
    private RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {

    }
}
