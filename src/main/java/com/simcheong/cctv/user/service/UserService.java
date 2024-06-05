package com.simcheong.cctv.user.service;

import com.simcheong.cctv.user.domain.User;
import com.simcheong.cctv.user.dto.UserRequest;
import com.simcheong.cctv.user.dto.UserResponse;
import com.simcheong.cctv.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void signUp(final UserRequest.SignUpDTO requestDTO) {
        requestDTO.encodePassword(passwordEncoder.encode(requestDTO.getPassword()));
        userRepository.save(requestDTO.mapToEntity());
    }

    @Transactional
    public UserResponse.SettingDTO login(final UserRequest.LoginDTO requestDTO) {
        User user = userRepository.findByNickname(requestDTO.getNickname())
                .orElseThrow(() -> new IllegalArgumentException("Invalid nickname or password"));

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid nickname or password");
        }

        return UserResponse.SettingDTO.from(user);
    }
}
