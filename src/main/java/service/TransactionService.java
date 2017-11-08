package service;

import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import model.Transaction;

@Service
@EnableScheduling

public class TransactionService {

	public final int TIME_LIMIT = 60000;

	public static List<Transaction> transactions = Collections.synchronizedList(new ArrayList<>());
	public static double transactionsSum = 0.0;


	public static PriorityQueue<Double> queue = new PriorityQueue<Double>(16);

	public TransactionService() {
		super();
	}

	/*
	 * Thread scheduled to run every 1ms to remove the expired transactions from
	 * the list. It basically checks the transaction at index 0 since the list
	 * is ordered by timestamp
	 */
	@Scheduled(fixedDelay = 1)
	public void doSomething() {
		if (!transactions.isEmpty()) {
			Transaction transaction = transactions.get(0);
			System.err.println(" elapsed time for top " + (new Date().getTime() - transaction.getTimestamp()) / 1000);
			if (new Date().getTime() - transaction.getTimestamp() > TIME_LIMIT) {
				subsFromSum(transaction.getAmount());
				transactions.remove(transaction);
			}
		}

	}

	// Renders all transactions
	public List<Transaction> getAllTransactions() {
		return transactions;
	}

	// Appends a transaction
	// to the transaction list
	public void addTransactionToList(Transaction tr) {
		if (tr != null) {
			transactions.add(tr);
			queue.add(tr.getAmount());
		}
	}

	// Adds the value of amount
	// to the total
	public void incrementTransactionSumValue(double amount) {
		transactionsSum += amount;
	}

	// Subtracts an amount
	// From the total
	public void subsFromSum(double amount) {
		transactionsSum -= amount;
	}

	public static double getTransactionsSum() {
		return transactionsSum;
	}

}
