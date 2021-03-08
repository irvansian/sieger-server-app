package sieger.repository.database;

import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.FirebaseApp;

/**
 * This class is used to initialize the firebase.
 * 
 * @author Irvan Sian Syah Putra
 *
 */
@Service
public class InitializeFirebase {
	/**
	 * Initialize the firebase with local private key.
	 * 
	 * @throws IOException
	 */
	@PostConstruct
	public static void initialize() throws IOException {
		FileInputStream serviceAccount =
		  new FileInputStream("E://serviceAccountKey.json");
		
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();

		FirebaseApp.initializeApp(options);
	}
}
