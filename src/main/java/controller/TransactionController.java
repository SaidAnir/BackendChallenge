package controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import model.Transaction;
import service.TransactionService;

@RestController
@RequestMapping("/n26/api")
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@RequestMapping(value = "/transactions", method = RequestMethod.POST)
	public ResponseEntity<?> processTransaction(@RequestBody Transaction transaction) {

			long time = new Date().getTime() - transaction.getTimestamp();
			
			// check if difference of time is in the range ]0,60000]
			if (time > 0.0 && time <= transactionService.TIME_LIMIT) {

				// Append transaction to the list
				// And increment the total amount
				transactionService.addTransactionToList(transaction);
				transactionService.incrementTransactionSumValue(transaction.getAmount());

				return new ResponseEntity<>(HttpStatus.CREATED);
			} else
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}

}
