package sieger.controller;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sieger.model.authentication.AuthenticationRequest;
import sieger.service.AccountService;
import sieger.util.JwtUtil;

@RestController
public class AccountController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@GetMapping("/")
	public String returnHello() {
		
		return "Hello";
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), 
							authenticationRequest.getPassword()));
		} catch(BadCredentialsException e) {
			throw new Exception("Incorrect name or password", e);
		}
		
		final UserDetails userDetails = accountService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(jwt);
		
	}
	
	//these are the real methods
	@PostMapping
	public ResponseEntity<String> registerUser(String email, String password, String username, String surname, String forname) {
		String result = accountService.registerUser(email, password, username, surname, forname);
		if(result != null) {
			return ResponseEntity.ok(result);
		}
		return new ResponseEntity<String>(null, null, 
				HttpStatus.SC_NOT_FOUND);
	}
	
	public ResponseEntity<String> confirmLogin(String identifier, String password, String type) {
		String result = accountService.confirmLogin(identifier, password, type);
		if(result != null) {
			return ResponseEntity.ok(result);
		}
		return new ResponseEntity<String>(null, null, 
				HttpStatus.SC_NOT_FOUND);
	}
	
	public ResponseEntity<String> changePassowrd(String email, String oldPassword, String newPassword) {
		boolean result = accountService.changePassword(email, oldPassword, newPassword);
		if(result) {
			return new ResponseEntity<String>(null, null, 
					HttpStatus.SC_OK);
		}else {
		    return new ResponseEntity<String>(null, null, 
				HttpStatus.SC_NOT_FOUND);
		}
	}
	
	public ResponseEntity<String> deleteAccount(String email, String password) {
		boolean result = accountService.deleteAccount(email, password);
		if(result) {
			return new ResponseEntity<String>(null, null, 
					HttpStatus.SC_OK);
		}else {
		    return new ResponseEntity<String>(null, null, 
				HttpStatus.SC_NOT_FOUND);
		}
	}
	
	
	
	
}
