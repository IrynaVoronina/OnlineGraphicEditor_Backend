package com.example.demo.service.element;

import com.example.demo.model.entities.elements.Element;
import com.example.demo.model.entities.elements.TextElement;

public interface ElementService {

    Element getById(Integer id);

    Element addElement(Element element);

    void deleteElement(Element element);
}
