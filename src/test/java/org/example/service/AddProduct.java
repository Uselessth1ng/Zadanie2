package org.example.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;


@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Value
public class AddProduct  {
    @JsonProperty("product_id")
    int productId;
    int quantity;
}