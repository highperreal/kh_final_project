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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotEmpty
    @Column
    private String username;

    @Column
    private String email;

    @Column(columnDefinition = "TEXT")
    private String providerId;

    @Column
    private String password;

    @NotEmpty
    @Column(columnDefinition = "TEXT")
    private String profileImage;

    @Column(columnDefinition = "TEXT")
    private String accessToken;

    @OneToMany(mappedBy = "user")
    private List<Fridge> fridges = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<MyRecipe> myRecipes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_role_id")
    private UserRole userRole;

    public void setUpdateAll(String username, String profileImage) {
        this.username = username;
        this.profileImage = profileImage;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
        userRole.getUsers().add(this);
    }

    public static User createUser (String username, String password,
                                   String email, String profileImage,
                                   String providerId, UserRole userRole,
                                   String accessToken) {
        User user = new User();
        user.username = username;
        user.password = password;
        user.email = email;
        user.profileImage = profileImage;
        user.providerId = providerId;
        user.setUserRole(userRole);
        user.accessToken = accessToken;
        return user;
    }

    public void setUpdateName(String username) {
        this.username = username;
    }

    public void setUpdateImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
