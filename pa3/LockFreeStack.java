import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class LockFreeStack<T>
{
    AtomicReference<Node<T>> top = new AtomicReference<>(null);
    static final int MIN_DELAY = 10;
    static final int MAX_DELAY = 10000;
    Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);

    protected boolean tryPush(Node node)
    {
        Node oldTop = top.get();
        node.next = oldTop;
        return top.compareAndSet(oldTop, node);
    }

    public void push(T value) throws Exception
    {
        Node node = new Node(value);
        while (true)
        {
            if (tryPush(node)) return;
            else               backoff.backoff();
        }
    }

    protected Node tryPop() throws Exception
    {
        Node oldTop = top.get();
        if (oldTop == null)                              throw new EmptyStackException();
        else if (top.compareAndSet(oldTop, oldTop.next)) return oldTop;
        else                                             return null;
    }

    public T pop() throws Exception
    {
        while(true)
        {
            Node<T> returnNode = tryPop();
            
            if (returnNode != null) return returnNode.data;
            else                    backoff.backoff();
        }
    }
}