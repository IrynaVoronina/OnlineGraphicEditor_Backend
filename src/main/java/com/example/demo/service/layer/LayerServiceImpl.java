package com.example.demo.service.layer;

import com.example.demo.dto.LayerOrderDto;
import com.example.demo.validation.InputValidator;
import com.example.demo.validation.exeptions.EntityNotFoundException;
import com.example.demo.model.entities.Image;
import com.example.demo.model.entities.layer.Layer;
import com.example.demo.repository.LayerRepository;

import com.example.demo.utilities.ByteLayerProcessor;
import com.example.demo.validation.exeptions.InvalidOrderException;
import com.example.demo.validation.exeptions.LayerException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Service
@AllArgsConstructor
public class LayerServiceImpl implements LayerService {

    private final LayerRepository layerRepository;

    @Override
    @Transactional
    public Layer getById(Integer id) {
        return getOrElseThrow(id);
    }

    private Layer getOrElseThrow(Integer id) {
        return layerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Layer with id %s does not exist", id)));
    }


    @Override
    public Layer createDefaultLayerForImage(Image image, byte[] byteData) {
        Layer layer = new Layer();
        layer.setPosition(1);
        layer.setImage(image);

        if (byteData != null) {
            layer.setByteData(byteData);
        } else {
            BufferedImage bufferedLayer = createBufferedLayerForNewImage(image);
            ByteLayerProcessor byteLayerProcessor = new ByteLayerProcessor(layer);
            byteLayerProcessor.setByteData(bufferedLayer, String.valueOf(image.getFormat()));
        }

        layer = layerRepository.save(layer);

        return layer;
    }

    private BufferedImage createBufferedLayerForNewImage(Image image) {

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage bufferedLayer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bufferedLayer.createGraphics();

        g2d.setColor(java.awt.Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        g2d.dispose();

        return bufferedLayer;
    }

    @Override
    public Layer addLayerForImage(Image image) {
        int maxOrder = layerRepository.findMaxOrderForImage(image.getId());
        int newOrder = maxOrder + 1;

        Layer layer = new Layer();
        layer.setPosition(newOrder);
        layer.setImage(image);

        BufferedImage newPngBufferedLayer = createNewPngBufferedLayer(image);

        ByteLayerProcessor byteLayerProcessor = new ByteLayerProcessor(layer);
        byteLayerProcessor.setByteData(newPngBufferedLayer, "PNG");

        layer = layerRepository.save(layer);

        return layer;
    }

    private BufferedImage createNewPngBufferedLayer(Image image) {

        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage bufferedLayer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bufferedLayer.createGraphics();

        g2d.setComposite(AlphaComposite.SrcOver);

        g2d.setColor(new Color(0, 0, 0, 0));

        g2d.dispose();

        return bufferedLayer;
    }

    /*
        @Override
        public void updateLayerOrder(List<LayerOrderDto> layerOrderList) {
            for (LayerOrderDto layerOrder : layerOrderList) {
                Layer layer = getOrElseThrow(layerOrder.getId());
                layer.setPosition(layerOrder.getNewPosition());
                layerRepository.save(layer);
            }
        }
     */
    @Override
    @Transactional
    public void updateLayerOrder(List<LayerOrderDto> layerOrderList) throws InvalidOrderException {

        Map<Integer, Integer> layerPositions = new TreeMap<>(); // ключ - позиція, значення - ід шару
        int layerOrderListSize = layerOrderList.size();

        for (LayerOrderDto layerOrder : layerOrderList) {
            int newPosition = layerOrder.getNewPosition();
            layerPositions.put(newPosition, layerOrder.getId());

            Layer layer = getOrElseThrow(layerOrder.getId());
            layer.setPosition(newPosition);
            layerRepository.save(layer);

        }

        InputValidator.validateLayerOrders(layerPositions, layerOrderListSize);
    }


    @Override
    @Transactional
    public Layer cloneById(Integer id) {
        Layer layer = getOrElseThrow(id);
        Layer clone = layer.clone();
        clone.setPosition(layerRepository.findMaxOrderForImage(clone.getImage().getId()) + 1);
        layerRepository.save(clone);
        return clone;
    }

    @Override
    @Transactional
    public void deleteLayer(Layer layer) throws LayerException {
/*
        if (layer.getImage().getLayers().size() == 1) {
            throw new LayerException("It is not possible to delete this layer, " +
                    "\n because it is the only one for the image");
        }
 */
        layerRepository.deleteById(layer.getId());

        updateLayerOrderAfterDelete(layer.getImage().getId());

    }

    private void updateLayerOrderAfterDelete(Integer imageId) {
        List<Layer> allLayersForImage = getAllLayersForImage(imageId);

        int i = 1;
        for (Layer layer : allLayersForImage) {
            layer.setPosition(i++);
        }
    }

    @Override
    public void deleteLayersByImage(Image image) {
        layerRepository.deleteLayersByImage(image);
    }

    @Override
    public List<Layer> getAllLayersForImage(Integer imageId) {
        return layerRepository.findAllByImageIdOrderByPosition(imageId);
    }

    @Override
    public byte[] mergeLayers(Image image) {
        List<Layer> layers = image.getLayers();
        if (layers.isEmpty()) {
            throw new RuntimeException("No layers provided for merging.");
        }

        int height = layers.get(0).getImage().getHeight();
        int width = layers.get(0).getImage().getWidth();

        BufferedImage fullImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = fullImage.createGraphics();
        for (Layer layer : layers) {
            ByteLayerProcessor byteLayerProcessor = new ByteLayerProcessor(layer);
            BufferedImage layerImageFromBytes = byteLayerProcessor.getImageFromBytes();
            g2d.setComposite(AlphaComposite.SrcOver);
            g2d.drawImage(layerImageFromBytes, 0, 0, null);
        }

        g2d.dispose();

        return BufferedImageToBytes(fullImage, String.valueOf(image.getFormat()));
    }


    private byte[] BufferedImageToBytes(BufferedImage bufferedImage, String format) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            if (!format.equalsIgnoreCase("png")) {
                BufferedImage newImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
                newImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
                bufferedImage = newImage;
            }
            ImageIO.write(bufferedImage, format, baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error converting bufferedImage to bytes.", e);
        }
    }
}
