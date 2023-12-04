package ru.idr.arbitragestatistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.idr.arbitragestatistics.config.ProjectConfig;

@SpringBootApplication
public class ArbitrageStatisticsApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProjectConfig.class);
		SpringApplication.run(ArbitrageStatisticsApplication.class, args);
	}

}
