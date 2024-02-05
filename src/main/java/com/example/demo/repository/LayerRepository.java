package com.example.demo.repository;


import com.example.demo.model.entities.Image;
import com.example.demo.model.entities.layer.Layer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LayerRepository extends JpaRepository<Layer, Integer> {
    @Query("SELECT COALESCE(MAX(l.position), 0) FROM Layer l WHERE l.image.id = :imageId")
    int findMaxOrderForImage(@Param("imageId") Integer imageId);

    List<Layer> findAllByImageIdOrderByPosition(Integer imageId);

    void deleteLayersByImage(Image image);
}

