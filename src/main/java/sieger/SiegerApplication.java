package sieger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sieger.controller.UserController;


@SpringBootApplication
public class SiegerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiegerApplication.class, args);
	}

}
