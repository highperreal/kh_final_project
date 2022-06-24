package kh.teamc.recipebackend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class UpdateUserDto {
    private Long id;
    private String username;
    private String email;
    private String profileImage;
}
