package sieger.service;

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
		Account account = null;
		if(type.equals("email")) {
			account = accountRepository.retrieveAccountByEmail(identifier).get();
		}
		if(type.equals("username")) {
			account = accountRepository.retrieveAccountByUsername(identifier).get();
		}
		if(account.getPassword().equals(password) && account != null) {
			return account.getUserId();
		} else {
			return null;
		}
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
		return false;
	}
	
}
