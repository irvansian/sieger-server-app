package sieger.repository.database;

import java.util.Optional;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.UserRecord.UpdateRequest;

import sieger.model.Account;
import sieger.repository.AccountRepository;

public class AccountDatabase implements AccountRepository {

	@Override
	public Optional<Account> retrieveAccountByEmail(String email) {
		
		return null;
	}

	@Override
	public Optional<Account> retrieveAccountByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Account> retrieveAccountById(String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createAccount(Account account) {
		FirebaseAuth auth = FirebaseAuth.getInstance();
		UserRecord.CreateRequest userRecord = new CreateRequest();
		userRecord.setEmail(account.getEmail());
		userRecord.setDisplayName(account.getUsername());
		userRecord.setPassword(account.getPassword());
		userRecord.setUid(account.getId());
		try {
			auth.createUser(userRecord);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	public boolean updateAccountById(String id, Account account) {
		FirebaseAuth auth = FirebaseAuth.getInstance();
		UserRecord.UpdateRequest userRecord = new UpdateRequest(account.getId());
		userRecord.setEmail(account.getEmail());
		userRecord.setDisplayName(account.getUsername());
		userRecord.setPassword(account.getPassword());
		try {
			auth.updateUser(userRecord);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteAccount(String email, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
