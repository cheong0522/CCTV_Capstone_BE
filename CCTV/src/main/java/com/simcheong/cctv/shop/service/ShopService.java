package com.simcheong.cctv.shop.service;

import com.simcheong.cctv.shop.domain.Shop;
import com.simcheong.cctv.shop.dto.ShopRequest;
import com.simcheong.cctv.shop.dto.ShopResponse;
import com.simcheong.cctv.shop.repository.ShopRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final RestTemplate restTemplate;

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
        shop.getHlsUrls().addAll(requestDTO.getHlsUrls());
        return ShopResponse.DetailDTO.from(shop);
    }

    @Transactional
    public ShopResponse.DetailDTO deleteCctv(final Long shopId, final int cctvIndex) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalArgumentException("Shop not found with id: " + shopId));
        if (cctvIndex >= 0 && cctvIndex < shop.getHlsUrls().size()) {
            shop.getHlsUrls().remove(cctvIndex);
        } else {
            throw new IllegalArgumentException("Invalid CCTV index: " + cctvIndex);
        }
        return ShopResponse.DetailDTO.from(shop);
    }

    @Transactional
    public void deleteShop(final Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalArgumentException("Shop not found with id: " + shopId));
        shopRepository.delete(shop);
    }

    public void runModel(String cctvUrl) {
        String flaskUrl = "http://flask-app-url/predict";
        // CCTV URL을 Flask 애플리케이션으로 전송하여 모델을 실행
        restTemplate.postForObject(flaskUrl, cctvUrl, String.class);
    }

    @Transactional
    public List<String> getCctvUrls(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalArgumentException("Shop not found with id: " + shopId));
        return shop.getHlsUrls();
    }

}
