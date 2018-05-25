package guru.spring.recipe.services;

import guru.spring.recipe.commands.RecipeCommand;
import guru.spring.recipe.controllers.ResourceNotFoundExcception;
import guru.spring.recipe.converters.RecipeCommandToRecipe;
import guru.spring.recipe.converters.RecipeToRecipeCommand;
import guru.spring.recipe.model.Recipe;
import guru.spring.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;
    private RecipeCommandToRecipe recipeCommandToRecipe;
    private RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public List<Recipe> getRecipes(){

        return recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(Long id) {

        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcception("Recipe not found"));
    }

    @Override
    @Transactional
    public RecipeCommand save(RecipeCommand recipeCommand) {

        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);
        Recipe savedRecipe = recipeRepository.save(recipe);

        log.info("### Saved recipe id: " + savedRecipe.getId());

        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public RecipeCommand findCommandById(Long id) {

        return recipeToRecipeCommand.convert(recipeRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundExcception("Recipe not found")));
    }

    @Override
    public void deleteById(Long id) {

        recipeRepository.deleteById(id);
    }

}
