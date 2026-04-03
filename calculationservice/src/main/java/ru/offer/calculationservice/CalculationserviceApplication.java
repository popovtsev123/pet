package ru.offer.calculationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class CalculationserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculationserviceApplication.class, args);
	}

}
