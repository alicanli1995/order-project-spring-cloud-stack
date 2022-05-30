package com.inventory.inventoryservice.controller;


import com.inventory.inventoryservice.dto.request.OrderRequest;
import com.inventory.inventoryservice.dto.response.InventoryResponse;
import com.inventory.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

@CrossOrigin
@Validated
@RequestScope
@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@Log4j2
public class InventoryController {

    private final InventoryService inventoryService;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void orderSuccess(@RequestBody @Validated OrderRequest orderRequest){
        inventoryService.orderPlacedReduceQuantity(orderRequest);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse isInStockWithStock(@RequestParam String skuCode,@RequestParam Integer quantity ) {
        return inventoryService.isInStockWithStock(skuCode,quantity);
    }


}
