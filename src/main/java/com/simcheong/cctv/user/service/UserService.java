package com.simcheong.cctv.user.service;

import com.simcheong.cctv.user.domain.User;
import com.simcheong.cctv.user.dto.UserRequest;
import com.simcheong.cctv.user.dto.UserResponse;
import com.simcheong.cctv.user.repository.UserRepository;
import com.simcheong.cctv._core.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signUp(final UserRequest.SignUpDTO requestDTO) {
        checkDuplicateNickname(requestDTO.getNickname());
        checkDuplicatePhoneNumber(requestDTO.getPhoneNumber());

        requestDTO.encodePassword(passwordEncoder.encode(requestDTO.getPassword()));
        userRepository.save(requestDTO.mapToEntity());
    }

    private void checkDuplicateNickname(String nickname) {
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new IllegalArgumentException("Nickname already in use");
        }
    }

    private void checkDuplicatePhoneNumber(String phoneNumber) {
        if (userRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new IllegalArgumentException("Phone number already in use");
        }
    }

    @Transactional
    public UserResponse.LoginDTO login(final UserRequest.LoginDTO requestDTO) {
        User user = userRepository.findByNickname(requestDTO.getNickname())
                .orElseThrow(() -> new IllegalArgumentException("Invalid nickname or password"));

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid nickname or password");
        }

        // Refresh Token 생성 및 저장
        String refreshToken = generateRefreshToken();
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(user.getNickname());

        return UserResponse.LoginDTO.from(user, token);
    }

    private String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        userRepository.delete(user);
    }

    @Transactional
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
