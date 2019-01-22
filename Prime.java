// Soliman Alnaizy - so365993
// Parallel Processing spring 2019

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.*;

public class Prime extends Thread
{
	public static final int THREADS = 8, MAX_PRIMES = (int) 1e8;
	public static AtomicInteger counter = new AtomicInteger(2);
	public static long ti, tf;
	public static boolean [] primeList;

	// Starts the thread
	public synchronized void run()
	{
		checkPrimes();
	}

	// Takes the boolean array and changes all the composite (non-prime) indices to true
	// Using the "Sieve of Eratosthenes" method
	public void checkPrimes()
	{
		int current;
		int sqrtn = (int) Math.sqrt(primeList.length);

		while (counter.intValue() < sqrtn)
		{
			current = counter.getAndIncrement();

			if (primeList[current])
				continue;
			else
				for (int i = 2 * current; i < primeList.length; i += current)
					primeList[i] = true;

		}
	}

	// Prints all the relavent data to prime.txt
	public static void print() throws Exception
	{
		long sum = 0;
		int nPrimes = 0;

		// Count the index if it's false
		for (int i = 0; i < primeList.length; i++)
		{
			if (!primeList[i])
			{
				nPrimes++;
				sum += i;
			}
		}

		PrintWriter print = new PrintWriter(new FileWriter("primes.txt"));
		print.print("Execution time:\t" + (tf - ti) + "ms\n");
		print.print("Num of primes:\t" + nPrimes + "\n");
		print.print("Sum of primes:\t" + sum + "\n");
		print.print("Last 10 primes:\t");

		// Print the last 10 prime numbers starting from the end of the Prime List
		int lastTen = 0, i = primeList.length - 1;

		// Printing since the array starts at 0
		while (lastTen < 10 && i > 0)
		{
			if (!primeList[i--])
			{
				lastTen++;
				print.print((i + 1) + ((lastTen == 10) ? "." : ", "));
			}
		}

		print.close();
	}

	public static void main(String [] args) throws Exception
	{
		// To make sure that the primeList array has atleast 2 numbers in it
		primeList = new boolean[Math.max(2, MAX_PRIMES + 1)];
		Thread [] t = new Thread[THREADS];

		// 0 and 1 are not considered primes. We'll add those manually
		primeList[0] = true;
		primeList[1] = true;

		// Initial time
		ti = System.currentTimeMillis();

		for (int i = 0; i < THREADS; i++)
		{
			t[i] = new Thread(new Prime());
			t[i].start();
		}

		// Wait for threads to finish
		for (int i = 0; i < THREADS; i++)
			t[i].join();

		// Final time
		tf = System.currentTimeMillis();

		print();

		return;
	}
}
