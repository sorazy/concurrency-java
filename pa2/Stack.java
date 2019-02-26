// Natasha Zdravkovic
// HW Assignment 1

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

class Node<T>
{
	T value;
	Node<T> next;

	// constructs a new node with the inputted value
	Node (T val)
	{
		this.value = val;
	}
}

public class Stack<T>
{
	// Lock lock;
	AtomicReference<Node<T>> head;
	AtomicInteger numOps = new AtomicInteger(0);

	// constructs a new stack
	public Stack()
	{
		// lock = new ReentrantLock();

		// sets the head initially to an atomic reference of null
		// this makes sure that the head can only be accessed by one thread
		// at a time and keep the stack correct
		head = new AtomicReference<>(null);
	}

	// places a new node at the top of the stack with the given value
	public boolean push(T value)
	{
		// Lock.lock();

		Node<T> n = new Node<T>(value);

		// places the new node in front of the head and sets the
		// head "pointer" towards the newly placed node

		n.next = head.get();

		while(!head.compareAndSet(n.next, n))
		{
			n.next = head.get();
		}

		numOps.getAndIncrement();
		// Lock.unlock();

		return true;
	}

	// returns the value placed at the top of the stack and decrements the head
	public T pop()
	{
		// Lock.lock();

		Node<T> n = head.get();

		// if the stack is empty, there is nothing to pop
		if (head.get() == null)
		{
			// Lock.unlock();
			return null;
		}

		// gets the head of the list while setting the head pointer to the
		// next node
		//head.set(head.get().next);

		while(!head.compareAndSet(n, n.next))
		{
			n = head.get();

			if (head.get() == null)
				return null;

		//	head.set(n.next);
		}

		numOps.getAndIncrement();
		// Lock.unlock();

		// returns the value of the head
		return n.value;
	}

	// returns the number of operations that are done on the stack
	public int getNumOps()
	{
		return numOps.get();
	}
}
