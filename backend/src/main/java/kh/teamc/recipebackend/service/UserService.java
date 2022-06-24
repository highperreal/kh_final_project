package kh.teamc.recipebackend.service;

import kh.teamc.recipebackend.entity.User;
import kh.teamc.recipebackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void join(User user) {
        userRepository.save(user);
    }

    public boolean checkDuplicateProviderId(String providerId) {
        return userRepository.findDuplicateIdByProviderId(providerId).isPresent();
    }

    public User findUserById (Long id){
        return userRepository.findById(id).orElseThrow();
    }

}
