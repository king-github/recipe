package guru.spring.recipe.services;

import guru.spring.recipe.commands.CategoryCommand;
import guru.spring.recipe.commands.IngredientCommand;
import guru.spring.recipe.controllers.ResourceNotFoundExcception;
import guru.spring.recipe.converters.CategoryToCategoryCommand;
import guru.spring.recipe.converters.IngredientCommandToIngredient;
import guru.spring.recipe.converters.IngredientToIngredientCommand;
import guru.spring.recipe.model.Ingredient;
import guru.spring.recipe.model.Recipe;
import guru.spring.recipe.model.UnitOfMeasure;
import guru.spring.recipe.repositories.CategoryRepository;
import guru.spring.recipe.repositories.IngredientRepository;
import guru.spring.recipe.repositories.RecipeRepository;
import guru.spring.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryToCategoryCommand categoryToCategoryCommand;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Override
    public List<CategoryCommand> getAllCategoryCommand() {

        return categoryRepository.findAll().stream()
                .map(categoryToCategoryCommand::convert)
                .collect(Collectors.toList());
    }


}
