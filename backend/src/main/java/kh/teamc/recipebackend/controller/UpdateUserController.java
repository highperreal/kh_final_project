package kh.teamc.recipebackend.controller;

import kh.teamc.recipebackend.dto.UpdateUserDto;
import kh.teamc.recipebackend.service.UpdateUserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UpdateUserController {

    private final UpdateUserService updateUserService;

    @GetMapping("/update")
    public UpdateUserDto printUser(@RequestParam("id") Long id) {
        return updateUserService.searchUser(id);
    }

    @PostMapping(
            value = "/update/legacy",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}
            // 이미지 업로드 시 ContentType 체크가 무조건 필요함.
    )
    public void userUpload(
            @RequestPart updateRequest request,
            @RequestPart(required = false)  MultipartFile imgFile) {
        updateUserService.updateUserAll(
                request.id,
                request.username,
                imgFile
        );
        log.info("이름 : {}, 이미지 : {}", request.getUsername(), imgFile);
    }

    @PutMapping(
            value = "/update",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}
            // 이미지 업로드 시 ContentType 체크가 무조건 필요함.
    )
    public void userUploadV2(
            @RequestPart updateIdRequest idRequest,
            @RequestPart(required = false) updateNameRequest nameRequest,
            @RequestPart(required = false)  MultipartFile imgFile) {
        if (nameRequest == null) {
            updateUserService.updateImage(idRequest.id, imgFile);
        } else if (imgFile == null || imgFile.isEmpty()) {
            updateUserService.updateUsername(idRequest.id, nameRequest.name);
        } else {
            updateUserService.updateUserAll(
                    idRequest.id,
                    nameRequest.name,
                    imgFile
            );
        }
    }

    @Data
    @AllArgsConstructor
    static class resultList<T> {
        private int count;
        private T data;
    }

    @Data
    static class updateRequest {
        private Long id;
        private String username;
    }

    @Data
    static class updateIdRequest {
        private Long id;
    }

    @Data
    static class updateNameRequest {
        private String name;
    }
}
