package kh.teamc.recipebackend.dto;

import kh.teamc.recipebackend.entity.Ingredient;
import kh.teamc.recipebackend.entity.IngredientType;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class IngredientTypeDto {
    private Long ingredientTypeId;
    private String ingredientTypeName;
    private String image;
    private List<IngredientDto> ingredientDtos;

    public IngredientTypeDto(IngredientType type) {
        this.ingredientTypeId = type.getId();
        this.ingredientTypeName = type.getName();
        this.image = type.getImage();
        this.ingredientDtos = type.getIngredients()
                .stream()
                .map(IngredientDto::new)
                .collect(Collectors.toList());
    }


}
