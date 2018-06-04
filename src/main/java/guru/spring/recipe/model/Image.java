package guru.spring.recipe.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "description"})
@Entity
public class Image {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] imageData;

    @OneToOne
    private Recipe recipe;

}
