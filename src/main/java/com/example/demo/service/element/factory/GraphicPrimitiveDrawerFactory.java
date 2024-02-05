package com.example.demo.service.element.factory;

import com.example.demo.model.entities.elements.GraphicPrimitiveElement;
import com.example.demo.model.enums.GraphicPrimitiveType;
import com.example.demo.service.element.template.*;
import org.springframework.stereotype.Component;

@Component
public class GraphicPrimitiveDrawerFactory {
    public GraphicPrimitiveDrawer createGraphicPrimitiveDrawer(GraphicPrimitiveType type, GraphicPrimitiveElement element) {

        GraphicPrimitiveDrawer drawer = null;

        switch (type) {
            case circle -> drawer = new CircleDrawer(element);
            case line -> drawer = new LineDrawer(element);
            case rectangle -> drawer = new RectangleDrawer(element);
            case triangle -> drawer = new TriangleDrawer(element);
        }
        return drawer;
    }
}
