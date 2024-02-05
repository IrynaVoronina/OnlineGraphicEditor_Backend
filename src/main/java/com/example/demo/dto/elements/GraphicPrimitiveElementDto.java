package com.example.demo.dto.elements;


import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GraphicPrimitiveElementDto extends ElementDetailsDto {
    String type;
}
