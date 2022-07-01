package de.htwberlin.webtech.api.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.htwberlin.webtech.api.file.File;
import de.htwberlin.webtech.api.ingredient.Ingredient;
import de.htwberlin.webtech.authentication.models.User;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recipes")
public class Recipe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "direction")
    private String direction;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "recipe_ingredient",
            joinColumns = { @JoinColumn(name = "recipe_id") },
            inverseJoinColumns = { @JoinColumn(name = "ingredient_id") }
    )
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "recipe_image",
            joinColumns =
                    { @JoinColumn(name = "recipe_id", referencedColumnName = "id") },
            inverseJoinColumns =
                    { @JoinColumn(name = "image_id", referencedColumnName = "id") })
    private File image;

    //Constructor
    public Recipe() {}

    public Recipe(String name, String direction, String description) {
        this.name = name;
        this.direction = direction;
        this.description = description;
    }

    //Getter und Setter

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void removeIngredient(long ingredientId) {
        Ingredient ingredient = this.ingredients.stream().filter(t -> t.getId() == ingredientId).findFirst().orElse(null);
        if (ingredient != null) {
            this.ingredients.remove(ingredient);
            ingredient.getRecipes().remove(this);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }
}
