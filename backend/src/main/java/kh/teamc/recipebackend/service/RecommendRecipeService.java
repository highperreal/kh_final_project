package kh.teamc.recipebackend.service;

import kh.teamc.recipebackend.entity.MainIngredient;
import kh.teamc.recipebackend.entity.Recipe;
import kh.teamc.recipebackend.repository.MainIngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendRecipeService {

    private final MainIngredientRepository mainIngredientRepository;

    public List<Recipe> recommendRecipeIdList(Long id) {
        List<MainIngredient> mainIngredients = mainIngredientRepository.findByIngredientId(id);
        return mainIngredients.stream().map(m -> m.getRecipe()).collect(Collectors.toList());
    }

    public HashSet<Recipe> distinctRecommendRecipeIdList(List<Long> ingredientIds) {

        HashSet<Recipe> recommendRecipes = new HashSet<>();

        for (Long id : ingredientIds) {
            List<Recipe> recipes = recommendRecipeIdList(id);
            recommendRecipes.addAll(recipes);
        }

        return recommendRecipes;

    }

}
