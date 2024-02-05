package com.example.demo.repository;

import com.example.demo.model.entities.Image;
import com.example.demo.model.enums.Format;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

}
