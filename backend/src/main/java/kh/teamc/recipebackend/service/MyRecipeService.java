package kh.teamc.recipebackend.service;

import kh.teamc.recipebackend.dto.MyRecipeDto;
import kh.teamc.recipebackend.entity.MyRecipe;
import kh.teamc.recipebackend.entity.Recipe;
import kh.teamc.recipebackend.entity.User;
import kh.teamc.recipebackend.repository.MyRecipeRepository;
import kh.teamc.recipebackend.repository.RecipeRepository;
import kh.teamc.recipebackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MyRecipeService {

    private final MyRecipeRepository myRecipeRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public List<MyRecipeDto> findMyRecipe(Long id) {
        return myRecipeRepository.searchMyRecipe(id);
    }

    @Transactional
    public void deleteRecipe(Long userId, Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId);
        List<MyRecipeDto> myRecipeDto = myRecipeRepository.searchMyRecipeOne(userId, recipeId);
        MyRecipe dRecipe = myRecipeRepository.findById(myRecipeDto.get(0).getMyRecipeId()).orElseThrow();
        myRecipeRepository.delete(dRecipe);
        recipe.removeLike();
        recipeRepository.save(recipe);
    }

    @Transactional
    public void saveMyRecipe(Long userId, Long recipeId) {
        User user = userRepository.findById(userId).orElseThrow();
        Recipe recipe = recipeRepository.findById(recipeId);

        MyRecipe myRecipe = MyRecipe.builder()
                .user(user)
                .recipe(recipe)
                .build();
        myRecipeRepository.save(myRecipe);

        recipe.addLike();
    }
}
