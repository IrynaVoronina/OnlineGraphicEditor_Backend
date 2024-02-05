package com.example.demo.service.element.template;

import com.example.demo.model.entities.elements.Element;
import com.example.demo.service.element.factory.GraphicPrimitiveDrawer;

import java.awt.*;

public class TriangleDrawer extends LayerRenderer implements GraphicPrimitiveDrawer {

    public TriangleDrawer(Element element) {
        super(element);
    }

    @Override
    protected void draw(Graphics graphics, Element element) {

        int x1 = (int) element.getCoordinates()[0];
        int y1 = (int) element.getCoordinates()[1];

        int x2 = (int) element.getCoordinates()[2];
        int y2 = (int) element.getCoordinates()[3];

        int x3 = (int) element.getCoordinates()[4];
        int y3 = (int) element.getCoordinates()[5];

        Polygon triangle = new Polygon();
        triangle.addPoint(x1, y1);
        triangle.addPoint(x2, y2);
        triangle.addPoint(x3, y3);

        graphics.drawPolygon(triangle);
    }
}
