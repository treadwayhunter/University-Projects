# Homework 4

## Compilation
I used the G++ compiler for compilation. If you need to recompile:
* g++ h4p1.cpp -o p1

## Execution
The program can be run by issuing the command
* ./p1

The program will ask you to input a lambda and service time.
In-depth error handling was not added:
* ensure lambda is an integer between 10 and 30.
* ensure service time is a floating point value.

## Assumptions made
#### Averages for Turnaround Time
* Each individual process' turn around time was calculated, then added to a vector.
* These values in the vector were summed, and then divided by the size of the vector (which is 10000).
* This was always within 3 sigfigs of Tq = Tw + Ts, and usually within 2 sigfigs of Tq = q/lambda


