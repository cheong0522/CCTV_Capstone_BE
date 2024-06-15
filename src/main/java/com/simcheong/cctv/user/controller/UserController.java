package com.simcheong.cctv.user.controller;

import com.simcheong.cctv._core.utils.ApiUtils;
import com.simcheong.cctv.user.domain.User;
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
    ) {
        userService.signUp(requestDTO);
        return ApiUtils.success(null);
    }

    @Operation(summary = "로그인", description = "닉네임과 비밀번호로 로그인한다.")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ApiUtils<UserResponse.LoginDTO> login(
            @RequestBody @Valid @Schema(implementation = UserRequest.LoginDTO.class) final UserRequest.LoginDTO requestDTO,
            @Parameter(hidden = true) final Error error
    ) {
        return ApiUtils.success(userService.login(requestDTO));
    }

    @Operation(summary = "회원 탈퇴", description = "사용자 ID를 이용하여 회원을 탈퇴한다.")
    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiUtils<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ApiUtils.success(null);
    }

    @Operation(summary = "회원 정보 조회", description = "사용자 ID를 이용하여 회원 정보를 조회한다.")
    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiUtils<UserResponse.SettingDTO> getUserById(@PathVariable Long userId) {
        User user = userService.findById(userId);
        return ApiUtils.success(UserResponse.SettingDTO.from(user));
    }
}
