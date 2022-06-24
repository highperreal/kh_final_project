package kh.teamc.recipebackend.repository;

import kh.teamc.recipebackend.dto.FridgeDto;
import kh.teamc.recipebackend.dto.RecipeFridgeDto;

import java.util.List;

public interface FridgeRepositoryCustom {

    List<FridgeDto> searchUserFridge(Long id);
    List<FridgeDto> searchUserFridgeIngredient(Long userid, Long ingredientId);
    List<RecipeFridgeDto> searchRecipeByFridge(Long id);

}
