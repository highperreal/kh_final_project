package kh.teamc.recipebackend.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import kh.teamc.recipebackend.config.auth.PrincipalDetails;
import kh.teamc.recipebackend.config.jwt.JwtProperties;
import kh.teamc.recipebackend.entity.User;
import kh.teamc.recipebackend.entity.UserRole;
import kh.teamc.recipebackend.repository.UserRoleRepository;
import kh.teamc.recipebackend.service.UpdateUserService;
import kh.teamc.recipebackend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static kh.teamc.recipebackend.config.SocialLoginProperties.*;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UpdateUserService updateUserService;

    @GetMapping("/info")
    public ResponseEntity<UserDto> getUser(@RequestParam("id") Long id) {
        User user = userService.findUserById(id);
        UserDto userDto = new UserDto(user.getId(), user.getEmail(), user.getUsername(), user.getProfileImage());
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/join")
    public ResponseEntity<LoginUserResponseDto> joinUser(@RequestBody JoinUserRequestDto joinRequestDto) {

        String username = joinRequestDto.getUsername();
        String email = joinRequestDto.getEmail();
        String image = joinRequestDto.getImage();
        String password = passwordEncoder.encode(PASSWORD);
        String providerId = joinRequestDto.getProviderId();
        String accessToken = joinRequestDto.getAccessToken();

        if (!userService.checkDuplicateProviderId(providerId)) {
                UserRole userRole = UserRole.createUserRole("ROLE_USER");
                User user = User.createUser(username, password, email, image, providerId, userRole, accessToken);
                userRoleRepository.save(userRole);
                userService.join(user);
        }

        return authentication(email);
    }

    @PostMapping("/update")
    public void update(@RequestBody UserDto userDto) {
        updateUserService.updateUsername(userDto.getId(), userDto.getUsername());
    }


    public ResponseEntity<LoginUserResponseDto> authentication (String email) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, PASSWORD));
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String jwtToken = generateJwtToken(principalDetails);

        LoginUserResponseDto loginResponseDto = new LoginUserResponseDto( principalDetails.getId(), principalDetails.getEmail(),
                                                                          principalDetails.getUsername(), principalDetails.getImage(), jwtToken);
        return ResponseEntity.ok(loginResponseDto);
    }

    public String generateJwtToken(PrincipalDetails principalDetails) {

        return Jwts.builder()
                .setSubject(principalDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .claim("email", principalDetails.getUser().getEmail())
                .signWith(Keys.hmacShaKeyFor(JwtProperties.SECRET.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class JoinUserRequestDto {
        private String providerId;
        private String image;
        private String email;
        private String username;
        private String accessToken;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class LoginUserRequestDto {
        private String email;
    }

    @Data
    @AllArgsConstructor
    static class LoginUserResponseDto {
        private Long id;
        private String email;
        private String username;
        private String image;
        private String token;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class UserDto {
        private Long id;
        private String email;
        private String username;
        private String image;
    }
}
