package de.htwberlin.webtech.authentication.controllers;

import de.htwberlin.webtech.api.ingredient.Ingredient;
import de.htwberlin.webtech.api.ingredient.IngredientRepository;
import de.htwberlin.webtech.api.recipe.Recipe;
import de.htwberlin.webtech.api.recipe.RecipeRepository;
import de.htwberlin.webtech.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/public")
public class PublicContentController {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @GetMapping("/recipes/ingredients/{id}")
    public ResponseEntity<List<Recipe>> getRecipeByIngredientId(@PathVariable("id") long id) {
        List<Recipe> recipes = recipeRepository.findRecipesByIngredientsId(id);

        if (recipes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> getAllRecipes(@RequestParam(required = false) String name) {
        List<Recipe> recipes = new ArrayList<>();
        List<Ingredient> searchedIngredients;
        if (name == null)
            recipes.addAll(recipeRepository.findAll());
        else {
            searchedIngredients = ingredientRepository.findByNameContainingIgnoreCase(name);
            if(searchedIngredients.size() != 1)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            else
                recipes.addAll(recipeRepository.findRecipesByIngredientsId(searchedIngredients.get(0).getId()));
        }

        if (recipes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/recipes/{id}/ingredients")
    public ResponseEntity<List<Ingredient>> getAllIngredientsOfRecipe(@PathVariable(value = "id") Long id) {
        List<Ingredient> ingredients = ingredientRepository.findIngredientsByRecipesId(id);

        if (ingredients.isEmpty()) {

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Recipe with id = " + id));
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }


}
