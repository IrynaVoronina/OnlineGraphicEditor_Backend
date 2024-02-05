package com.example.demo.model.entities.layer;


import com.example.demo.model.entities.Image;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import com.example.demo.model.entities.elements.Element;

import java.util.List;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LayerMemento {

    private final Integer id;
    int position;
    Image image;
    byte[] byteData;
    List<Element> elements;

}



