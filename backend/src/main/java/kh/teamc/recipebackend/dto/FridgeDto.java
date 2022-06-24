package kh.teamc.recipebackend.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@RequiredArgsConstructor
public class FridgeDto {
    @NotEmpty
    private Long fridgeId;

    private Long userid;

    @NotEmpty
    private String username;


    private Long ingredientId;

    @NotEmpty
    private String ingredientName;

    @NotEmpty
    private String ingredientTypeName;

    @NotEmpty
    private String ingredientImage;

    @QueryProjection
    public FridgeDto(Long fridgeId, Long userid, String username, Long ingredientId, String ingredientName, String ingredientTypeName, String ingredientImage) {
        this.fridgeId = fridgeId;
        this.userid = userid;
        this.username = username;
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.ingredientTypeName = ingredientTypeName;
        this.ingredientImage = ingredientImage;
    }
}
