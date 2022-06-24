package kh.teamc.recipebackend.dto;

import kh.teamc.recipebackend.entity.Flavor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlavorDto {
    private Long id;
    private String name;

    public FlavorDto(Flavor f) {
        this.id = f.getId();
        this.name = f.getName();
    }
}
