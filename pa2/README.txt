Name    : Soliman Alnaizy
NID     : so365993
Semester: Spring 2019
Course  : COP 4520

Objectives:
1) I linearized the code by replacing all the locks with Atomic variables. The compareAndSet() 
   method is the point of linearization and proved to be particularly useful especially since it 
   returns a boolean object, which could very convieniently be placed in a while loop and used as a
   condition statement. The compareAndSet() method makes sure that the expected head is the same as
   the current head to make sure that none of the other threads meddled with the head during the
   variable check.

2) In Java (at least the latest versions), all atomic variables are also volatile variables.
   Therefore the variable is now stored in local memory rather than cache, and all threads have
   access to that same variable. If atomic variables weren't used, then all threads would be able
   to simultaneously get access to the stack and call the push()/pop() method multiple times on the
   same "head" variable. Which could lead to a faulty stack. For example, calling pop() multiple
   times on the same head would result in the same value being returned more than once, even though
   the value may have existed only once in the stack.
   I was able to bring the stack down to use only 1 AtomicReference, which was the head node of the
   stack. As that seemed to be the most important part of the stack that we had to make sure that
   must be accessed only once at a time.
