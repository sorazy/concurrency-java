Name    : Soliman Alnaizy
NID     : so365993
Semester: Spring 2019
Course  : COP 4520

Objective:  To find all the prime numbers between the numbers 1-n using 8 seperate threads.

Execution:  Using the JVM, compile "Prime.java" on a terminal using the "javac" command.
            Then run the created "Prime.class" file on a terminal using the "java" command.
            Once the program is finished executing, a "primes.txt" file will be created in
            the local directory.

Efficiency: The program implements the sieve of Erotosthenes algorithm to find all the primes.
            After some imperical analysis (see table below), it's hard to determine the exact
            run-time of the program, however, since the numbers are converging to 0 at O(n), we
            can say that O(n) is an upper bound of the run-time 

Experimental
Evaluation: +-------+----------------+--------+------------------+
            |   n   |     O(n)       |  Time  |  (T/O(n))*10^-5  |
            +-------+----------------+--------+------------------+
            |  1e5  |  100,000       |  ~7ms  |       7.00       |
            +-------+----------------+--------+------------------+
            |  1e6  |  1,000,000     | ~48ms  |       4.8        |
            +-------+----------------+--------+------------------+
            |  1e7  |  10,000,000    | ~214ms |       2.14       |
            +-------+----------------+--------+------------------+
            |  1e8  |  100,000,000   |~1184ms |       1.18       |
            +-------+----------------+--------+------------------+

            The program also ran roughly about 1.4x faster with 8 threads than with a single thread.