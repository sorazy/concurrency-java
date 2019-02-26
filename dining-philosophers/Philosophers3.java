// Soliman Alnaizy - so365993
// Parallel Processing - Dijkstra's Philosophers3 Problem - Spring 2019
// VERSION 3 -- Deadlock and starvation free.

import java.io.*;
import java.util.*;

class Chopstick
{
	boolean isUsed = false;
	int id;

	public Chopstick(int id)
	{
		this.id = id;
	}

	public synchronized boolean checkAndInvert()
	{
		if (this.isUsed)
			return false;
		else
			return this.isUsed = true;
	}

	public synchronized void raiseChopstick()
	{
		this.isUsed = true;
	}

	public synchronized void lowerChopstick()
	{
		this.isUsed = false;
	}

	public synchronized boolean isUsed()
	{
		return this.isUsed;
	}
}

public class Philosophers3 extends Thread
{
	public static int NUM_CHOPSTICKS;
	public static boolean running = true;
	public static Chopstick [] chopsticks;
	public static ArrayList<Thread> q = new ArrayList<>();
	int left, right, id;
	boolean isEating;

	Philosophers3(int left, int right, int id)
	{
		this.left = left;
		this.right = right;
		this.id = id;
	}

	public static void terminate()
	{
		running = false;
	}

	public void run()
	{
		try 
		{
			while (running)
			{
				System.out.println("Philosopher #" + this.id + " is thinking.");

				this.isEating = true;

				// This loop is to prevent "Philosopher #X is thinking." repeadetly
				while (running && this.isEating)
				{
					// Check if their turn by comparing with the next object in the queue
					if (Thread.currentThread() == q.get(0))
					{
						q.remove(0);

						// Check if the left is available. If it's not, then wait until it is.
						// sychronized to avoid multiple philosophers reaching for the same chopstick
						synchronized (chopsticks[this.left])
						{
							if (chopsticks[this.left].checkAndInvert())
							{
								// Next, check if the right is available.
								synchronized (chopsticks[this.right])
								{
									if (chopsticks[this.right].checkAndInvert())
									{
										// Thread sleeps for half a second
										System.out.println("Philosopher #" + this.id + " is now eating.");
										this.sleep((int) (500));

										chopsticks[this.right].lowerChopstick();
									}
									else
									{
										// If the right is in use, then lower the left one. No point in holding on to it.
										chopsticks[this.left].lowerChopstick();

										// Add it to the back of the queue
										q.add(Thread.currentThread());
										continue;
									}
								}

								chopsticks[this.left].lowerChopstick();
								System.out.println("Philosopher #" + this.id + " is done eating and is back to thinking.");

								this.isEating = false;
							}
							else
							{
								// Add it to the back of the queue
								q.add(Thread.currentThread());
								continue;
							}
						}
					}	
					else
					{
						continue;
					}				
				}

				// Add it to the back of the queue
				q.add(Thread.currentThread());
			}
		} 
		catch (Exception e)
		{

		}
	}

	public static void main(String[] args) throws Exception
	{
		// Take in number of chopsticks from the command line
		NUM_CHOPSTICKS = 5;

		// We're going to need some stuff here. Each Thread represents a philospher
		Thread [] philosphers = new Thread[NUM_CHOPSTICKS];
		chopsticks = new Chopstick[NUM_CHOPSTICKS];

		// Initialize chopsticks
		for (int i = 0; i < NUM_CHOPSTICKS; i++)
			chopsticks[i] = new Chopstick(i);

		// Start the threads. Each philosopher get's 2 chopsticks that have a number 
		// from 1-5 (It's in a circle, hence the mod). Each thread has an id of (i + 1)
		for (int i = 0; i < NUM_CHOPSTICKS; i++)
		{
			// Invert the left and right for one of them to prevent deadlock
			if (i < 4)
				philosphers[i] = new Thread(new Philosophers3(i % 5, (i + 1) % 5, i + 1));
			else
				philosphers[i] = new Thread(new Philosophers3((i + 1) % 5, i % 5, i));
			
			q.add(philosphers[i]);
			philosphers[i].start();

		}

		Scanner in = new Scanner(System.in);
		String line = "";

		// Keep going until an "n" is entered
		while (!line.equalsIgnoreCase("n"))
   			line = in.next();
	
		// Once we reach this line of code, terminate all the threads
		Philosophers3.terminate();

		// Join all threads as soon as they're done
		for (int i = 0; i < NUM_CHOPSTICKS; i++)
			philosphers[i].join();
	}
}
