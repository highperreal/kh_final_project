package kh.teamc.recipebackend.repository;

import kh.teamc.recipebackend.dto.MyRecipeDto;

import java.util.List;

public interface MyRecipeRepositoryCustom {

    List<MyRecipeDto> searchMyRecipe(Long id);
    List<MyRecipeDto> searchMyRecipeOne(Long userid, Long recipeId);
}
