package ru.idr.arbitragestatistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class ArbitrageStatisticsApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = 
			new AnnotationConfigApplicationContext(
				ru.idr.arbitragestatistics.config.ProjectConfig.class
				// ,
				// ru.idr.datamarkingeditor.config.ProjectConfig.class
			);

		SpringApplication.run(ArbitrageStatisticsApplication.class, args);

		context.close();
	}

}
