package guru.spring.recipe.repositories;

import guru.spring.recipe.model.Category;
import guru.spring.recipe.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotesRepository extends JpaRepository<Notes, Long> {

        

}
