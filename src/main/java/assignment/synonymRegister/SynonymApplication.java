package assignment.synonymRegister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("assignment.synonymRegister")
public class SynonymApplication {

	public static void main(String[] args) {
		SpringApplication.run(SynonymApplication.class, args);
	}

}
