package com.simcheong.cctv.shop.domain;

import com.simcheong.cctv._core.utils.AuditingFields;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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

    @ElementCollection
    @CollectionTable(name = "cctv_urls", joinColumns = @JoinColumn(name = "shop_id"))
    @Column(name = "url")
    private List<String> hlsUrls = new ArrayList<>();

    @Builder
    public Shop(Long shopId, String name, String address, List<String> hlsUrl) {
        this.shopId = shopId;
        this.name = name;
        this.address = address;
        this.hlsUrls = hlsUrl;
    }
}
