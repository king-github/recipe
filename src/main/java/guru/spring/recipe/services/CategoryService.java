package guru.spring.recipe.services;

import guru.spring.recipe.commands.CategoryCommand;
import guru.spring.recipe.model.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    List<CategoryCommand> getAllCategoryCommand();

    Set<Category> getAllCategoryByIds(Iterable<Long> ids);
}
