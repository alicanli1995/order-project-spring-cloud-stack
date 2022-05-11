package com.inventory.inventoryservice.service;


import com.inventory.inventoryservice.dto.request.OrderRequest;
import com.inventory.inventoryservice.dto.response.InventoryResponse;

public interface InventoryService {

    void orderPlacedReduceQuantity(OrderRequest orderRequest);

    InventoryResponse isInStockWithStock(String skuCode, Integer quantity);
}
