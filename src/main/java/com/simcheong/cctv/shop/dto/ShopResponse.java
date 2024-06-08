package com.simcheong.cctv.shop.dto;

import com.simcheong.cctv.shop.domain.Shop;
import lombok.Getter;

public class ShopResponse {

    @Getter
    public static final class DetailDTO {
        private final Long shopId;
        private final String name;
        private final String address;
        private final String rtspUrl;

        private DetailDTO(final Shop shop) {
            this.shopId = shop.getShopId();
            this.name = shop.getName();
            this.address = shop.getAddress();
            this.rtspUrl = shop.getRtspUrl();
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
