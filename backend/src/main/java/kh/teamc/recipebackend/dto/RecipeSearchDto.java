package kh.teamc.recipebackend.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeSearchDto {

    private Long id;

    private String image;

    private String name;

    private String subtitle;

    private Integer likeQuantity;

    private int calorie;

    private String flavorName;

    @QueryProjection
    public RecipeSearchDto(Long id, String image, String name, String subtitle, Integer likeQuantity, int calorie, String flavorName) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.subtitle = subtitle;
        this.likeQuantity = likeQuantity;
        this.calorie = calorie;
        this.flavorName = flavorName;
    }
}

