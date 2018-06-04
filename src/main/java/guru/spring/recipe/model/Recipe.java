package guru.spring.recipe.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "description", "description", "prepTime", "cookTime", "servings", "source", "url", "difficulty"})
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @OneToOne(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Image image;

    @OneToOne(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Notes notes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private List<Ingredient> ingredients = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "recipe_category",
               joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();


    public Recipe addIngredient(Ingredient ingredient) {

        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);

        return this;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
        if (notes != null)
            notes.setRecipe(this);
    }
}
