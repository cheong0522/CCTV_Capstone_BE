package com.simcheong.cctv.user.dto;

import com.simcheong.cctv.user.domain.User;
import lombok.Getter;

public class UserResponse {

    @Getter
    public static class SettingDTO {
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
}
