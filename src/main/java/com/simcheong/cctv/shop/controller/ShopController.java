package com.simcheong.cctv.shop.controller;

import com.simcheong.cctv.shop.dto.ShopRequest;
import com.simcheong.cctv.shop.dto.ShopResponse;
import com.simcheong.cctv.shop.service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/shops")
public class ShopController {

    private final ShopService shopService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShopResponse.DetailDTO registerShop(@RequestBody @Valid ShopRequest.RegisterDTO requestDTO) {
        return shopService.registerShop(requestDTO);
    }

    @GetMapping
    public List<ShopResponse.ListDTO> getAllShops() {
        return shopService.getAllShops();
    }

    @GetMapping("/{shopId}")
    public ShopResponse.DetailDTO getShopById(@PathVariable Long shopId) {
        return shopService.getShopById(shopId);
    }

    @PutMapping("/{shopId}/cctv")
    public ShopResponse.DetailDTO updateShopCctv(@PathVariable Long shopId, @RequestBody @Valid ShopRequest.UpdateCctvDTO requestDTO) {
        return shopService.updateShopCctv(shopId, requestDTO);
    }
}
