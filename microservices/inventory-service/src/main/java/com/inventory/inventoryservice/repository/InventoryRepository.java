package com.inventory.inventoryservice.repository;

import com.inventory.inventoryservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    Inventory findBySkuCode(String skuCode);
}
