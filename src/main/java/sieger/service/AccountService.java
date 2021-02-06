package sieger.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sieger.model.Account;
import sieger.model.User;
import sieger.repository.AccountRepository;

@Service
public class AccountService {
	
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
		
		if(type.equals("email")) {
			Optional<Account> account = accountRepository.retrieveAccountByEmail(identifier);
			if(!account.isEmpty()) {
				if(account.get().getPassword().equals(password)) {
					return account.get().getUserId();
				}
			}
		}
		if(type.equals("username")) {
			Optional<Account> account = accountRepository.retrieveAccountByUsername(identifier);
			if(!account.isEmpty()) {
				if(account.get().getPassword().equals(password)) {
					return account.get().getUserId();
				}
			}
		}
		return null;
	}
	
	public boolean changePassword(String email, String oldPassword, String newPassword) {
		Account account = accountRepository.retrieveAccountByEmail(email).get();
		if(account.getPassword().equals(oldPassword)) {
			account.setPassword(newPassword);
		}
		accountRepository.updateAccountById(account.getId(), account);
		return true;
	}
	
	public boolean deleteAccount(String email, String password) {
		Optional<Account> account = accountRepository.retrieveAccountByEmail(email);
		if (!account.isEmpty()) {
			if(account.get().getPassword().equals(password)) {
				accountRepository.deleteAccount(account.get().getId());
				userService.deleteUser(account.get().getUserId());
				return true;
			}
		}
		return false;
	}
	
}
