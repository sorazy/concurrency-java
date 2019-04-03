// A simple test case class I created to test the SuperAwesomeAtomicStack class
// ... it's just a frequency array to make sure that each number is being popped once.

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Test extends Thread
{
	public static SuperAwesomeAtomicStack<Integer> s = new SuperAwesomeAtomicStack<>();
	public static AtomicInteger counter = new AtomicInteger(0);
	public static final int CALLS = 10000, THREADS = 30;

	public static int [] array = new int[CALLS];

	public void run()
	{
		AtomicInteger k = new AtomicInteger(0);
		for (int i = counter.getAndIncrement(); i < CALLS; i = counter.getAndIncrement())
		{
			// 50% Chance to push, 50% chance to pop
			if (Math.random() >= 0.50)
				s.push(i);
			else
				s.pop();
		}
	}

	// Same test above but sequestial
	public static void seq_run()
	{
		for (int i = 0; i < CALLS; i++)
		{
			if (Math.random() >= 0.50)
				s.push(i);
			else
				s.pop();
		}
	}

	public static void main(String [] args) throws Exception
	{
		Thread [] t = new Thread[THREADS];

		// To calculate the average
		long total = 0;
		for (int j = 0; j < 1000; j++)
		{
			long start = System.nanoTime();
			for (int i = 0; i < THREADS; i++)
			{
				t[i] = new Thread(new Test());
				t[i].start();
			}

			for (int i = 0; i < THREADS; i++)
				t[i].join();

			long end = System.nanoTime();
			total += end - start;

			// Reset everything for next round
			s = new SuperAwesomeAtomicStack<>();
			counter = new AtomicInteger(0);
			array = new int[CALLS];
		}
		
		// Outputs the time
		System.out.println("Average time to for " + THREADS + " threads to execute 10,000 calls: " + total / 1000);
		return;
	}
}