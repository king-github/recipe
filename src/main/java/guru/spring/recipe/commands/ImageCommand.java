package guru.spring.recipe.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ImageCommand {

    private Long id;
    private byte[] imageData;
    private RecipeCommand recipeCommand;

}
