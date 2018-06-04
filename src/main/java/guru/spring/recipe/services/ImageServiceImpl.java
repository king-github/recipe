package guru.spring.recipe.services;

import guru.spring.recipe.exceptions.ResourceNotFoundException;
import guru.spring.recipe.model.Recipe;
import guru.spring.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.function.Supplier;

@Service
public class ImageServiceImpl implements ImageService {

    private static final Supplier<ResourceNotFoundException> RECIPE_NOT_FOUND_EXCEPTION
            = ResourceNotFoundException.of("Recipe");

    private RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(RECIPE_NOT_FOUND_EXCEPTION);

        try {
            recipe.getImage().setImageData(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        recipeRepository.save(recipe);


    }

}
