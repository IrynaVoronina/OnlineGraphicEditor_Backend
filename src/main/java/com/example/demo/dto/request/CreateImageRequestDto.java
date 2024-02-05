package com.example.demo.dto.request;

import com.example.demo.dto.ImageDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateImageRequestDto extends ImageDto {

    String name;
    int width;
    int height;
    String format;
}



