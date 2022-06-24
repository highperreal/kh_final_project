package kh.teamc.recipebackend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class IngredientType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_type_id")
    private Long id;

    @Column(name = "ingredient_name")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String image;

    @OneToMany(mappedBy = "ingredientType")
    private List<Ingredient> ingredients = new ArrayList<>();



}
