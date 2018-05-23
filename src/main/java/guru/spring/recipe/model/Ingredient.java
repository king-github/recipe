package guru.spring.recipe.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "description", "amount"})
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private BigDecimal amount;

    @ManyToOne
    private Recipe recipe;

    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMesure unitOfMesure;


    public Ingredient(String description, BigDecimal amount, UnitOfMesure unitOfMesure) {
        this.description = description;
        this.amount = amount;
        this.recipe = recipe;
        this.unitOfMesure = unitOfMesure;
    }
}
