package kh.teamc.recipebackend.repository;

import kh.teamc.recipebackend.entity.Fridge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FridgeRepository extends JpaRepository<Fridge, Long>, FridgeRepositoryCustom {
}
