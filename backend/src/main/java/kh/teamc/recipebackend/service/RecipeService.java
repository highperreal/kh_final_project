package kh.teamc.recipebackend.service;

import kh.teamc.recipebackend.dto.RecipeSearchCondition;
import kh.teamc.recipebackend.dto.RecipeSearchDto;
import kh.teamc.recipebackend.entity.Flavor;
import kh.teamc.recipebackend.entity.Recipe;
import kh.teamc.recipebackend.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository repository;
    private final EntityManager em;

    //레시피 전체 조회
    public List<Recipe> allRecipes() {
        return repository.findAll();
    }

    //id로 한건조회(상세보기)
    public Recipe findOne(Long id) {
        return repository.findOne(id);
    }

    //맛별 카테고리 검색
    public List<Recipe> findByFlavor(Long flavor) {
        return repository.findByFlavor(flavor);
    }
    
    //검색 동적쿼리(이름정렬)
    public List<RecipeSearchDto> searchOrderByName(RecipeSearchCondition cond) {
        return repository.alignName(cond);
    }

    //검색 동적쿼리(좋아요순)
    public List<RecipeSearchDto> searchOrderByLike(RecipeSearchCondition cond) {
        return repository.alignLike(cond);
    }

    //검색 동적쿼리(칼로리순)
    public List<RecipeSearchDto> searchOrderByCalorie(RecipeSearchCondition cond) {
        return repository.alignCalorie(cond);
    }

    //모든 맛 리스트
    public List<Flavor> allFlavors() {
        return repository.allFlavorList();
    }
}
