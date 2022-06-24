package kh.teamc.recipebackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse extends BasicResponse {
    private String errorMessage;
    private int errorCode;

}
