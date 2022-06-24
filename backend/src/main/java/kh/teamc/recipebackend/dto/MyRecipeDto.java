package kh.teamc.recipebackend.dto;

import com.querydsl.core.QueryFactory;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Data
public class MyRecipeDto {
    private Long myRecipeId;
    private Long id;
    private String image;
    private String name;
    private String subtitle;
    private int likeQuantity;
    private int calorie;
    private String flavorName;

    @QueryProjection
    public MyRecipeDto(Long myRecipeId, Long id, String image, String name, String subtitle, int likeQuantity, int calorie, String flavorName) {
        this.myRecipeId = myRecipeId;
        this.id = id;
        this.image = image;
        this.name = name;
        this.subtitle = subtitle;
        this.likeQuantity = likeQuantity;
        this.calorie = calorie;
        this.flavorName = flavorName;
    }



}
