package ru.idr.arbitragestatistics.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "ru.idr" )
@PropertySource("classpath:application.properties")
public class ProjectConfig {
    
}
