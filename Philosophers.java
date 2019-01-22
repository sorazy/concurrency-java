// Natasha Zdravkovic
// Parallel Processing

import java.io.*;
import java.util.*;

class Chopsticks implements Runnable
{
	private int left;
	private int right;

	Philosopher(int left, int right)
	{
		this.left = left;
		this.right = right;
	}

	public void run()
	{
		try
		{
			while(true)
			{
				System.out.println(Thread.currentThread().getID() + "is now thinking.");

				synchronized(left)
				{
					System.out.println(Thread.currentThread().getID() + "is now hungry.");

					synchronized(right)
					{
						System.out.println(Thread.currentThread().getID() + "is now eating.");
					}
				}
				System.out.println(Thread.currentThread().getID() + "is now thinking.");
			}
		}
		catch(InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}
}

public class Philosophers
{
	public static void main(String[] args)
	{
		Philosopher[] table = new Philosopher[5];

		Thread[] t = new Thread[5];

		for (int i = 0; i < 5; i++)
		{
			t[i] = new Thread(table[i], i + 1);
			t[i].start();
		}

		// wait for threads to finish
		for (int i = 0; i < 5; i++)
			t[i].join();
	}
}
