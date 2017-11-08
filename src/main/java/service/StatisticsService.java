package service;

import java.util.List;

import org.springframework.stereotype.Service;

import model.Statistic;
import model.Transaction;

@Service
public class StatisticsService {

	/*
	 * Prepares the statistics JSON result. 
	 * The sum value is calculated with get method ( O(1) ). 
	 * The Max and Min values are calculated each time iterating
	 * through the transactions list O(n).
	 *  Access to transactions list is done
	 * in thread-safe mode using synchronizedList
	 */
	public Statistic getStatistics() {
		double sum = 0.0;
		double avg = Double.NaN;
		double max = Double.NaN;
		double min = Double.NaN;
		long count = 0;
		
		List<Transaction> transactions = TransactionService.transactions;
		if (!transactions.isEmpty()) {

			synchronized (transactions) {
				max = transactions.get(0).getAmount();
				min = transactions.get(0).getAmount();

				for (Transaction tr : transactions) {
					max = tr.getAmount() > max ? tr.getAmount() : max;
					min = tr.getAmount() < min ? tr.getAmount() : min;
				}
				
				sum   = TransactionService.getTransactionsSum();
				count = transactions.size();
				avg   = sum / transactions.size();

			}

		}
		return new Statistic(sum, avg, max, min, count);
	}

}
