package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Value
public class Product {
    int id;
    String name;
    String category;
    double price;
    int discount;
    int quantity;
}
