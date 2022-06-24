package kh.teamc.recipebackend.repository;

import kh.teamc.recipebackend.entity.MyRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyRecipeRepository extends JpaRepository<MyRecipe, Long>, MyRecipeRepositoryCustom {

}
