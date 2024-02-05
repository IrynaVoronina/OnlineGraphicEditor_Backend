package com.example.demo.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaveAsImageDto {
    Integer id;
    String name;
    String format;
    String location;
}