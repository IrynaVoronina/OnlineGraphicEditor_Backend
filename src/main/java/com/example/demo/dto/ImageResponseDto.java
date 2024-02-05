package com.example.demo.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageResponseDto {
    String name;
    int width;
    int height;
    String format;
    String location;
}