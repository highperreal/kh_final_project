package kh.teamc.recipebackend.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kh.teamc.recipebackend.dto.IngredientDtoLegacy;
// import kh.teamc.recipebackend.dto.QIngredientDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static kh.teamc.recipebackend.entity.QIngredient.ingredient;
import static kh.teamc.recipebackend.entity.QIngredientType.ingredientType;

@RequiredArgsConstructor
public class IngredientRepositoryImpl implements IngredientRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    /*
    @Override
    public List<IngredientDtoLegacy> searchAll() {
        return queryFactory
                .select(new QIngredientDto(
                        ingredient.name.as("ingredientName"),
                        ingredientType.name.as("ingredientTypeName"),
                        ingredientType.image.as("ingredientImage")
                        ))
                .from(ingredient)
                .leftJoin(ingredient.ingredientType, ingredientType)
                .fetch();
    }
    *
     */
}
