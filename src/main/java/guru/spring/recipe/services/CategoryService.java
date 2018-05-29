package guru.spring.recipe.services;

import guru.spring.recipe.commands.CategoryCommand;

import java.util.List;

public interface CategoryService {
    List<CategoryCommand> getAllCategoryCommand();
}
