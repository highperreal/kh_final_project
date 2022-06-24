package kh.teamc.recipebackend.repository;

import kh.teamc.recipebackend.entity.MainIngredient;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainIngredientRepository extends JpaRepository<MainIngredient, Long> {

    @EntityGraph(attributePaths = {"recipe"})
    List<MainIngredient> findByIngredientId (Long id);

}
