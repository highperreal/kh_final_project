package kh.teamc.recipebackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListResponse<T> extends BasicResponse {
    private int count;
    private T data;
}
