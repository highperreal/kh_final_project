package kh.teamc.recipebackend.dto;

import kh.teamc.recipebackend.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeListDto {

    private Long id;
    private String image;
    private String name;
    private String subtitle;
    private int likeQuantity;
    private int calorie;
    private Long flavorId;
    private String flavorName;

    public RecipeListDto(Recipe r) {
        this.id = r.getId();
        this.image = r.getImage();
        this.name = r.getName();
        this.subtitle = r.getSubtitle();
        this.likeQuantity = r.getLikeQuantity();
        this.calorie = r.getCalorie();
        this.flavorId = r.getFlavor().getId();
        this.flavorName = r.getFlavor().getName();
    }
}
