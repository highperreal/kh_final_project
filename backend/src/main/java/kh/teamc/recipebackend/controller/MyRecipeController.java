package kh.teamc.recipebackend.controller;

import kh.teamc.recipebackend.dto.MyRecipeDto;
import kh.teamc.recipebackend.dto.response.BasicResponse;
import kh.teamc.recipebackend.dto.response.ListResponse;
import kh.teamc.recipebackend.service.MyRecipeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyRecipeController {

    private final MyRecipeService myRecipeService;

    @GetMapping("/myrecipe/v1")
    public ResponseEntity<BasicResponse> myRecipe(@RequestParam("id") Long id) {

        List<MyRecipeDto> myRecipe = myRecipeService.findMyRecipe(id);
        return ResponseEntity.ok().body(new ListResponse<>(myRecipe.size(), myRecipe));
    }

    @DeleteMapping("/myrecipe/delete")
    public void deleteMyRecipe(@RequestBody @Valid MyRecipeController.MyRecipeRequest request) {
        myRecipeService.deleteRecipe(request.userid, request.recipeid);
    }

    @PostMapping("/myrecipe/add")
    public void addMyRecipe(@RequestBody @Valid MyRecipeRequest request) {
        myRecipeService.saveMyRecipe(request.userid, request.recipeid);
    }

    @Data
    static class MyRecipeRequest {
        private Long userid;
        private Long recipeid;
    }


    @Data
    @AllArgsConstructor
    static class resultList<T> {
        private int count;
        private T data;
    }
}
