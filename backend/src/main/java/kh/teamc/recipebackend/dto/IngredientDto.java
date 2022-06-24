package kh.teamc.recipebackend.dto;

import kh.teamc.recipebackend.entity.Ingredient;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class IngredientDto {
    private long ingredientId;
    private String ingredientName;

    public IngredientDto(Ingredient ingredient) {
        this.ingredientId = ingredient.getId();
        this.ingredientName = ingredient.getName();
    }

}
