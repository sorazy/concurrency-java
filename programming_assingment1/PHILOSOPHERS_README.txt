Name    : Soliman Alnaizy
NID     : so365993
Semester: Spring 2019
Course  : COP 4520

Objective:  To find a concurrent solution to Dijkstra's Dining Philosopher's problem.
            There are 4 versions of this assignment. Each is descripted in the assignment's pdf

Execution:  Using the JVM, compile "PhilosophersX.java" on a terminal using the "javac" command.
            Then run the created "PhilosophersX.class" file on a terminal using the "java" command.
            Example of what to type in a Linux commandline:
                        javac Philosophers1.java
                        java Philosophers1

	             ** To exit the program, you must press "n" AND the enter key **

            VERSION 1:
                  The philosophers are instructed to always pick the left chopstick first. Then
                  to keep holding it until the right one is available. The problem with this
                  approach is that the program hits deadlock almost instantly. Version 2 takes care
                  of this problem.

            VERSION 2:
                  All the philosphers are instructed to pick up the left one first, except for one.
                  The last one is instructed to pick the right one up first. This guarantees at
                  least one of the philosopher will eat. Once that philosopher is done, it gives
                  the chance for other philosophers to pick up the chopstick. This is definitely an
                  imporvement from the first version, whoever it doesn't protect threads from
                  starvattion, as one thread could potentially keep eating indefinitely. Version 3
                  deals with this issue.

            VERSION 3:
                  This version build's upon version 2 by implementing a queue to make sure that
                  no one philosopher is hogging the chopsticks at any given time. As soon as a
                  philosopher is done eating, they're sent to the end of the queue and have to wait
                  their turn.

            Version 4:
                  Basically the exact same as version 3 except you can pass the number of
                  philosophers you would like to to have on your table as a commandline argument.
                  Which removes the need for any sort of magic numbers in the code.

Efficiency: This code is very efficient. The best. No other code is more efficient than mine.
            Believe me.