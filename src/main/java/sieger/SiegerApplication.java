package sieger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import sieger.controller.UserController;


@SpringBootApplication
public class SiegerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiegerApplication.class, args);
		
	}

}
