package kh.teamc.recipebackend.controller;

import kh.teamc.recipebackend.entity.Recipe;
import kh.teamc.recipebackend.service.RecommendRecipeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecommendRecipeController {

    private final RecommendRecipeService recommendRecipeService;

    @GetMapping("/recommendRecipes")
    public Result<RecommendRecipeDto> test(@RequestParam("ingredientIds") List<Long> ingredientIds) {

        HashSet<Recipe> recipes = recommendRecipeService.distinctRecommendRecipeIdList(ingredientIds);

        List<RecommendRecipeDto> recommendRecipes = recipes.stream()
                .map(m -> new RecommendRecipeDto(
                        m.getId(), m.getName(),
                        m.getSubtitle(), m.getImage(),
                        m.getCalorie(), m.getRecipeIngredient(),
                        m.getSteps(), m.getLikeQuantity()))
                .collect(Collectors.toList());

        return new Result<>(recommendRecipes.size(), recommendRecipes);
    }

    @Data
    @AllArgsConstructor
    static class RecommendRecipeDto {
        private Long id;
        private String name;
        private String subtitle;
        private String image;
        private int calorie;
        private String recipeIngredient;
        private String steps;
        private int likeQuantity;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private List<T> data;
    }


}
