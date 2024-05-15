package com.simcheong.cctv.user.controller;

import com.simcheong.cctv._core.utils.ApiUtils;
import com.simcheong.cctv.user.dto.UserRequest;
import com.simcheong.cctv.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "USER", description = "회원 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "이메일, 닉네임, 비밀번호를 입력받아 회원가입한다.")
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public ApiUtils<?> signUp(
            @RequestBody @Valid @Schema(implementation = UserRequest.SignUpDTO.class) final UserRequest.SignUpDTO requestDTO,
            @Parameter(hidden = true) final Error error
    ){
        userService.signUp(requestDTO);
        return ApiUtils.success(null);
    }
}