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

	public SuperAwesomeAtomicStack()
	{
		this.desc = new AtomicReference<>(new Descriptor<>(null, 0));
		this.nOps = new AtomicInteger(0);
	}

	// A typical LIFO push method
	public void push(T value)
	{
		// Get the current descriptor, and make a new descriptor that will take it's place
		Descriptor<T> currDesc = this.desc.get();
		Descriptor<T> newDesc = new Descriptor<>(value, desc.get().size + 1);

		newDesc.head.next = desc.get().head;

		// Keep waiting until we see the head that we expected at the beginning
		// If different head, that means some other thread changed it. Try again ¯\_(ツ)_/¯
		while(!desc.compareAndSet(currDesc, newDesc))
		{
			currDesc = this.desc.get();
			newDesc.size = currDesc.size + 1;
			newDesc.head.next = currDesc.head;

			nOps.getAndIncrement();
		}
	}

	// A typical LIFO pop method
	public T pop()
	{
		Descriptor<T> currDesc = this.desc.get();

		// If the retrieved descriptor has a size of 0, then stack is empty. Return null.
		if (desc.get().size == 0)
			return null;

		Descriptor<T> newDesc = new Descriptor<>(currDesc.head.next, currDesc.size - 1);

		// Keep waiting until we see the head that we expected at the beginning
		while(!desc.compareAndSet(currDesc, newDesc))
		{
			// If different descriptor, then try again ¯\_(ツ)_/¯
			currDesc = this.desc.get();

			// To avoid NullPointerExceptions. If size is 0, then empty stack.
			if (currDesc.size == 0)
				return null;

			// Make a new descriptor with updated values
			newDesc = new Descriptor<>(currDesc.head.next, currDesc.size - 1);

			nOps.getAndIncrement();
		}

		return currDesc.head.value;
	}


	// Made this solely for testing purposes
	public int size()
	{
		return desc.get().size;
	}

	// For some reason the number of operations is relavent?
	public int getNumOps()
	{
		return nOps.get();
	}
}
