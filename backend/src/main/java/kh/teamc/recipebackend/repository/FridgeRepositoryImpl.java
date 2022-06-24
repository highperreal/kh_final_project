package kh.teamc.recipebackend.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kh.teamc.recipebackend.dto.FridgeDto;
import kh.teamc.recipebackend.dto.QFridgeDto;
import kh.teamc.recipebackend.dto.QRecipeFridgeDto;
import kh.teamc.recipebackend.dto.RecipeFridgeDto;
import kh.teamc.recipebackend.entity.QFridge;
import kh.teamc.recipebackend.entity.QIngredient;
import kh.teamc.recipebackend.entity.QIngredientType;
import kh.teamc.recipebackend.entity.QUser;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static kh.teamc.recipebackend.entity.QFlavor.flavor;
import static kh.teamc.recipebackend.entity.QFridge.fridge;
import static kh.teamc.recipebackend.entity.QIngredient.ingredient;
import static kh.teamc.recipebackend.entity.QIngredientType.ingredientType;
import static kh.teamc.recipebackend.entity.QMainIngredient.mainIngredient;
import static kh.teamc.recipebackend.entity.QRecipe.recipe;
import static kh.teamc.recipebackend.entity.QUser.user;

@RequiredArgsConstructor
public class FridgeRepositoryImpl implements FridgeRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<FridgeDto> searchUserFridge(Long id) {
        return queryFactory
                .select(new QFridgeDto(
                        fridge.id.as("fridgeId"),
                        user.id.as("userid0"),
                        user.username,
                        ingredient.id.as("ingredientId"),
                        ingredient.name.as("ingredientName"),
                        ingredientType.name.as("ingredientTypeName"),
                        ingredientType.image.as("ingredientImage")
                ))
                .from(fridge)
                .leftJoin(fridge.user, user)
                .leftJoin(fridge.ingredient, ingredient)
                .leftJoin(ingredient.ingredientType, ingredientType)
                .where(
                        useridEq(id)
                )
                .fetch();
    }

    @Override
    public List<FridgeDto> searchUserFridgeIngredient(Long userid, Long ingredientId) {
        return queryFactory
                .select(new QFridgeDto(
                        fridge.id.as("fridgeId"),
                        user.id.as("userid0"),
                        user.username,
                        ingredient.id.as("ingredientId"),
                        ingredient.name.as("ingredientName"),
                        ingredientType.name.as("ingredientTypeName"),
                        ingredientType.image.as("ingredientImage")
                ))
                .from(fridge)
                .leftJoin(fridge.user, user)
                .leftJoin(fridge.ingredient, ingredient)
                .leftJoin(ingredient.ingredientType, ingredientType)
                .where(
                        useridEq(userid),
                        ingredientIdEq(ingredientId)
                )
                .fetch();
    }


    @Override
    public List<RecipeFridgeDto> searchRecipeByFridge(Long id) {
        List<FridgeDto> userFridge = searchUserFridge(id);
        List<String> fridgeIds = userFridge
                .stream()
                .map(FridgeDto::getIngredientName)
                .collect(Collectors.toList());

        return queryFactory
                .select(new QRecipeFridgeDto(
                        recipe.id,
                        recipe.image,
                        recipe.name,
                        recipe.subtitle,
                        recipe.likeQuantity,
                        recipe.calorie,
                        recipe.flavor.name.as("flavorName")
                ))
                .from(mainIngredient)
                .where(mainIngredient.ingredient.name.in(fridgeIds))
                .leftJoin(mainIngredient.ingredient, ingredient)
                .leftJoin(mainIngredient.recipe, recipe)
                .leftJoin(mainIngredient.recipe.flavor, flavor)
                .fetch();
    }

    private BooleanExpression useridEq(Long id) {
        return id != null ? user.id.eq(id) : null;
    }

    private BooleanExpression ingredientIdEq(Long id) { return id != null ? ingredient.id.eq(id) : null; }

    private BooleanExpression ingredientNameEq(String ingredientName) {
        return ingredientName != null ? ingredient.name.eq(ingredientName) : null;
    }


}
