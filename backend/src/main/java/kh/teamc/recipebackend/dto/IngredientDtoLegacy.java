package kh.teamc.recipebackend.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;


@Data
public class IngredientDtoLegacy {
    private String ingredientName;
    private String ingredientTypeName;
    private String ingredientImage;

    @QueryProjection
    public IngredientDtoLegacy(String ingredientName, String ingredientTypeName, String ingredientImage) {
        this.ingredientName = ingredientName;
        this.ingredientTypeName = ingredientTypeName;
        this.ingredientImage = ingredientImage;
    }

    /*
    public IngredientDtoLegacy(FridgeDto dto) {
        this.ingredientName = dto.getIngredientName();
        this.ingredientTypeName = dto.getIngredientTypeName();
        this.ingredientImage = dto.getIngredientImage();
    }
    */
}
