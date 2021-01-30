package sieger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
	
	public String registerUser(String email, String password, String username, String surname, String forname) {
		return null;
	}
	
	public String confirmLogin(String identifier, String password, String type) {
		return null;
	}
	
	public void changePassowrd(String email, String oldPassword, String newPassword) {
		
	}
	
	public void deleteAccount(String email, String password) {
		
	}
	
	
	
	
}
