package sieger.repository.database;

import java.util.Optional;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

import sieger.model.Account;
import sieger.repository.AccountRepository;

public class AccountDatabase implements AccountRepository {

	@Override
	public Optional<Account> retrieveAccountByEmail(String email) {
		// TODO Auto-generated method stub
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
		
		return false;
	}

	@Override
	public boolean updateAccountById(String id, Account account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAccount(String email, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
