package kh.teamc.recipebackend.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kh.teamc.recipebackend.dto.QRecipeSearchDto;
import kh.teamc.recipebackend.dto.RecipeSearchCondition;
import kh.teamc.recipebackend.dto.RecipeSearchDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import kh.teamc.recipebackend.entity.Flavor;
import kh.teamc.recipebackend.entity.Recipe;

import javax.persistence.EntityManager;
import java.util.List;

import static kh.teamc.recipebackend.entity.QFlavor.*;
import static kh.teamc.recipebackend.entity.QRecipe.*;
import static org.springframework.util.StringUtils.*;

@Repository
@Transactional(readOnly = true)
public class RecipeRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public RecipeRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional
    public void save (Recipe recipe) {
        if (recipe.getId() == null) {
            em.persist(recipe);
        } else {
            em.merge(recipe);
        }
    }

    public Recipe findById(Long id) {
        return em.find(Recipe.class, id);
    }

    public List<Recipe> findAll() {
        return em.createQuery("select r from Recipe r join fetch r.flavor f", Recipe.class)
                .getResultList();
    }

    public List<Recipe> findByName(String name) {
        return em.createQuery("select r from Recipe r " +
                        "where r.name = :name", Recipe.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Recipe> findByFlavor(Long flavorId) {
        List<Recipe> result = em.createQuery("select distinct r from Recipe r " +
                        "join fetch r.flavor f " +
                        "where r.flavor.id = ?1", Recipe.class)
                .setParameter(1, flavorId)
                .getResultList();
        return result;
    }

    public List<Flavor> allFlavorList() {
        return em.createQuery("select f from Flavor f", Flavor.class)
                .getResultList();
    }

    public Recipe findOne(Long id) {
        return em.createQuery(
                        "select distinct r from Recipe r " +
                                "join fetch r.flavor f " +
                                "where r.id = ?1", Recipe.class)
                .setParameter(1, id)
                .getSingleResult();
    }

    public List<RecipeSearchDto> alignName(RecipeSearchCondition cond) {
        return queryFactory
                .select(new QRecipeSearchDto(
                        recipe.id,
                        recipe.image,
                        recipe.name,
                        recipe.subtitle,
                        recipe.likeQuantity,
                        recipe.calorie,
                        flavor.name.as("flavorName")
                ))
                .from(recipe)
                .leftJoin(recipe.flavor, flavor)
                .where(
                        nameLike(cond.getName()),
                        flavorEq(cond.getFlavorId())
                )
                .orderBy(orderByName(cond.getAlignName()))
                .fetch();
    }

    private OrderSpecifier<?> orderByName(Boolean alignName) {
        if (alignName == null) {
            alignName = false;
        }
        if (alignName) {
            return recipe.name.asc();
        } else {
            return recipe.name.desc();
        }
    }

    public List<RecipeSearchDto> alignLike(RecipeSearchCondition cond) {
        return queryFactory
                .select(new QRecipeSearchDto(
                        recipe.id,
                        recipe.image,
                        recipe.name,
                        recipe.subtitle,
                        recipe.likeQuantity,
                        recipe.calorie,
                        flavor.name.as("flavorName")
                ))
                .from(recipe)
                .leftJoin(recipe.flavor, flavor)
                .where(
                        nameLike(cond.getName()),
                        flavorEq(cond.getFlavorId())
                )
                .orderBy(orderByLike(cond.getAlignLike()))
                .fetch();
    }

    private OrderSpecifier<?> orderByLike(Boolean alignLike) {
        if (alignLike == null) {
            alignLike = false;
        }
        if (alignLike) {
            return recipe.likeQuantity.asc();
        } else {
            return recipe.likeQuantity.desc();
        }
    }

    public List<RecipeSearchDto> alignCalorie(RecipeSearchCondition cond) {
        return queryFactory
                .select(new QRecipeSearchDto(
                        recipe.id,
                        recipe.image,
                        recipe.name,
                        recipe.subtitle,
                        recipe.likeQuantity,
                        recipe.calorie,
                        flavor.name.as("flavorName")
                ))
                .from(recipe)
                .leftJoin(recipe.flavor, flavor)
                .where(
                        nameLike(cond.getName()),
                        flavorEq(cond.getFlavorId())
                )
                .orderBy(orderByCalorie(cond.getAlignCalorie()))
                .fetch();
    }

    private OrderSpecifier<?> orderByCalorie(Boolean alignCalorie) {
        if (alignCalorie == null) {
            alignCalorie = false;
        }
        if (alignCalorie) {
            return recipe.calorie.asc();
        } else {
            return recipe.calorie.desc();
        }
    }

    private BooleanExpression nameLike(String name) {
        return hasText(name) ? recipe.name.contains(name) : null;
    }

    private BooleanExpression flavorEq(Long flavorId) {
        return flavorId != null ? flavor.id.eq(flavorId) : null;
    }
}
