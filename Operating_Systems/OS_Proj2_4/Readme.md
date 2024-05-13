# Report
To compile and run:\
gcc main.c -o main -l pthread
./main

## Results
The result of 5 independent runs are listed in gamelog 1-5 respectively. The game is seeded with the current time, so each run of the game had a different seed.

## Design and Implementation
Files:
* main.c --> contains the main implementation of the game
* deck.h --> maintains the definition of Deck and Card, and all functions related to them.
* players.h --> helper functions to try and keep main.c clutter free.

The Deck is maintained as a Linked List, with each Card as a node. The head of the Deck struct is the top card.\
Each player's hand happens to be a "Deck" of 2 cards.

The player and dealer are maintained as separate functions. When rounds() is called, a thread is created for the dealer with dealerThread(), and each player is given a thread playerThread(). The dealer and player share some mutexes, ensuring certain critical sections are not written/read at the same time. Once the game starts, the dealer signals the first player and then waits for the players to finish. Each player takes their turn at the appropriate time, waiting if they arrive at their turn early. Once a player finishes their turn, they signal the next player to start their turn. This continues until a player wins. 

