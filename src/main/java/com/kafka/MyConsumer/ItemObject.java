package com.kafka.MyConsumer;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ItemObject {
    private Long itemId;
    private String itemDesc;
    private String category;
    private String itemType;
    private String status;
    private Double price;
    private Boolean pickupAllowed;
    private Boolean shippingAllowed;
    private Boolean deliveryAllowed;
}
