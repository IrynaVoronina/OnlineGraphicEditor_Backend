package com.example.demo.repository;

import com.example.demo.model.entities.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    Color findByHexCode(String hexCode);
}


