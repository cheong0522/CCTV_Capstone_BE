package com.simcheong.cctv.user.service;

import com.simcheong.cctv.user.dto.UserRequest;
import com.simcheong.cctv.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Transactional
    public void signUp(final UserRequest.SignUpDTO requestDTO) {
        requestDTO.encodePassword(passwordEncoder.encode(requestDTO.getPassword()));
        userRepository.save(requestDTO.mapToEntity());
    }
}