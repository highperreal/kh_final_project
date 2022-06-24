package kh.teamc.recipebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class Flavor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flavor_id")
    private Long id;

    @Column(name = "flavor_name")
    private String name;

    @OneToMany(mappedBy = "flavor")
    @ToString.Exclude
    private List<Recipe> recipes = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Flavor flavor = (Flavor) o;
        return id != null && Objects.equals(id, flavor.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
