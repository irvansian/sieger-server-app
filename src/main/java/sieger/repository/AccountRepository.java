package sieger.repository;

import java.util.Optional;

import sieger.model.Account;

public interface AccountRepository {
	Optional<Account> retrieveAccountByEmail(String email);
	Optional<Account> retrieveAccountByUsername(String username);
	Optional<Account> retrieveAccountById(String accountId);
	boolean createAccount(Account account);
	boolean updateAccountById(String id, Account account);
	boolean deleteAccount(String accountId);	
}
