package guru.spring.recipe.services;

import guru.spring.recipe.commands.CategoryCommand;
import guru.spring.recipe.converters.CategoryToCategoryCommand;
import guru.spring.recipe.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryToCategoryCommand categoryToCategoryCommand;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Override
    public List<CategoryCommand> getAllCategoryCommand() {

        return categoryRepository.findAll().stream()
                .map(categoryToCategoryCommand::convert)
                .collect(Collectors.toList());
    }


}
