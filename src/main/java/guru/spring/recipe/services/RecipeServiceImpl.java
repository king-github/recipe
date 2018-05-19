package guru.spring.recipe.services;

import guru.spring.recipe.model.Recipe;
import guru.spring.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> getRecipes(){

        return recipeRepository.findAll();
    }

}
