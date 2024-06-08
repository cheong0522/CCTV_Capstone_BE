package com.simcheong.cctv.shop.repository;

import com.simcheong.cctv.shop.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
