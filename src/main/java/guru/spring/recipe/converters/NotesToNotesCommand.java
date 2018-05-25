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
public class NotesToNotesCommand implements Converter<Notes, NotesCommand>{

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes source) {
        if (source == null) {
            return null;
        }

        final NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(source.getId());
        notesCommand.setDescription(source.getRecipeNotes());
        return notesCommand;
    }
}