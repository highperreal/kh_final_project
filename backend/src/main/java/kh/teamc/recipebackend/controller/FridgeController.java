package kh.teamc.recipebackend.controller;

import kh.teamc.recipebackend.dto.FridgeDto;
import kh.teamc.recipebackend.dto.IngredientTypeDto;
import kh.teamc.recipebackend.dto.RecipeFridgeDto;
import kh.teamc.recipebackend.dto.response.BasicResponse;
import kh.teamc.recipebackend.dto.response.ErrorResponse;
import kh.teamc.recipebackend.dto.response.ListResponse;
import kh.teamc.recipebackend.service.FridgeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FridgeController {
    private final FridgeService fridgeService;

    // 회원의 냉장고 반환

    @GetMapping("/fridge/v1")
    public ResponseEntity<BasicResponse> userFridgeV1(@RequestParam("id") Long id) {
        List<FridgeDto> fridgeList = fridgeService.findUserFridge(id);
        if (fridgeList.isEmpty()) {
            System.out.println("empty");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("냉장고가 비었습니다.", 404));
        }
        log.info("entity : {}", fridgeList);

        return ResponseEntity.ok()
                .body(new ListResponse<>(fridgeList.size(), fridgeList));

    }

    // 전체 재료 반환 메소드
    /*
    @GetMapping("/fridge/v2")
    public ResponseEntity<BasicResponse> allFridge() {

        List<IngredientDtoLegacy> ingredientAllList = fridgeService.findIngredientAll();

        return ResponseEntity.ok()
                .body(new ListResponse<>(ingredientAllList.size(), ingredientAllList));


    }
     */

    // 전체 재료 개선판
    @GetMapping("/fridge/v3")
    public ResponseEntity<BasicResponse> allFridge2() {
        List<IngredientTypeDto> ingredientTypeDtoList = fridgeService.findIngredientAllV2();

        return ResponseEntity.ok()
                .body(new ListResponse<>(ingredientTypeDtoList.size(), ingredientTypeDtoList));
    }

    @PostMapping("/fridge/create")
    public CreateFridgeResponse createFridgeV1(@RequestBody @Valid FridgeRequest request) {
        Long id = fridgeService.createFridge(request.userid, request.ingredientid);
        return new CreateFridgeResponse(id);
    }


    // 단건 삭제
    @DeleteMapping("/fridge/delete")
    public void deleteFridgeV1 (@RequestBody @Valid FridgeRequest request) {

        fridgeService.deleteFridge(request.userid, request.ingredientid);

        List<FridgeDto> fridgeList = fridgeService.findUserFridge(request.userid);

    }


    // 전체 삭제
    @DeleteMapping("/fridge/delete-all")
    public void deleteFridgeAll(@RequestBody @Valid DeleteAllRequest request) {
        List<FridgeDto> userFridge = fridgeService.findUserFridge(request.userid);
        userFridge
                .forEach(e -> fridgeService.deleteFridge(e.getUserid(), e.getIngredientId()));
    }



    @GetMapping("/fridge/find")
    public ResponseEntity<BasicResponse> userFridgeRecommend(@RequestParam("id") Long id) {
        List<RecipeFridgeDto> sortedRecipes = fridgeService.findRecipeByFridge(id)
                .stream()
                .sorted(Comparator.comparing(RecipeFridgeDto::getId))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(new ListResponse<>(sortedRecipes.size(), sortedRecipes));
    }

    @Data
    @AllArgsConstructor
    static class CreateFridgeResponse {
        private Long id;
    }

    @Data
    static class FridgeRequest {
        private Long userid;
        private Long ingredientid;
    }

    @Data
    static class DeleteAllRequest {
        private Long userid;
    }
}
