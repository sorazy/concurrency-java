// A simple test case class I created to test the SuperAwesomeAtomicStack class
// ... it's just a frequency array to make sure that each number is being popped once.

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Test extends Thread
{
	public static Stack<Integer> s = new Stack<>();
	public static AtomicInteger counter = new AtomicInteger(0);
	public static int [] array = new int[1000];

	public void run()
	{
		for (int temp = counter.getAndIncrement(); temp < 1000; temp = counter.getAndIncrement())
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
		Thread [] t = new Thread[5];

		for (int i = 0; i < 5; i++)
		{
			t[i] = new Thread(new Test());
			t[i].start();
		}

		for (int i = 0; i < 5; i++)
			t[i].join();

		for (int i = 0; i < array.length; i++)
		{
			System.out.print(array[i] + ", ");

			if (i % 25 == 0 && i != 0)
				System.out.println();
		}

		System.out.println();

		return;
	}
}