package com.example.demo.model.entities.elements;


import com.example.demo.model.entities.layer.Layer;
import com.example.demo.model.entities.Color;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public abstract class Element {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    double[] coordinates;

    @ManyToOne
    @JoinColumn(name = "colorId")
    Color color;

    @ManyToOne
    @JoinColumn(name = "layerId")
    Layer layer;
}




