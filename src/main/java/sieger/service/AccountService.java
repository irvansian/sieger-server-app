package sieger.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sieger.model.Account;
import sieger.model.User;
import sieger.repository.AccountRepository;
import sieger.security.SecurityConfiguration;

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
	
	public String registerUser(String email, String password, String username, String surname, String forename) {
		if (userService.getUserByUsername(username).isEmpty()) return null;
		Account account = new Account(email, password, username);
		accountRepository.createAccount(account);
		User user = new User(username, surname, forename);
		userService.createNewUser(user);
		return userService.getUserByUsername(username).get().getUserId();
		
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Account> account = accountRepository.retrieveAccountByUsername(username);
		
		Collection<GrantedAuthority> grantedAuths=getAuthorities();
		boolean enables = true;  
	    boolean accountNonExpired = true;  
	    boolean credentialsNonExpired = true;  
	    boolean accountNonLocked = true;
		 
	    User userdetail = new User(account.get().getUsername(), account.get().getPassword(),
	    		, enables, grantedAuths);  
	   
	    
	    return userdetail;
	}
	
	private Collection<GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<Account> getAccountByName(String name){
		return accountRepository.retrieveAccountByUsername(name);
	}
}
