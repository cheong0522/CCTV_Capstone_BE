package com.simcheong.cctv.shop.dto;

import com.simcheong.cctv.shop.domain.Shop;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ShopRequest {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class RegisterDTO {
        @NotEmpty
        private String name;

        @NotEmpty
        private String address;

        public static RegisterDTO of(final String name, final String address) {
            return new RegisterDTO(name, address);
        }

        public Shop toEntity() {
            return Shop.builder()
                    .name(name)
                    .address(address)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class UpdateCctvDTO {
        @NotEmpty
        private String rtspUrl;

        public static UpdateCctvDTO of(final String rtspUrl) {
            return new UpdateCctvDTO(rtspUrl);
        }
    }
}
