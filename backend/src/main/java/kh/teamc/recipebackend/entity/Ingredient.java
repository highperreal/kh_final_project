package kh.teamc.recipebackend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long id;

    @Column(name ="ingredient_name")
    @NotEmpty
    private String name;

    @OneToMany(mappedBy = "ingredient")
    private List<Fridge> fridges = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_type_id")
    private IngredientType ingredientType;

}
