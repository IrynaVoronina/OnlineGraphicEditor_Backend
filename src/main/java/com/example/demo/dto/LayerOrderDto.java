package com.example.demo.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LayerOrderDto {
    Integer id;
    Integer oldPosition;
    Integer newPosition;
}
