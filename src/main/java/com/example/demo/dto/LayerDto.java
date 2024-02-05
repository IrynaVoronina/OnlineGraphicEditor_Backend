package com.example.demo.dto;

import com.example.demo.dto.elements.ElementResponseDto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import jakarta.persistence.Lob;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LayerDto {
    Integer id;
    int position;
    Integer imageId;
    byte[] byteData;
    List<ElementResponseDto> elements;
}

