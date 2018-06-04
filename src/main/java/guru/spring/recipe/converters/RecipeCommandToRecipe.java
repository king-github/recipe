package guru.spring.recipe.converters;

import guru.spring.recipe.commands.RecipeCommand;
import guru.spring.recipe.model.Category;
import guru.spring.recipe.model.Notes;
import guru.spring.recipe.model.Recipe;
import guru.spring.recipe.services.CategoryService;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryConverter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final NotesCommandToNotes notesConverter;
    private final CategoryService categoryService;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConverter,
                                 IngredientCommandToIngredient ingredientConverter,
                                 NotesCommandToNotes notesConverter,
                                 CategoryService categoryService) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
        this.categoryService = categoryService;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());

        Notes notes = notesConverter.convert(source.getNotes());

        if (notes != null) {
            recipe.setNotes(notes);
            notes.setRecipe(recipe);
        }

        if (source.getCheckedCategories() != null && source.getCheckedCategories().size() > 0)
            recipe.setCategories(categoryService.getAllCategoryByIds(source.getCheckedCategories()));

        if (source.getIngredients() != null && source.getIngredients().size() > 0){
            source.getIngredients()
                    .forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return recipe;
    }
}