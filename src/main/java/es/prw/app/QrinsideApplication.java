package es.prw.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "es.prw")
public class QrinsideApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrinsideApplication.class, args);
	}

}
