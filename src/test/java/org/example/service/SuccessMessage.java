package org.example.service;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Value
public class SuccessMessage {
    String message;
    String msg;
}
