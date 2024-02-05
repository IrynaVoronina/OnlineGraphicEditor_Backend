package com.example.demo.model.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.example.demo.model.enums.*;
import com.example.demo.model.entities.layer.Layer;

import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    int width;
    int height;

    @Enumerated(EnumType.STRING)
    Format format;

    String location;

    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL)
    private List<Layer> layers;
}

