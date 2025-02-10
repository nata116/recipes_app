package gr.rapti.project.recipes.repository;

import gr.rapti.project.recipes.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {

    Optional<Recipe> findById (Long id);
    List<Recipe> findByTitleStartingWithIgnoreCase(String title);
}