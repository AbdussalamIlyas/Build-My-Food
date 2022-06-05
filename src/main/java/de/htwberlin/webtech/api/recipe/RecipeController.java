package de.htwberlin.webtech.api.recipe;

import de.htwberlin.webtech.api.ingredient.Ingredient;
import de.htwberlin.webtech.api.ingredient.IngredientRepository;
import de.htwberlin.webtech.authentication.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RecipeController {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    IngredientRepository ingredientRepository;


    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> getAllRecipes() {

        List<Recipe> recipes = new ArrayList<>(recipeRepository.findAll());
        if (recipes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe _recipe = recipeRepository.save(new Recipe(recipe.getName()));
        return new ResponseEntity<>(_recipe, HttpStatus.CREATED);

    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Recipe with id = " + id));
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    //Add Ingredient to a Recipe
    @PutMapping("/recipes/{recipeId}/ingredients/{ingredientId}")
    ResponseEntity<Recipe> addIngredientToRecipe(
            @PathVariable Long recipeId,
            @PathVariable Long ingredientId
    ) {
        Recipe recipe = recipeRepository.findById(recipeId).get();
        Ingredient ingredient = ingredientRepository.findById(ingredientId).get();
        recipe.getIngredients().add(ingredient);
        return new ResponseEntity<>(recipeRepository.save(recipe), HttpStatus.OK);
    }

    @PutMapping("/recipes/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable("id") long id, @RequestBody Recipe recipe) {
        Recipe _recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Recipe with id = " + id));
        _recipe.setName(recipe.getName());

        return new ResponseEntity<>(recipeRepository.save(_recipe), HttpStatus.OK);
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable("id") long id) {
        recipeRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/recipes")
    public ResponseEntity<HttpStatus> deleteAllRecipes() {
        recipeRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
