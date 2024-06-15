package com.simcheong.cctv.shop.repository;

import com.simcheong.cctv.shop.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    List<Shop> findAll();

    Optional<Shop> findById(Long shopId);
}
