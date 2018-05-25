package guru.spring.recipe.converters;

import guru.spring.recipe.commands.NotesCommand;
import guru.spring.recipe.commands.UnitOfMeasureCommand;
import guru.spring.recipe.model.Notes;
import guru.spring.recipe.model.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand source) {
        if(source == null) {
            return null;
        }

        final Notes notes = new Notes();
        notes.setId(source.getId());
        notes.setRecipeNotes(source.getDescription());
        return notes;
    }
}