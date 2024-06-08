package com.simcheong.cctv.user.controller;

import com.simcheong.cctv._core.security.JwtTokenProvider;
import com.simcheong.cctv._core.utils.ApiUtils;
import com.simcheong.cctv.user.dto.UserRequest;
import com.simcheong.cctv.user.dto.UserResponse;
import com.simcheong.cctv.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "USER", description = "회원 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

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

    @Operation(summary = "로그인", description = "닉네임과 비밀번호를 입력받아 로그인한다.")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiUtils<?> login(
            @RequestBody @Valid @Schema(implementation = UserRequest.LoginDTO.class) final UserRequest.LoginDTO requestDTO,
            @Parameter(hidden = true) final Error error
    ){
        UserResponse.SettingDTO user = userService.login(requestDTO);
        String token = jwtTokenProvider.createToken(requestDTO.getNickname());
        return ApiUtils.success(Map.of("token", token, "user", user));
    }
}
