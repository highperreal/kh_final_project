package kh.teamc.recipebackend.repository;

import kh.teamc.recipebackend.entity.IngredientType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientTypeRepository extends JpaRepository<IngredientType, Long> {
}
