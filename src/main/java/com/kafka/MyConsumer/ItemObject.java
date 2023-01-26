package com.kafka.MyConsumer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @Override
    public String toString() {
        return "ItemObject{" +
                "itemId=" + itemId +
                ", itemDesc='" + itemDesc + '\'' +
                ", category='" + category + '\'' +
                ", itemType='" + itemType + '\'' +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", pickupAllowed=" + pickupAllowed +
                ", shippingAllowed=" + shippingAllowed +
                ", deliveryAllowed=" + deliveryAllowed +
                '}';
    }
}
