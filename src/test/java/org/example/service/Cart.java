package org.example.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Value
public class Cart {
    @JsonProperty("total_discount")
    int totalDiscount;
    @JsonProperty("total_price")
    int totalPrice;
    @JsonProperty("cart")
    List<Product> carts;
}
