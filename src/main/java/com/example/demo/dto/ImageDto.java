package com.example.demo.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageDto {
    Integer id;
    String name;
    int width;
    int height;
    String format;
    String location;
    List<LayerDto> layers;
}

