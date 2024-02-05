package com.example.demo.controller;

import com.example.demo.dto.LayerDto;
import com.example.demo.dto.LayerOrderDto;

import com.example.demo.dto.LayerOrderResponseDto;
import com.example.demo.dto.mapper.LayerMapper;
import com.example.demo.model.entities.layer.Layer;
import com.example.demo.service.layer.LayerService;
import com.example.demo.validation.exeptions.InvalidOrderException;
import com.example.demo.validation.exeptions.LayerException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/layer")
public class LayerController {

    LayerService layerService;
    LayerMapper layerMapper;


    @GetMapping("/{id}")
    public ResponseEntity<LayerOrderResponseDto> getLayerById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(layerMapper.toLayerOrderResponseDto(layerService.getById(id)));
    }

    @DeleteMapping("/{layerId}")
    public ResponseEntity<Void> deleteLayerById(@PathVariable Integer layerId) throws LayerException {
        Layer layer = layerService.getById(layerId);
        layerService.deleteLayer(layer);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/allLayers")
    public ResponseEntity<List<LayerDto>> getAllLayersForImageWithOrders(@RequestParam("imageId") Integer imageId) {
        List<Layer> layerList = layerService.getAllLayersForImage(imageId);
        List<LayerDto> dtoList = layerMapper.toDtoList(layerList);
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("/updateOrders")
    public ResponseEntity<Void> updateOrders(@RequestBody List<LayerOrderDto> layerOrderDtoList) throws InvalidOrderException {
        layerService.updateLayerOrder(layerOrderDtoList);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/clone/{layerId}")
    public ResponseEntity<LayerDto> cloneLayer(@PathVariable Integer layerId) {
        return ResponseEntity.ok().body(layerMapper.toDto(layerService.cloneById(layerId)));
    }
}



