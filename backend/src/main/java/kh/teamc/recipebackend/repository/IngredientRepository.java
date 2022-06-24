package kh.teamc.recipebackend.repository;

import kh.teamc.recipebackend.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>, IngredientRepositoryCustom {
    Optional<Ingredient> findIngredientByName(String name);
}
