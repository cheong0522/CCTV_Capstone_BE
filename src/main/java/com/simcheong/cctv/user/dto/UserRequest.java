package com.simcheong.cctv.user.dto;

import com.simcheong.cctv.user.domain.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequest {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class SignUpDTO {
        @Size(min = 3, max = 45, message = "3에서 45자 이내여야 합니다.")
        @NotEmpty
        private String nickname;

        @Size(min = 8, max = 20, message = "8에서 20자 이내여야 합니다.")
        @NotEmpty
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$", message = "영문, 숫자, 특수 문자를 포함해 주세요.")
        private String password;

        @NotEmpty
        private String phoneNumber;

        public static SignUpDTO of(final String nickname, final String password, final String phoneNumber){
            return new SignUpDTO(nickname, password, phoneNumber);
        }

        public User mapToEntity() {
            return User.builder()
                    .nickname(nickname)
                    .password(password)
                    .phoneNumber(phoneNumber)
                    .build();
        }

        public void encodePassword(final String encodedPassword) { password = encodedPassword; }
    }
}
