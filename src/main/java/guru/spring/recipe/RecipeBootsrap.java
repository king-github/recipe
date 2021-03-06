package guru.spring.recipe;


import guru.spring.recipe.model.*;
import guru.spring.recipe.repositories.CategoryRepository;
import guru.spring.recipe.repositories.RecipeRepository;
import guru.spring.recipe.repositories.UnitOfMeasureRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RecipeBootsrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    private Map<String, UnitOfMeasure> uoms;
    private Map<String, Category> categories;

    public RecipeBootsrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository,
                          UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        uoms = unitOfMeasureRepository.findAll()
                                     .stream()
                                     .collect(Collectors.toMap(UnitOfMeasure::getUom,
                                                               Function.identity()));

        categories = categoryRepository.findAll()
                                       .stream()
                                       .collect(Collectors.toMap(Category::getDescription,
                                                                 Function.identity()));


        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {

        ArrayList<Recipe> recipes = new ArrayList<>();


        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setServings(4);
        guacRecipe.setSource("Simple Recipe");
        guacRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");



        guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");
        guacRecipe.setNotes(guacNotes);

        guacRecipe.getCategories().add(getCategory("Mexican"));
        guacRecipe.getCategories().add(getCategory("American"));

        guacRecipe
            .addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), getUom("Each")))
            .addIngredient(new Ingredient("Kosher salt", new BigDecimal(".5"), getUom("Teaspoon")))
            .addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), getUom("Tablespoon")))
            .addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), getUom("Tablespoon")))
            .addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), getUom("Each")))
            .addIngredient(new Ingredient("Cilantro", new BigDecimal(2), getUom("Tablespoon")))
            .addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), getUom("Dash")))
            .addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), getUom("Each")));


        Image guacImage = new Image();
        guacImage.setImageData(loadImage("/static/images/guacamole400x400.jpg"));
        guacImage.setRecipe(guacRecipe);
        guacRecipe.setImage(guacImage);

        recipes.add(guacRecipe);


        //Yummy Tacos
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setDifficulty(Difficulty.MODERATE);

        tacosRecipe.setServings(4);
        tacosRecipe.setSource("Hard Recipe");
        tacosRecipe.setUrl("http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");

        tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");


        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");
        tacosRecipe.setNotes(tacoNotes);

        tacosRecipe
            .addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), getUom("Tablespoon")))
            .addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), getUom("Teaspoon")))
            .addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), getUom("Teaspoon")))
            .addIngredient(new Ingredient("Sugar", new BigDecimal(1), getUom("Teaspoon")))
            .addIngredient(new Ingredient("Salt", new BigDecimal(".5"), getUom("Teaspoon")))
            .addIngredient(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1),  getUom("Each")))
            .addIngredient(new Ingredient("finely grated orange zestr", new BigDecimal(1), getUom("Tablespoon")))
            .addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), getUom("Tablespoon")))
            .addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), getUom("Tablespoon")))
            .addIngredient(new Ingredient("boneless chicken thighs", new BigDecimal(4), getUom("Tablespoon")))
            .addIngredient(new Ingredient("small corn tortillasr", new BigDecimal(8), getUom("Each")))
            .addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), getUom("Cup")))
            .addIngredient(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), getUom("Each")))
            .addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), getUom("Each")))
            .addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), getUom("Pinch")))
            .addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), getUom("Each")))
            .addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), getUom("Each")))
            .addIngredient(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), getUom("Cup")))
            .addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(4), getUom("Each")));

        tacosRecipe.getCategories().add(getCategory("American"));
        tacosRecipe.getCategories().add(getCategory("Fast Food"));

        Image tacosImage = new Image();
        tacosImage.setImageData(loadImage("/static/images/tacos400x400.jpg"));
        tacosImage.setRecipe(tacosRecipe);
        tacosRecipe.setImage(tacosImage);

        recipes.add(tacosRecipe);

        return recipes;
    }

    private UnitOfMeasure getUom(String name) {
        return Optional.ofNullable(uoms.get(name)).orElseThrow(() -> new RuntimeException("UnitOfMeasure: "+name+" not found!"));
    }

    private Category getCategory(String name) {
        return Optional.ofNullable(categories.get(name)).orElseThrow(() -> new RuntimeException("Category: "+name+" not found!"));
    }

    private byte[] loadImage(String filename) {
        try {
            System.out.println("### " + filename);
            return Files.readAllBytes(new ClassPathResource(filename).getFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException("No image file: " + filename);
        }
    }
}
