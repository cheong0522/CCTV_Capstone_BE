package com.simcheong.cctv.shop.controller;

import com.simcheong.cctv.shop.dto.ShopRequest;
import com.simcheong.cctv.shop.dto.ShopResponse;
import com.simcheong.cctv.shop.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Tag(name = "SHOP", description = "상점 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/shops")
public class ShopController {

    private final ShopService shopService;
    private final TaskScheduler taskScheduler;

    @Operation(summary = "상점 등록", description = "상점 이름과 주소를 입력받아 상점을 등록한다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShopResponse.DetailDTO registerShop(@RequestBody @Valid ShopRequest.RegisterDTO requestDTO) {
        return shopService.registerShop(requestDTO);
    }

    @Operation(summary = "상점 목록 조회", description = "등록된 상점들의 목록을 조회한다.")
    @GetMapping
    public List<ShopResponse.ListDTO> getAllShops() {
        return shopService.getAllShops();
    }

    @Operation(summary = "상점 상세 조회", description = "상점 ID를 이용하여 상점의 상세 정보를 조회한다.")
    @GetMapping("/{shopId}")
    public ShopResponse.DetailDTO getShopById(@PathVariable Long shopId) {
        return shopService.getShopById(shopId);
    }

    @Operation(summary = "CCTV 등록", description = "상점 ID와 HLS 주소를 이용하여 상점의 CCTV를 등록한다.")
    @PutMapping("/{shopId}/cctv")
    public ShopResponse.DetailDTO updateShopCctv(@PathVariable Long shopId, @RequestBody @Valid ShopRequest.UpdateCctvDTO requestDTO) {
        ShopResponse.DetailDTO updatedShop = shopService.updateShopCctv(shopId, requestDTO);
        requestDTO.getHlsUrls().forEach(url -> taskScheduler.schedule(() -> shopService.runModel(url), new Date()));
        return updatedShop;
    }

    @Operation(summary = "CCTV 삭제", description = "상점 ID와 CCTV 인덱스를 이용하여 상점의 CCTV를 삭제한다.")
    @DeleteMapping("/{shopId}/cctv/{cctvIndex}")
    public ShopResponse.DetailDTO deleteCctv(@PathVariable Long shopId, @PathVariable int cctvIndex) {
        return shopService.deleteCctv(shopId, cctvIndex);
    }

    @Operation(summary = "상점 삭제", description = "상점 ID를 이용하여 상점을 삭제한다.")
    @DeleteMapping("/{shopId}")
    public void deleteShop(@PathVariable Long shopId) {
        shopService.deleteShop(shopId);
    }

    @Operation(summary = "CCTV URL 목록 조회", description = "상점 ID를 이용하여 CCTV URL 목록을 조회한다.")
    @GetMapping("/{shopId}/cctv/urls")
    public List<String> getCctvUrls(@PathVariable Long shopId) {
        return shopService.getCctvUrls(shopId);
    }

}
