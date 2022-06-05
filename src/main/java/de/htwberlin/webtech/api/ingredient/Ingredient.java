package de.htwberlin.webtech.api.ingredient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.htwberlin.webtech.api.recipe.Recipe;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ingredients")
public class Ingredient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "calories")
    private int calories;

    @JsonIgnore
    @ManyToMany(mappedBy = "ingredients", fetch = FetchType.EAGER)
    private Set<Recipe> recipes = new HashSet<>();

    //Constructor
    public Ingredient() {}

    public Ingredient(String name, int calories) {
        this.name = name;
        this.calories = calories;
    }


    //Getter und Setter

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
