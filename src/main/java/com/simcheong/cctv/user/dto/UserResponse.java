package com.simcheong.cctv.user.dto;

import com.simcheong.cctv.user.domain.User;
import lombok.Getter;

public class UserResponse {

    @Getter
    public static final class SettingDTO {
        private final String nickname;
        private final String phoneNumber;
        private final String profileImage;

        private SettingDTO(final User user) {
            nickname = user.getNickname();
            phoneNumber = user.getPhoneNumber();
            profileImage = user.getProfileImage();
        }

        public static SettingDTO from(final User user) { return new SettingDTO(user); }
    }

    @Getter
    public static final class LoginDTO {
        private final Long userId;
        private final String nickname;
        private final String refreshToken;
        private final String token;

        private LoginDTO(final User user, String token) {
            userId = user.getUserId();
            nickname = user.getNickname();
            refreshToken = user.getRefreshToken();
            this.token = token;
        }

        public static LoginDTO from(final User user, String token) {
            return new LoginDTO(user, token);
        }
    }
}
