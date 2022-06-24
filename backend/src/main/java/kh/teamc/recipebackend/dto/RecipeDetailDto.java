package kh.teamc.recipebackend.dto;

import kh.teamc.recipebackend.entity.Flavor;
import kh.teamc.recipebackend.entity.Recipe;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeDetailDto {
    private Long id;
    private String image;
    private String name;
    private String subtitle;
    private int likeQuantity;
    private int calorie;
    private Long flavorId;
    private String flavorName;
    private String steps;
    private String recipeIngredient;

    public RecipeDetailDto(Recipe r) {
        this.id = r.getId();
        this.image = r.getImage();
        this.name = r.getName();
        this.subtitle = r.getSubtitle();
        this.likeQuantity = r.getLikeQuantity();
        this.calorie = r.getCalorie();
        this.flavorId = r.getFlavor().getId();
        this.flavorName = r.getFlavor().getName();
        this.steps = r.getSteps();
        this.recipeIngredient = r.getRecipeIngredient();
    }
}


