package com.example.demo.utilities;


import com.example.demo.model.entities.layer.Layer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class ByteLayerProcessor {

    Layer layer;

    public ByteLayerProcessor(Layer layer) {
        this.layer = layer;
    }

    public BufferedImage getImageFromBytes() {
        try {
            return ImageIO.read(new ByteArrayInputStream(layer.getByteData()));
        } catch (IOException e) {
            throw new RuntimeException("Error reading image from bytes", e);
        }
    }

    public void setByteData(BufferedImage image, String format) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, format, baos);
            layer.setByteData(baos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Error converting image to bytes", e);
        }
    }
}
