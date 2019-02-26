// Soliman Alnaizy - so365993
// Parallel Processing - Dijkstra's Philosophers1 Problem - Spring 2019
// VERSION 1

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
		{
			return false;
		}
		else
		{
			this.isUsed = true;
			return true;
		}
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

public class Philosophers1 extends Thread
{
	public static int NUM_CHOPSTICKS;
	public static boolean running = true;
	public static Chopstick [] chopsticks;
	int left, right, id;

	Philosophers1(int left, int right, int id)
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
				System.out.println(id + " philosopher is thinking.");

				if (chopsticks[this.left].checkAndInvert())
				{
					System.out.println(id + " philosopher is hungry.");

					if (chopsticks[this.right].checkAndInvert())
					{
						System.out.println(id + " philosopher is eating");
						Thread.sleep((int) (Math.random() * 5));

						chopsticks[this.right].lowerChopstick();
					}

					chopsticks[this.left].lowerChopstick();
				}

				System.out.println(id + " philosopher is done eating and is back to thinking.");
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

		// Start the threads. Each philosopher get's 2 chopsticks that have a number 
		// from 1-5 (It's in a circle, hence the mod). Each thread has an id of (i + 1)
		for (int i = 0; i < NUM_CHOPSTICKS; i++)
		{
			philosphers[i] = new Thread(new Philosophers1(i % 5, (i + 1) % 5, i));
			philosphers[i].start();
		}

		Scanner in = new Scanner(System.in);
		String line = "";

		// Keep going until an "n" is entered
		while (!line.equalsIgnoreCase("n"))
   			line = in.next();
	
		// Once we reach this line of code, terminate all the threads
		Philosophers1.terminate();

		// Join all threads as soon as they're done
		for (int i = 0; i < NUM_CHOPSTICKS; i++)
			philosphers[i].join();
	}
}
