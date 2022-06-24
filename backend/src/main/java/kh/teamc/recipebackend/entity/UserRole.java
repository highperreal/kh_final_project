package kh.teamc.recipebackend.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Long id;

    @Column(name = "role_name")
    @NotEmpty
    private String name;

    @OneToMany(mappedBy = "userRole")
    private List<User> users = new ArrayList<>();

    public static UserRole createUserRole (String roleName) {
        UserRole userRole = new UserRole();
        userRole.name = roleName;
        return userRole;
    }
}