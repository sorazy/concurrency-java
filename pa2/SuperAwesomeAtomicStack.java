// Soliman Alnaizy - so365993
// COP4520 - Homework Assignment1

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

class Node<Anytype>
{
	T value;
	Node<Anytype> next;

	Node (Anytype val)
	{
		this.value = val;
	}
}

public class SuperAwesomeAtomicStack<Anytype>
{
	AtomicReference<Node<Anytype>> head;
	AtomicInteger numOps = new AtomicInteger(0);

	// Constructor. Begin with a "null" node
	public SuperAwesomeAtomicStack()
	{
		head = new AtomicReference<>(null);
	}

	// A typical LIFO push method
	public void push(Anytype value)
	{
		Node<Anytype> theNewHead = new Node<Anytype>(value);

		theNewHead.next = head.get();

		// Keep waiting until we see the head that we expected at the beginning
		while(!head.compareAndSet(theNewHead.next, theNewHead))
			theNewHead.next = head.get();

		numOps.getAndIncrement();
	}

	// A typical LIFO pop method
	public T pop()
	{
		Node<Anytype> tempHead = head.get();

		// Empty lists return null
		if (head.get() == null)
			return null;

		// Keep waiting until we see the head that we expected at the beginning
		while(!head.compareAndSet(tempHead, tempHead.next))
		{
			tempHead = head.get();

			// To avoid NullPointerExceptions
			if (head.get() == null)
				return null;
		}

		numOps.getAndIncrement();

		return tempHead.value;
	}

	// For some reason the number of operations is relavent?
	public int getNumOps()
	{
		return numOps.get();
	}
}
