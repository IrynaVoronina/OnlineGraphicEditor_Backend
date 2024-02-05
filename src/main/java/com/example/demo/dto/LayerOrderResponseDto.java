package com.example.demo.dto;

import com.example.demo.dto.elements.ElementResponseDto;
import com.example.demo.model.entities.Image;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LayerOrderResponseDto {
    Integer id;
    int position;
    ImageDto image;
    byte[] byteData;
    List<ElementResponseDto> elements;
}
