import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Thread
{
    public static EBOStack<Integer> s = new EBOStack<>();
    public static AtomicInteger counter = new AtomicInteger(0);
    public static final int CALLS = 10000, THREADS = 5;
    public static int [] pusharray = new int[CALLS];
    public static int [] poparray = new int[CALLS];

    public void run()
    {
        for (int i = counter.getAndIncrement(); i < CALLS; i = counter.getAndIncrement())
		{
            double r = Math.random();
            if (r >= 0.50)
            {
                s.push(i);
            }
            else try
            {
                s.pop();
            }
            catch(Exception e)
            {
                ;
            }
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
				t[i] = new Thread(new Main());
				t[i].start();
			}

			for (int i = 0; i < THREADS; i++)
				t[i].join();

			long end = System.nanoTime();
			total += end - start;

			// Reset everything for next round
			s = new EBOStack<>();
			counter = new AtomicInteger(0);
        }
        
        System.out.println("Average time to for " + THREADS + " threads to execute " + CALLS + " calls: " + (total / 1000) + "ns");
        return;
    }
}