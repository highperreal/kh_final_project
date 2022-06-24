package kh.teamc.recipebackend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeSearchCondition {

    private String name;
    private Long flavorId;
    private Boolean alignName;
    private Boolean alignLike;
    private Boolean alignCalorie;
}
