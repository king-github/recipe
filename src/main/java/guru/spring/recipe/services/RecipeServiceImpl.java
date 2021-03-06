package guru.spring.recipe.services;

import guru.spring.recipe.commands.RecipeCommand;
import guru.spring.recipe.exceptions.ResourceNotFoundException;
import guru.spring.recipe.converters.RecipeCommandToRecipe;
import guru.spring.recipe.converters.RecipeToRecipeCommand;
import guru.spring.recipe.model.Notes;
import guru.spring.recipe.model.Recipe;
import guru.spring.recipe.repositories.NotesRepository;
import guru.spring.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;


@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private static final Supplier<ResourceNotFoundException> RECIPE_NOT_FOUND_EXCEPTION
            = ResourceNotFoundException.of("Recipe");

    @Autowired
    private NotesRepository notesRepository;

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
                .orElseThrow(RECIPE_NOT_FOUND_EXCEPTION);
    }

    @Override
    @Transactional
    public RecipeCommand save(RecipeCommand recipeCommand) {

        System.out.println("##### cat ");
        recipeCommand.getCheckedCategories().forEach(System.out::println);

        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);
        Recipe savedRecipe = recipeRepository.save(recipe);

        log.info("### Saved recipe id: " + savedRecipe.getId());

        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public RecipeCommand findCommandById(Long id) {

        return recipeToRecipeCommand.convert(recipeRepository.findById(id)
                .orElseThrow(RECIPE_NOT_FOUND_EXCEPTION));
    }

    @Override
    public void deleteById(Long id) {

        recipeRepository.deleteById(id);
    }

}
