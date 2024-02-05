package com.example.demo.service.element.template;

import com.example.demo.model.entities.elements.Element;

import com.example.demo.model.entities.layer.Layer;
import com.example.demo.utilities.ByteLayerProcessor;
import lombok.AllArgsConstructor;

import java.awt.*;
import java.awt.image.BufferedImage;


@AllArgsConstructor
public abstract class LayerRenderer {

    private final Element element;

    public Layer renderLayer() {

        ByteLayerProcessor byteLayerProcessor = new ByteLayerProcessor(element.getLayer());

        Layer layer = element.getLayer();

        BufferedImage bufferedImage = byteLayerProcessor.getImageFromBytes();

        Graphics graphics = bufferedImage.getGraphics();

        graphics.setColor(java.awt.Color.decode(element.getColor().getHexCode()));

        draw(graphics, element);

        graphics.dispose();

        byteLayerProcessor.setByteData(bufferedImage, "png");

        return layer;
    }


    protected abstract void draw(Graphics graphics, Element element);


}

