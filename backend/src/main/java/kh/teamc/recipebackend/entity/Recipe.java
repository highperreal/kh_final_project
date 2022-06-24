package kh.teamc.recipebackend.entity;

import kh.teamc.recipebackend.exception.NotEnoughLikeException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    @Column(name = "recipe_name")
    private String name;

    @Column(name ="subtitle")
    private String subtitle;

    @Column(columnDefinition = "TEXT")
    private String steps;

    @Column
    private int calorie;

    @Column(columnDefinition = "TEXT")
    private String recipeIngredient;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column
    private int likeQuantity;

    @OneToMany(mappedBy = "recipe")
    private List<MainIngredient> mainIngredientList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flavor_id")
    private Flavor flavor;

    public void addLike() {
        this.likeQuantity = this.likeQuantity + 1;
    }

    public void removeLike() {
        int restLike = this.likeQuantity - 1;
        if (restLike <0) {
            throw new NotEnoughLikeException("need more like");
        }
        this.likeQuantity = restLike;
    }

}
