package kh.teamc.recipebackend.service;

import kh.teamc.recipebackend.dto.FridgeDto;
import kh.teamc.recipebackend.dto.IngredientDtoLegacy;
import kh.teamc.recipebackend.dto.IngredientTypeDto;
import kh.teamc.recipebackend.dto.RecipeFridgeDto;
import kh.teamc.recipebackend.entity.Fridge;
import kh.teamc.recipebackend.entity.Ingredient;
import kh.teamc.recipebackend.entity.IngredientType;
import kh.teamc.recipebackend.entity.User;
import kh.teamc.recipebackend.repository.FridgeRepository;
import kh.teamc.recipebackend.repository.IngredientRepository;
import kh.teamc.recipebackend.repository.IngredientTypeRepository;
import kh.teamc.recipebackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FridgeService {

    private final FridgeRepository fridgeRepository;
    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientTypeRepository ingredientTypeRepository;

    public List<FridgeDto> findUserFridge(Long id) {
        return fridgeRepository.searchUserFridge(id);
    }

    //v1
    /*
    public List<IngredientDtoLegacy> findIngredientAll() {
        return ingredientRepository.searchAll();
    }
    *
     */

    //v2
    public List<IngredientTypeDto> findIngredientAllV2() {
        List<IngredientType> typeList = ingredientTypeRepository.findAll();

        return typeList
                .stream()
                .map(IngredientTypeDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long createFridge(Long userId, Long ingredientId) {
        User user = userRepository.findById(userId).orElseThrow();
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow();

        validateDuplicateIngredient(user, ingredient);

        Fridge fridge = new Fridge(user, ingredient);

        fridgeRepository.save(fridge);

        return fridge.getId();

    }

    @Transactional
    public void deleteFridge(Long userId, Long ingredientId) {
        List<FridgeDto> ingredients = fridgeRepository.searchUserFridgeIngredient(userId, ingredientId);
        if(ingredients.isEmpty()) {
            throw new IllegalStateException("???????????? ????????? ????????????.");
        }
        Fridge fridge = fridgeRepository.findById(ingredients.get(0).getFridgeId()).orElseThrow();
        fridgeRepository.delete(fridge);
    }

    public List<RecipeFridgeDto> findRecipeByFridge(Long id) {
        return fridgeRepository.searchRecipeByFridge(id)
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }


    // ????????? ?????? ???????????? ?????????
    private void validateDuplicateIngredient(User user, Ingredient ingredient){
        List<FridgeDto> ingredients = fridgeRepository.searchUserFridgeIngredient(user.getId(), ingredient.getId());
        if(!ingredients.isEmpty()) {
            throw new IllegalStateException("?????? ???????????? ???????????????");
        }
    }


}
