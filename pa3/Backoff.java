import java.util.Random;

public class Backoff
{
    public int minDelay, maxDelay;
    int limit;
    final Random r;

    Backoff(int min, int max)
    {
        limit = minDelay = min;
        maxDelay = max;
        r = new Random();        
    }

    public void backoff() throws Exception
    {
        int delay = r.nextInt(limit);
        limit = Math.min(maxDelay, 2 * limit);
        Thread.sleep(delay);
    }
}