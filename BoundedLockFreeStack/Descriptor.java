// Soliman Alnaizy - 03/02/2019
// Concurrent stack that supports a "size" field

// A typical node class that will be used in a stack
class Node<T>
{
	T value;
	Node<T> next;

	Node (T val)
	{
		this.value = val;
	}
}

// A descriptor that will allow us to create a concurrent stack
class Descriptor<T>
{
	Node<T> head;
	int size;

	Descriptor(Node<T> head, int size)
	{
		this.head = head;
		this.size = size;
	}

	Descriptor(T data, int size)
	{
		this.head = new Node<T>(data);
		this.size = size;
	}

}