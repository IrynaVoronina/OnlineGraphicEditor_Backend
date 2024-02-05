package com.example.demo.service.element.template;

import com.example.demo.model.entities.elements.Element;
import com.example.demo.service.element.factory.GraphicPrimitiveDrawer;

import java.awt.*;

public class CircleDrawer extends LayerRenderer implements GraphicPrimitiveDrawer {

    public CircleDrawer(Element element) {
        super(element);
    }

    @Override
    protected void draw(Graphics graphics, Element element) {

        int centerX = (int) element.getCoordinates()[0];
        int centerY = (int) element.getCoordinates()[1];

        int x = (int) element.getCoordinates()[2];
        int y = (int) element.getCoordinates()[3];

        int radius = (int) Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));

        graphics.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
    }
}

