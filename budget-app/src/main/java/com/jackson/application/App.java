package com.jackson.application;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;

import com.jackson.dao.CreditCardDao;
import com.jackson.model.bdg.Transaction;
import com.jackson.model.bdg.entities.CreditTransaction;
import com.jackson.reader.COBuilder;
import com.jackson.reader.CsvReader;
import com.jackson.service.utils.BudgetUtils;

public class App {
	
	private static final String FILE_LOCATION = "C:/Users/Pettah/Downloads/Test_file.csv";
	
	@SuppressWarnings({ "resource" })
	public static void main(String[] args) throws ParseException {

		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		BudgetUtils utils = (BudgetUtils) context.getBean("budgetUtils");

//		utils.addTransactions(FILE_LOCATION);
		
//		System.out.println("" + utils.retrieveAllUsers());
		
		System.out.println("" + utils.retrieveUserByUserUid(100001));
		
//		System.out.println("" + utils.retrieveAllIncomes());
	}
}
