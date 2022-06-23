package de.htwberlin.webtech.api.recipe;

import de.htwberlin.webtech.api.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findRecipesByIngredientsId(Long ingredientId);
    List<Recipe> findByNameContaining(String name);
    List<Recipe> findRecipeByName(String name);
    List<Recipe> findByUserId(Long userId);

    @Transactional
    void deleteByUserId(long userId);
}
