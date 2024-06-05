package com.simcheong.cctv.user.domain;

import com.simcheong.cctv._core.utils.AuditingFields;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "\"user\"") // 테이블 이름을 큰따옴표로 감쌈
public class User extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;

    @Setter
    @Column(nullable = false, length = 15, name = "nickname")
    private String nickname;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(length = 11, name = "phone_number")
    private String phoneNumber;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "profile_image")
    private String profileImage;

    @Builder
    public User(Long userId, String nickname, String password, String phoneNumber, String profileImage) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
    }
}
