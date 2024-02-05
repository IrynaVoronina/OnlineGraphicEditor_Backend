package com.example.demo.service.element.template;

import com.example.demo.model.entities.elements.Element;
import com.example.demo.service.element.factory.GraphicPrimitiveDrawer;

import java.awt.*;

public class RectangleDrawer extends LayerRenderer implements GraphicPrimitiveDrawer {

    public RectangleDrawer(Element element) {
        super(element);
    }

    @Override
    protected void draw(Graphics graphics, Element element) {

        int x1 = (int) element.getCoordinates()[0];
        int y1 = (int) element.getCoordinates()[1];

        int x2 = (int) element.getCoordinates()[2];
        int y2 = (int) element.getCoordinates()[3];

        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);

        graphics.drawRect(x1, y1, width, height);
    }
}
