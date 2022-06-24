package kh.teamc.recipebackend.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kh.teamc.recipebackend.dto.MyRecipeDto;
import kh.teamc.recipebackend.dto.QMyRecipeDto;
import kh.teamc.recipebackend.entity.QMyRecipe;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static kh.teamc.recipebackend.entity.QFlavor.flavor;
import static kh.teamc.recipebackend.entity.QMyRecipe.myRecipe;
import static kh.teamc.recipebackend.entity.QRecipe.recipe;
import static kh.teamc.recipebackend.entity.QUser.user;

@RequiredArgsConstructor
public class MyRecipeRepositoryImpl implements MyRecipeRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    private BooleanExpression userIdEq(Long id) {
        return id != null ? user.id.eq(id) : null;
    }

    private BooleanExpression recipeIdEq(Long id) {
        return id != null ? recipe.id.eq(id) : null;
    }

    @Override
    public List<MyRecipeDto> searchMyRecipe(Long id) {
        return queryFactory
                .select(new QMyRecipeDto(
                        myRecipe.id.as("myRecipeId"),
                        recipe.id,
                        recipe.image,
                        recipe.name,
                        recipe.subtitle,
                        recipe.likeQuantity,
                        recipe.calorie,
                        recipe.flavor.name.as("flavorName")
                ))
                .from(myRecipe)
                .where(userIdEq(id))
                .leftJoin(myRecipe.user, user)
                .leftJoin(myRecipe.recipe, recipe)
                .leftJoin(myRecipe.recipe.flavor, flavor)
                .fetch();
    }

    @Override
    public List<MyRecipeDto> searchMyRecipeOne(Long userId, Long recipeId) {
        return queryFactory
                .select(new QMyRecipeDto(
                        myRecipe.id.as("myRecipeId"),
                        recipe.id,
                        recipe.image,
                        recipe.name,
                        recipe.subtitle,
                        recipe.likeQuantity,
                        recipe.calorie,
                        recipe.flavor.name.as("flavorName")
                ))
                .from(QMyRecipe.myRecipe)
                .where(
                        userIdEq(userId),
                        recipeIdEq(recipeId)
                )
                .leftJoin(QMyRecipe.myRecipe.user, user)
                .leftJoin(QMyRecipe.myRecipe.recipe, recipe)
                .leftJoin(QMyRecipe.myRecipe.recipe.flavor, flavor)
                .fetch();
    }
}
