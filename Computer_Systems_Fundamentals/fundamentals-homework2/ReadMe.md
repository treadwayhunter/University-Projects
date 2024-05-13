# Homework 2
* Assumptions made in homework
* Run Instructions 

## Assumptions made in homework
### Problem 2
* Part A
    - Calculated between 0 and 99 inclusive

### Problem 4
* No Leap Years
    - All years have 365 days
* Exactly 24 hours in a day
    - 8760 hours in one year
    - 175200 hours in 20 years
* When generating a queue with 20 years of server operation:
    - I generate a random time until failure (Exponential Distribution with about 1 failure every 500 hours)
    - If queue iteration is just less than 175200 hours, I allow one more iteration of a failure and recovery, so the final result ends up being over 175200 hours.
    

## Run Instructions
* Problem 1 is a pdf.
* I compiled all programs using the g++ compiler in Linux.
* In the directory you're in, just run "g++ h2p2.cpp -o p2", "g++ h2p3.cpp -o p3", "g++ h2p4.cpp -o p4", then run the command "./p2" through p4 respectively to execute each program.
* Each program will create a txt file, for example p2 will generate "h2p2.txt" showing the output of the program.
