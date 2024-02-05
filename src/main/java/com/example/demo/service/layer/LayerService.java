package com.example.demo.service.layer;

import com.example.demo.dto.LayerOrderDto;
import com.example.demo.model.entities.Image;
import com.example.demo.model.entities.layer.Layer;
import com.example.demo.validation.exeptions.InvalidOrderException;
import com.example.demo.validation.exeptions.LayerException;

import java.io.IOException;
import java.util.List;

public interface LayerService {

    Layer getById(Integer id);

    Layer createDefaultLayerForImage(Image image, byte[] fileData);

    Layer addLayerForImage(Image image) throws IOException;

    void updateLayerOrder(List<LayerOrderDto> layerOrderList) throws InvalidOrderException;

    void deleteLayer(Layer layer) throws LayerException;

    void deleteLayersByImage(Image image);

    List<Layer> getAllLayersForImage(Integer imageId);

    byte[] mergeLayers(Image image);

    Layer cloneById(Integer id);

}
