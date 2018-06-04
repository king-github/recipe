package guru.spring.recipe.converters;

import guru.spring.recipe.commands.RecipeCommand;
import guru.spring.recipe.model.Category;
import guru.spring.recipe.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand>{

    private final CategoryToCategoryCommand categoryConveter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final NotesToNotesCommand notesConverter;
    private final ImageToImageCommand imageToImageCommand;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConveter,
                                 IngredientToIngredientCommand ingredientConverter,
                                 NotesToNotesCommand notesConverter,
                                 ImageToImageCommand imageToImageCommand) {
        this.categoryConveter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
        this.imageToImageCommand = imageToImageCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }

        final RecipeCommand command = new RecipeCommand();
        command.setId(source.getId());
        command.setCookTime(source.getCookTime());
        command.setPrepTime(source.getPrepTime());
        command.setDescription(source.getDescription());
        command.setDifficulty(source.getDifficulty());
        command.setDirections(source.getDirections());
        command.setServings(source.getServings());
        command.setSource(source.getSource());
        command.setUrl(source.getUrl());
        command.setImage(imageToImageCommand.convert(source.getImage()));
        command.setNotes(notesConverter.convert(source.getNotes()));

       /* if (source.getCategories() != null && source.getCategories().size() > 0){
            source.getCategories()
                    .forEach((Category category) -> command.getCategories().add(categoryConveter.convert(category)));
        }*/

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            command.setCheckedCategories(source.getCategories().stream()
                        .map(Category::getId)
                        .collect(Collectors.toList()));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0){
            source.getIngredients()
                    .forEach(ingredient -> command.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return command;
    }
}