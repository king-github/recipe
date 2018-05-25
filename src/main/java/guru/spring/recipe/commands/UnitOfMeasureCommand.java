package guru.spring.recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor

@ToString
public class UnitOfMeasureCommand {

    private Long id;
    private String description;

}
