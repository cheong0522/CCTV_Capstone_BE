package com.simcheong.cctv.shop.domain;

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
@Table(name = "\"shop\"") // 테이블 이름을 역따옴표로 감쌈
public class Shop extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long shopId;

    @Setter
    @Column(nullable = false, length = 100, name = "name")
    private String name;

    @Setter
    @Column(nullable = false, length = 255, name = "address")
    private String address;

    @Setter
    @Column(name = "rtsp_url")
    private String rtspUrl;

    @Builder
    public Shop(Long shopId, String name, String address, String rtspUrl) {
        this.shopId = shopId;
        this.name = name;
        this.address = address;
        this.rtspUrl = rtspUrl;
    }
}
