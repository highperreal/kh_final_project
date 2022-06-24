package kh.teamc.recipebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseDto<T> {
    private T data;
    private int count;
}
