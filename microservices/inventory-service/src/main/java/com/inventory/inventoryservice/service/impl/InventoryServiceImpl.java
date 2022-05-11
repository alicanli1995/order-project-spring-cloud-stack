package com.inventory.inventoryservice.service.impl;


import com.inventory.inventoryservice.dto.request.OrderRequest;
import com.inventory.inventoryservice.dto.response.InventoryResponse;
import com.inventory.inventoryservice.repository.InventoryRepository;
import com.inventory.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Log4j2
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

//    private static final String debugId = "24bb6aa3-9729-4e54-a4fb-5975c57f69ba";
    private final InventoryRepository inventoryRepository;
    @Override
    public void orderPlacedReduceQuantity(OrderRequest orderRequest) {
        var product = inventoryRepository.findBySkuCode(orderRequest.getSkuCode());
        product.setQuantity(product.getQuantity() - orderRequest.getQuantity());
        inventoryRepository.save(product);
        log.info(String.format("This product -> %s has placed order. Product has a -> %d quantity.",
                product.getSkuCode(),product.getQuantity()));
    }

    @Override
    @Transactional
    public InventoryResponse isInStockWithStock(String skuCode, Integer quantity) {
        var response =  inventoryRepository.findBySkuCode(skuCode);
        if ((response.getQuantity() - quantity)>0 ) {
            return InventoryResponse.builder()
                    .skuCode(skuCode)
                    .isInStock(Boolean.TRUE).build();
        }
        else {
            log.warn(String.format("This product -> %s has not placed order. Product has a -> %d quantity.",
                    skuCode,response.getQuantity()));
            return InventoryResponse.builder()
                    .skuCode(skuCode)
                    .isInStock(Boolean.FALSE).build();
        }
    }


}
