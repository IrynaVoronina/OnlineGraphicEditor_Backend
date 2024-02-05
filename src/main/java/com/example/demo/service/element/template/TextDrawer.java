package com.example.demo.service.element.template;


import com.example.demo.model.entities.elements.Element;
import com.example.demo.model.entities.elements.TextElement;
import com.example.demo.utilities.ByteLayerProcessor;

import java.awt.*;

public class TextDrawer extends LayerRenderer {

    public TextDrawer(Element element) {
        super(element);
    }

    @Override
    protected void draw(Graphics graphics, Element element) {
        if (element instanceof TextElement textElement) {

            Font font = new Font(textElement.getFontName(), Font.PLAIN, textElement.getSize());
            graphics.setFont(font);

            double[] coordinates = textElement.getCoordinates();
            double x = coordinates[0];
            double y = coordinates[1];

            graphics.drawString(textElement.getText(), (int) x, (int) y);
        }
    }
}
