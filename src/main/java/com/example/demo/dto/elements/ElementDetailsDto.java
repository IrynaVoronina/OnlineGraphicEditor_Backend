package com.example.demo.dto.elements;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class ElementDetailsDto {

    Integer id;
    double[] coordinates;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Integer colorId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Integer layerId;


}

