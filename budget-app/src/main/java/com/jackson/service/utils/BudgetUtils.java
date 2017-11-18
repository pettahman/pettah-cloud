package com.jackson.service.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.jackson.dao.CreditCardDao;
import com.jackson.dao.UserDao;
import com.jackson.model.bdg.Transaction;
import com.jackson.model.bdg.entities.CreditTransaction;
import com.jackson.model.bdg.entities.Income;
import com.jackson.model.bdg.entities.User;
import com.jackson.reader.COBuilder;
import com.jackson.reader.CsvReader;

@Component
public class BudgetUtils {
	
	private static final String BANK = "CHASE";

	@Autowired
	private CreditCardDao creditCardDao;
	
	@Autowired
	private UserDao userDao;

	public void addTransactions(String fileLocation) {

		List<Transaction> transactions = proccessTransactions(fileLocation);
		saveCreditTransactions(transactions);
	}
	
	public List<User> retrieveAllUsers() {		
		return userDao.retrieveAllUsers();
	}
	
	public List<Income> retrieveAllIncomes() {		
		return userDao.retrieveAllIncomes();
	}
	
	public List<User> retrieveUserByUserUid(Integer userUid) {
		return userDao.retrieveUserByUserUid(userUid);
	}

	@SuppressWarnings("unchecked")
	private List<Transaction> proccessTransactions(String fileLocation) {

		CsvReader csvReader = new CsvReader();
		COBuilder coBuilder = new COBuilder();

		Collection<Map<String, String>> data = (Collection<Map<String, String>>) csvReader.read(fileLocation,
				Transaction.class);

		return (List<Transaction>) coBuilder.build(Transaction.class, data);
	}

	private void saveCreditTransactions(List<Transaction> transactions) {
		List<CreditTransaction> creditCards = new ArrayList<CreditTransaction>();
		for (Transaction t : transactions) {

			CreditTransaction creditCard = new CreditTransaction();
			// ** Values from Activity CSV
			creditCard.setAmount(t.getAmount());
			creditCard.setTransDate(t.getTransDate());
			creditCard.setPostingDate(t.getPostDate());
			creditCard.setDescription(t.getDescription());
			creditCard.setType(t.getType());

			// ** Other values for table
			creditCard.setBank(BANK);
			creditCard.setUploadDate(new Date());
			creditCard.setUserUid(100001);
			creditCard.setCardNumber(4083);
			// creditCard.setExpenseCategoryUid(100000);
			creditCards.add(creditCard);
		}
		// System.out.println(creditCards);
		for (CreditTransaction c : creditCards) {
			try {
				creditCardDao.saveCreditCardTransactions(c);
			} catch (DataIntegrityViolationException e) {
				if (e.getMessage().contains("COMMON.UNIQUE_TRANSACTION")) {
					System.out.println("The Transaction already exists!");
				} else if (e.getMessage().contains("COMMON.BDG_USER_FK")) {
					System.out.println("User doesn't exist!");
				} else {
					System.out.println("Oh SHIT something else happened!");
				}
			} catch (Exception e) {
				System.out.println("Oh SHIT something that's not an Integrity Excpetion occured!");
			}
		}
	}
}
