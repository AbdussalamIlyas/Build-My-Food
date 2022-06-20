package de.htwberlin.webtech.api.ingredient;

import de.htwberlin.webtech.api.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findIngredientsByRecipesId(Long recipeId);
    List<Ingredient> findByNameContainingIgnoreCase(String name);
}
