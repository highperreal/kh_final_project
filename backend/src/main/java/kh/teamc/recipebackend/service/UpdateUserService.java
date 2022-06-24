package kh.teamc.recipebackend.service;

import kh.teamc.recipebackend.dto.UpdateUserDto;
import kh.teamc.recipebackend.entity.User;
import kh.teamc.recipebackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UpdateUserService {

    // 저장소 url, yml에 저장
    @Value("${file.path}")
    private String path;

    private final UserRepository userRepository;

    public UpdateUserDto searchUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return UpdateUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .build();
    }

    @Transactional
    public void updateUserAll(Long id, String username, MultipartFile imgFile) {
        if (username.isEmpty()) {
            throw new IllegalStateException("이름이 비었습니다");
        }

        String filename = saveImgFile(username, imgFile);

        //String filename = saveThumbImgFile(username, imgFile);

        User user = userRepository.findById(id).orElseThrow();

        deleteOriginFile(user);

        user.setUpdateAll(username, filename);
        userRepository.save(user);

    }

    @Transactional
    public void updateUsername(Long id, String username) {
        User user = userRepository.findById(id).orElseThrow();
        user.setUpdateName(username);
        userRepository.save(user);
    }

    @Transactional
    public void updateImage(Long id, MultipartFile imgFile) {
        User user = userRepository.findById(id).orElseThrow();
        String username = user.getUsername();

        String filename = saveImgFile(username, imgFile);
        deleteOriginFile(user);

        user.setUpdateImage(filename);
        userRepository.save(user);

    }

    /*
    원본파일 삭제 메서드
     */
    private void deleteOriginFile(User user) {
        File originFile = new File(
                path + File.separator + user.getProfileImage()
        );
        if(originFile.exists()) originFile.delete();
    }
    
    
    /*
    이미지 저장 메서드
     */
    private String saveImgFile(String username, MultipartFile imgFile) {
        String now = LocalDateTime
                .now()
                .format(DateTimeFormatter.ofPattern("_yyyyMMdd-HH-mm-ss_"));
        String filename = StringUtils.cleanPath(username + now + imgFile.getOriginalFilename());
        Path copyOfLocation = Paths.get(path + File.separator + filename);

        try {
            Files.copy(imgFile.getInputStream(), copyOfLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not store file : " + imgFile.getOriginalFilename());
        }
        return filename;
    }

    /*
    썸네일 이미지 저장 메서드
     */
    private String saveThumbImgFile(String username, MultipartFile imgFile) {
        String now = LocalDateTime
                .now()
                .format(DateTimeFormatter.ofPattern("_yyyyMMdd-HH-mm-ss_"));
        String filename = StringUtils.cleanPath(username + now + imgFile.getOriginalFilename());

        try {
            Thumbnails
                    .of(imgFile.getInputStream())
                    .forceSize(300, 300)
                    .toFile(new File(path + File.separator + filename));

        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not store file : " + imgFile.getOriginalFilename());
        }

        return filename;
    }


}
