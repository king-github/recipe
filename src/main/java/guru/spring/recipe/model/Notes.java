package guru.spring.recipe.model;


import lombok.*;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Entity
public class Notes {

    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    private Recipe recipe;

    @Lob
    private String recipeNotes;
}
