// Soliman Alnaizy - so365993
// COP4520 - Homework Assignment2

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

// Everytime we call push() or pop(), we get replace the old descriptor with a new one that
// contains the updated values of head and size
public class SuperAwesomeAtomicStack<T>
{
	AtomicReference<Descriptor<T>> desc;
	AtomicInteger nOps;
	AtomicInteger nPushes;
	public static final int CAPACITY = 10000;

	public SuperAwesomeAtomicStack()
	{
		this.desc = new AtomicReference<>(new Descriptor<>(null, 0));
		this.nOps = new AtomicInteger(0);
		this.nPushes = new AtomicInteger(0);
	}

	// It first compares the size and checks if it's less than the capacity.
	// If the current size is over capacity, then throw an excpetion. This is to avoid
	// deadlocking in the case where all threads are pushing an object to a full stack.
	// The same logic applies when size is less than 0.
	public boolean RDCSSPush(Descriptor<T> currDesc, Descriptor<T> newDesc)
	{
		if (currDesc.size >= CAPACITY)
			return true;
		else
			return desc.compareAndSet(currDesc, newDesc);
	}

	// Similar logic to RDCSSPush()
	public boolean RDCSSPop(Descriptor<T> currDesc, Descriptor<T> newDesc)
	{
		if (currDesc.size <= 0)
			return true;
		else
			return desc.compareAndSet(currDesc, newDesc);
	}

	// Keep waiting until we see the head that we expected at the beginning
	// If different head, that means some other thread changed it. Try again ¯\_(ツ)_/¯	
	public void push(T value)
	{
		Descriptor<T> currDesc, newDesc;
		
		do {
			currDesc = this.desc.get();
			newDesc  = new Descriptor<>(value, currDesc.size + 1);
			newDesc.head.next = currDesc.head;

			nOps.getAndIncrement();
		} while(!RDCSSPush(currDesc, newDesc));
	}

	// Keep waiting until we see the head that we're expecting	
	// If different descriptor, then try again ¯\_(ツ)_/¯
	public T pop()
	{
		Descriptor<T> currDesc, newDesc;

		do {
			currDesc = this.desc.get();
			if (currDesc.size <= 0)
				return null;

			newDesc = new Descriptor<>(currDesc.head.next, currDesc.size - 1);

			nOps.getAndIncrement();
		} while(!RDCSSPop(currDesc, newDesc));
		
		return currDesc.head.value;
	}

	// Returns the current size of the stack
	public int size()
	{
		return desc.get().size;
	}

	public int getNumOps()
	{
		return nOps.get();
	}
}