package com.backend.controller;

import com.backend.dto.CMRespDto;
import com.backend.dto.user.UserReqDto;
import com.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signUp")
    public ResponseEntity<?> signUp(@RequestBody UserReqDto userReqDto) {

        try {
            userService.validate(userReqDto);
            userService.join(userReqDto);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(CMRespDto.builder().isSuc(false).msg(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(CMRespDto.builder().isSuc(true).msg("회원가입 성공").build(), HttpStatus.OK);
    }
}
