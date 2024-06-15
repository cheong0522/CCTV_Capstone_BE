package com.simcheong.cctv.shop.service;

import com.simcheong.cctv.shop.domain.Shop;
import com.simcheong.cctv.shop.dto.ShopRequest;
import com.simcheong.cctv.shop.dto.ShopResponse;
import com.simcheong.cctv.shop.repository.ShopRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    @Transactional
    public ShopResponse.DetailDTO registerShop(final ShopRequest.RegisterDTO requestDTO) {
        Shop shop = shopRepository.save(requestDTO.toEntity());
        return ShopResponse.DetailDTO.from(shop);
    }

    @Transactional
    public List<ShopResponse.ListDTO> getAllShops() {
        return shopRepository.findAll().stream()
                .map(ShopResponse.ListDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ShopResponse.DetailDTO getShopById(final Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalArgumentException("Shop not found with id: " + shopId));
        return ShopResponse.DetailDTO.from(shop);
    }

    @Transactional
    public ShopResponse.DetailDTO updateShopCctv(final Long shopId, final ShopRequest.UpdateCctvDTO requestDTO) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalArgumentException("Shop not found with id: " + shopId));
        shop.setRtspUrl(requestDTO.getRtspUrl());
        return ShopResponse.DetailDTO.from(shop);
    }
}
