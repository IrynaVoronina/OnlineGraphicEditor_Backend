package com.example.demo.model.entities.layer;


import org.springframework.stereotype.Component;

@Component
public class LayerCaretaker {

    private LayerMemento layerMemento;

    public LayerMemento getLayerMemento() {
        return layerMemento;
    }

    public void setLayerMemento(LayerMemento layerMemento) {
        this.layerMemento = layerMemento;
    }

}

