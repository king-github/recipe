package guru.spring.recipe.converters;

import guru.spring.recipe.commands.ImageCommand;
import guru.spring.recipe.model.Image;
import guru.spring.recipe.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ImageCommandToImage implements Converter<ImageCommand, Image> {

    @Synchronized
    @Nullable
    @Override
    public Image convert(ImageCommand source) {
        if (source == null) {
            return null;
        }

        final Image image = new Image();
        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());

        image.setId(source.getId());
        image.setImageData(source.getImageData());
        image.setRecipe(recipe);
        return image;
    }

}