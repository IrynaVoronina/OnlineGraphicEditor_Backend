package com.example.demo.service.image.strategy;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class StrategyConfig {
    private final List<VisualEffectStrategy> visualEffectStrategies;

    @Bean
    public Map<VisualEffect, VisualEffectStrategy> setStrategyByVisualEffect() {
        Map<VisualEffect, VisualEffectStrategy> visualEffectMap = new EnumMap<>(VisualEffect.class);
        visualEffectStrategies.forEach(visualEffectStrategy ->
                visualEffectMap.put(visualEffectStrategy.getVisualEffect(), visualEffectStrategy));
        return visualEffectMap;
    }
}
