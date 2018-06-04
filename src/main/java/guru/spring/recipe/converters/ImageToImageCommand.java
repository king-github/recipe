package guru.spring.recipe.converters;


import guru.spring.recipe.commands.ImageCommand;
import guru.spring.recipe.commands.RecipeCommand;
import guru.spring.recipe.model.Image;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ImageToImageCommand implements Converter<Image, ImageCommand> {


    @Synchronized
    @Nullable
    @Override
    public ImageCommand convert(Image source) {
        if (source == null) {
            return null;
        }

        final ImageCommand imageCommand = new ImageCommand();
        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());

        imageCommand.setId(source.getId());
        imageCommand.setImageData(source.getImageData());
        imageCommand.setRecipeCommand(recipeCommand);

        return imageCommand;
    }
}
