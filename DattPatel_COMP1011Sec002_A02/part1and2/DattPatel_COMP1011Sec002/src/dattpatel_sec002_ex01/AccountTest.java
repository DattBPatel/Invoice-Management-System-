package dattpatel_sec002_ex01;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// AccountTest class to test multiple transactions
public class AccountTest {
    public static void main(String[] args) {
        // Create an account with initial balance
        Account account = new Account(500);

        // Create a list of Transaction objects
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(account, "deposit", 123));
        transactions.add(new Transaction(account, "withdraw", 321));
        transactions.add(new Transaction(account, "deposit", 439));
        transactions.add(new Transaction(account, "withdraw", 320));

        // Create a thread pool
        ExecutorService executor = Executors.newCachedThreadPool();

        // Execute the threads
        for (Transaction transaction : transactions) {
            executor.execute(transaction);
        }

        // Shutdown the executor
        executor.shutdown();

        // Wait for all threads to finish
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}