// A simple test case class I created to test the SuperAwesomeAtomicStack class
// ... it's just a frequency array to make sure that each number is being popped once.

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Test extends Thread
{
	public static SuperAwesomeAtomicStack<Integer> s = new SuperAwesomeAtomicStack<>();
	public static AtomicInteger counter = new AtomicInteger(0);
	public static final int CALLS = 10000, THREADS = 5;

	public static int [] array = new int[CALLS];

	public void run()
	{
		for (int temp = counter.getAndIncrement(); temp < CALLS; temp = counter.getAndIncrement())
			s.push(temp);

		Integer temp = s.pop();
		while (temp != null)
		{
			array[temp]++;
			temp = s.pop();
		}
	}

	public static void run(int a)
	{
		for (int temp = counter.getAndIncrement(); temp < CALLS; temp = counter.getAndIncrement())
			s.push(temp);

		Integer temp = s.pop();
		while (temp != null)
		{
			array[temp]++;
			temp = s.pop();
		}
	}

	public static void main(String [] args) throws Exception
	{
		Thread [] t = new Thread[THREADS];

		long total = 0;
		for (int j = 0; j < 10; j++)
		{
			long start = System.nanoTime();
			for (int i = 0; i < THREADS; i++)
			{
				t[i] = new Thread(new Test());
				t[i].start();
			}

			for (int i = 0; i < THREADS; i++)
				t[i].join();
			// run(0);
			long end = System.nanoTime();

			s = new SuperAwesomeAtomicStack<>();
			counter = new AtomicInteger(0);
			array = new int[CALLS];

			total += end - start;
		}
		
		System.out.println("Average time to execute 10,000 calls: " + total / 1000);
		// int i = 0;
		// while (i < array.length)
		// 	System.out.printf("%d%s", array[i++], (i % 25 == 0) ? "\n" : ", ");

		return;
	}
}