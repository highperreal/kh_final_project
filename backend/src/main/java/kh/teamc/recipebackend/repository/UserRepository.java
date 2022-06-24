package kh.teamc.recipebackend.repository;

import kh.teamc.recipebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail (String username);
    Optional<User> findDuplicateIdByProviderId(String providerId);
}
