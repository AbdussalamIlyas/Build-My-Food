package de.htwberlin.webtech.api.ingredient;

import de.htwberlin.webtech.api.recipe.Recipe;
import de.htwberlin.webtech.api.recipe.RecipeRepository;
import de.htwberlin.webtech.authentication.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class IngredientController {

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @GetMapping("/ingredients")
    public ResponseEntity<List<Ingredient>> getAllIngredients() {

        List<Ingredient> ingredients = new ArrayList<>(ingredientRepository.findAll());
        if (ingredients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @PostMapping("/ingredients")
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        Ingredient _ingredients = ingredientRepository.save(new Ingredient(ingredient.getName(),ingredient.getCalories()));
        return new ResponseEntity<>(_ingredients, HttpStatus.CREATED);

    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable("id") long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Ingredient with id = " + id));
        return new ResponseEntity<>(ingredient, HttpStatus.OK);
    }

    @PutMapping("/ingredients/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable("id") long id, @RequestBody Ingredient ingredient) {
        Ingredient _ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Ingredient with id = " + id));
        _ingredient.setName(ingredient.getName());

        return new ResponseEntity<>(ingredientRepository.save(_ingredient), HttpStatus.OK);
    }

    @DeleteMapping("/recipes/{recipeId}/ingredients/{ingredientId}")
    public ResponseEntity<HttpStatus> deleteIngredientFromRecipe(@PathVariable(value = "recipeId") Long recipeId, @PathVariable(value = "ingredientId") Long ingredientId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Recipe with id = " + recipeId));

        recipe.removeIngredient(ingredientId);
        recipeRepository.save(recipe);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/ingredients/{id}")
    public ResponseEntity<HttpStatus> deleteIngredient(@PathVariable("id") long id) {
        ingredientRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/ingredients")
    public ResponseEntity<HttpStatus> deleteAllIngredients() {
        ingredientRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
