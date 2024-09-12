package com.simcheong.cctv.user.domain;

import com.simcheong.cctv._core.utils.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "\"user\"")
public class User extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;

    @Setter
    @Column(nullable = false, length = 45, name = "nickname")
    private String nickname;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(length = 15, name = "phone_number")
    private String phoneNumber;

    @Setter
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
