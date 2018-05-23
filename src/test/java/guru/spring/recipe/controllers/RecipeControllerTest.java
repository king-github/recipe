package guru.spring.recipe.controllers;

import guru.spring.recipe.model.Recipe;
import guru.spring.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class RecipeControllerTest {

    RecipeController recipeController;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;


    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);

    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        mockMvc.perform(get("/recipe"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/index"));
    }

    @Test
    public void getIndexPage() {

        // given
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe());
        recipes.add(new Recipe());

        when(recipeService.getRecipes()).thenReturn(recipes);

        ArgumentCaptor<List> listArgumentCaptor = ArgumentCaptor.forClass(List.class);

        // when
        String indexPage = recipeController.getIndexPage(model);

        // then
        assertEquals("recipe/index", indexPage);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1))
                .addAttribute(eq("recipes"), listArgumentCaptor.capture());

        List value = listArgumentCaptor.getValue();
        assertEquals(recipes, value);
    }


    @Test
    public void getRecipe() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setId(1l);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        when(recipeService.getRecipeById(anyLong())).thenReturn(Optional.of(recipe));

        mockMvc.perform(get("/recipe/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"));

    }

    @Test
    public void getRecipeWhenNoExist() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        when(recipeService.getRecipeById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/recipe/show/1"))
                .andExpect(status().isNotFound());

    }
}