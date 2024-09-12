package com.simcheong.cctv.shop.dto;

import com.simcheong.cctv.shop.domain.Shop;
import lombok.Getter;

import java.util.List;

public class ShopResponse {

    @Getter
    public static final class DetailDTO {
        private final Long shopId;
        private final String name;
        private final String address;
        private final List<String> hlsUrls;

        private DetailDTO(final Shop shop) {
            this.shopId = shop.getShopId();
            this.name = shop.getName();
            this.address = shop.getAddress();
            this.hlsUrls = shop.getHlsUrls();
        }

        public static DetailDTO from(final Shop shop) {
            return new DetailDTO(shop);
        }
    }

    @Getter
    public static final class ListDTO {
        private final Long shopId;
        private final String name;
        private final String address;

        private ListDTO(final Shop shop) {
            this.shopId = shop.getShopId();
            this.name = shop.getName();
            this.address = shop.getAddress();
        }

        public static ListDTO from(final Shop shop) {
            return new ListDTO(shop);
        }
    }
}
