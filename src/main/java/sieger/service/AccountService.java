package sieger.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sieger.repository.AccountRepository;

@Service
public class AccountService implements UserDetailsService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserService userService;
	

	public AccountService(AccountRepository accountRepository, UserService userService) {
		this.accountRepository = accountRepository;
		this.userService = userService;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new User("foo", "password", new ArrayList<>());
	}
	
	//ini mulai sama kaya di entwurf
	
	public String registerUser(String email, String password, String username, String surname, String forename) {
		return null;
	}
	
	public String confirmLogin(String identifier, String password, String type) {
		return null;
	}
	
	public boolean changePassword(String email, String oldPassword, String newPassword) {
		return false;
	}
	
	public boolean deleteAccount(String email, String password) {
		return false;
	}
}
