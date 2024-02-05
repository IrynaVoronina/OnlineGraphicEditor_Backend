package com.example.demo.dto.elements;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TextElementDto extends ElementDetailsDto {
    String fontName;
    int size;
    String text;
}