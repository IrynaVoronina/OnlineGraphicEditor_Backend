package com.example.demo.controller;

import com.example.demo.dto.ImageDto;
import com.example.demo.dto.LayerDto;
import com.example.demo.dto.mapper.ImageMapper;
import com.example.demo.dto.mapper.LayerMapper;


import com.example.demo.dto.request.CreateImageRequestDto;
import com.example.demo.dto.request.SaveAsImageDto;
import com.example.demo.model.entities.Image;
import com.example.demo.service.image.ImageService;
import com.example.demo.service.image.ImageStorageService;
import com.example.demo.service.layer.LayerService;

import com.example.demo.validation.exeptions.ExistenceException;
import com.example.demo.validation.exeptions.LocationException;
import com.example.demo.validation.exeptions.UnsupportedFileFormatException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/image")
@CrossOrigin(origins = "*")
public class ImageController {

    private final ImageStorageService imageStorageService;
    private final ImageService imageService;
    private final ImageMapper imageMapper;
    private final LayerService layerService;
    private final LayerMapper layerMapper;

    public ImageController(@Qualifier("ImageStorageServiceProxy") ImageStorageService imageStorageService,
                           ImageService imageService,
                           ImageMapper imageMapper,
                           LayerService layerService,
                           LayerMapper layerMapper) {
        this.imageStorageService = imageStorageService;
        this.imageService = imageService;
        this.imageMapper = imageMapper;
        this.layerService = layerService;
        this.layerMapper = layerMapper;
    }


    @PostMapping("/create")
    public ResponseEntity<ImageDto> createImage(@RequestBody CreateImageRequestDto imageDto) {
        try {
            ImageDto createdImage = imageMapper.toDto(imageService.createImage(imageMapper.toEntity(imageDto)));
            return ResponseEntity.status(HttpStatus.CREATED).body(createdImage);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @GetMapping("/{imageId}")
    public ResponseEntity<ImageDto> getImageById(@PathVariable Integer imageId) {
        return ResponseEntity.ok().body(imageMapper.toDto(imageService.getById(imageId)));
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageDto> uploadImage(@RequestParam("file") MultipartFile file) throws UnsupportedFileFormatException {
        try {
            return ResponseEntity.ok().body(imageMapper.toDto(imageService.uploadImage(file)));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @PostMapping("/addLayer")
    public ResponseEntity<LayerDto> addLayer(@RequestParam("imageId") Integer imageId) {
        try {
            return ResponseEntity.ok().body(layerMapper.toDto(layerService.addLayerForImage(imageService.getById(imageId))));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveImage(@RequestParam("imageId") Integer imageId) throws LocationException, ExistenceException {

        try {
            imageStorageService.saveImage(imageService.getById(imageId));
            return ResponseEntity.ok().body("Image saved");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Something wrong with bytes");
        }
    }

    @PostMapping("/saveAs")
    public ResponseEntity<String> saveImageAs(SaveAsImageDto saveAsImageDto) throws ExistenceException, UnsupportedFileFormatException {
        try {
            imageStorageService.saveImageAs(saveAsImageDto);
            return ResponseEntity.ok().body("Image saved as");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Something wrong with bytes");
        }
    }

    @PutMapping("/{imageId}/applyVisualEffect")
    public ResponseEntity<ImageDto> applyVisualEffect(@PathVariable Integer imageId,
                                                      @RequestParam("effect") String effect) {
        try {
            Image image = imageService.applyVisualEffect(imageService.getById(imageId), effect);
            return ResponseEntity.ok().body(imageMapper.toDto(image));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}

