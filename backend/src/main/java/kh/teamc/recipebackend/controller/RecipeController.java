package kh.teamc.recipebackend.controller;

import kh.teamc.recipebackend.dto.*;
import kh.teamc.recipebackend.entity.Flavor;
import kh.teamc.recipebackend.entity.Recipe;
import kh.teamc.recipebackend.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    //전체리스트
    @GetMapping("/v1")
    public ResponseDto recipeList() {
        List<Recipe> findRecipes = recipeService.allRecipes();
        List<RecipeListDto> list = findRecipes.stream()
                .map(r -> new RecipeListDto(r))
                .collect(Collectors.toList());

        return new ResponseDto(list, list.size());
    }

    //상세보기
    @GetMapping("/v1/{id}")
    public RecipeDetailDto recipeDetail(@PathVariable("id") Long id) {
        Recipe one = recipeService.findOne(id);
        RecipeDetailDto result = new RecipeDetailDto(one);

        return result;
    }

    //맛별 카테고리
    @GetMapping("/v1/list/{flavorId}")
    public ResponseDto recipeFlavorList(@PathVariable Long flavorId) {
        List<Recipe> findFlavorRecipes = recipeService.findByFlavor(flavorId);
        List<RecipeListDto> list = findFlavorRecipes.stream()
                .map(r -> new RecipeListDto(r))
                .collect(Collectors.toList());

        return new ResponseDto(list, list.size());
    }

    @GetMapping("/v1/flavor")
    public ResponseDto flavorAll() {
        List<Flavor> flavorList = recipeService.allFlavors();
        List<FlavorDto> list = flavorList.stream()
                .map(f -> new FlavorDto(f))
                .collect(Collectors.toList());

        return new ResponseDto(list, list.size());
    }

    //검색쿼리(이름정렬)
    @GetMapping("/v2/list/name")
    public ResponseDto search1(RecipeSearchCondition cond) {
        List<RecipeSearchDto> list =  recipeService.searchOrderByName(cond);
        return new ResponseDto(list, list.size());
    }

    @GetMapping("/v2/list/like")
    public ResponseDto search2(RecipeSearchCondition cond) {
        List<RecipeSearchDto> list =  recipeService.searchOrderByLike(cond);
        return new ResponseDto(list, list.size());
    }

    @GetMapping("/v2/list/calorie")
    public ResponseDto search3(RecipeSearchCondition cond) {
        List<RecipeSearchDto> list =  recipeService.searchOrderByCalorie(cond);
        return new ResponseDto(list, list.size());
    }
}
