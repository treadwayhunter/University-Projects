#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <pthread.h>
#include <stdbool.h>
#include <unistd.h> // for testing purposes
#include "deck.h"

# define NUM_PLAYERS 6
# define START true
# define COMPLETE false
# define NOT_STARTED false
# define NOT_COMPLETE true
// Each player has a hand of 2.
// Each player can be represented in a function
pthread_t threads[NUM_PLAYERS];
pthread_mutex_t playerMutex;
pthread_cond_t playerCond;
pthread_cond_t playerReady;
pthread_cond_t roundCond;

Deck deck = {0, NULL}; // initialize deck globally so anyone can use it.
long dealer = 0; // the first player to play is always dealer + 1. Mod will need to be used
long currentPlayer = 1; // used for choosing a player
long dealToPlayer = 1;
Card *targetCard; // does this need malloc?

// Each player's hand is just a Deck of 2 cards.
Deck hands[NUM_PLAYERS];

bool roundStart = false;
bool readyPlayers[NUM_PLAYERS]; 
//bool playerWon = false;

bool allPlayersReady(long dealerID) {
    for (int i = 0; i < NUM_PLAYERS; i++) {
        if (i == dealerID) readyPlayers[i] = true;
        if (!readyPlayers[i]) return false;
    }
    return true;
}

void *player(void *id) {
    long pid = (long)id;

    pthread_mutex_lock(&playerMutex);   // I do wonder if this can cause a deadlock. What if the dealer isn't let in first?
    //printf("Player %ld has entered the mutex\n", pid);

    if (pid == dealer) { // dealer does not need to wait
        printf("I am the dealer: %ld\n", pid);
        // while players are not ready
        while (!allPlayersReady(dealer))  // should hopefully force the dealer to wait for all players to be ready before proceeding
            pthread_cond_wait(&playerReady, &playerMutex); // dealer waits for all non-dealer players to be ready
        currentPlayer = (dealer + 1) % NUM_PLAYERS; // sets the correct current player
        printf("\nDEALER NEEDS TO DEAL THE CARDS\n");
        roundStart = true;
        pthread_cond_broadcast(&roundCond);
    }
    else { // these are players
        printf("Player %ld is waiting for the dealer to start the game\n", pid);
        readyPlayers[pid] = true; // set the player to ready
        pthread_cond_broadcast(&playerReady); // signal that a player is ready
        while(roundStart == NOT_STARTED) // make player wait until the round has started
            pthread_cond_wait(&roundCond, &playerMutex); // wait for the dealer to signal the round is ready to start
    }

    if(pid == dealer) {
        printf("I AM THE DEALER: %ld\n", pid);
        while (roundStart == NOT_COMPLETE) {
            pthread_cond_wait(&roundCond, &playerMutex); // dealer waits for the game to be finished.
        }
    }

    while(roundStart == START) { // the round is started, so everyone can proceed. If it hasn't ended, just go back through the loop
        while(pid != currentPlayer) // current player was set by the dealer
            pthread_cond_wait(&playerCond, &playerMutex); // I might want a different mutex
        if (roundStart == NOT_COMPLETE) {
        // the current player is set free
            // simulate winning
            float r = (float)rand() / RAND_MAX;
            printf("Player %ld turn:: %f\n", pid, r);
            if (r > 0.9) { // simulate winning
                printf("Player %ld wins this round!\n", pid);
                roundStart = COMPLETE;
                pthread_cond_broadcast(&roundCond);
            }
        }
        currentPlayer = (currentPlayer + 1) % NUM_PLAYERS;
        if (currentPlayer == dealer) {
            currentPlayer = (currentPlayer + 1) % NUM_PLAYERS; // skip the dealer
        }
        pthread_cond_broadcast(&playerCond); 
    }
    printf("Player %ld breaks out of loop\n", pid);
    pthread_mutex_unlock(&playerMutex);
}

int main() {
    srand((unsigned int)time(NULL));
    //shuffleDeck(&deck);

    pthread_mutex_init(&playerMutex, NULL);
    pthread_cond_init(&roundCond, NULL);
    pthread_cond_init(&playerCond, NULL);
    pthread_cond_init(&playerReady, NULL);
    
    for (int i = 0; i < NUM_PLAYERS; i++) {
        readyPlayers[i] = false; // initialize ready players to false
    }

    for (long i = 0; i < NUM_PLAYERS; i++) {
        int create = pthread_create(&threads[i], NULL, player, (void *)i);
        if (create != 0) {
            printf("Fatal error: thread %ld was not created.\n", i);
            return -1;
        }
    }

    for (int i = 0; i < NUM_PLAYERS; i++) {
        int join = pthread_join(threads[i], NULL);
        if (join != 0) printf("Error joining");
    }


    pthread_cond_destroy(&roundCond);
    pthread_cond_destroy(&playerCond);
    pthread_mutex_destroy(&playerMutex);
}

/**
 * Game rules:
 * 6 players, 1 deck
 * A game is composed of 6 rounds.
 * - In each round, a player will act as the dealer. They will be responsible for shuffling the deck. 
 *  - Dealer will draw a card --> TARGET CARD
 *  - Dealer will deal one card to each player 
 *  - once complete, SIGNAL to players that the round begins
 * - In a round robin fashion, a player will draw a card, then check if either card matches the target card
 *  - if either card matches, the player wins the round
 *  - else, pick one card at random, and place it at the bottom of the deck
*/

/**
 * a player is chosen as the dealer
 *  while the other players are waiting to be chosen as the dealer
 *      play the game, in order.
 * 
 * player 1 is the dealer
 * while other players are waiting to be the dealer
 *      SIGNAL GAME START
 *      SIGNAL player 2 play
 *      
*/

/**
 * 
 * Each round, a player will act as the dealer. Everyone who is not the dealer waits for ROUND_START
 * The player that is the dealer will do their thing, then invoke ROUND_START
 *  each player must wait, unless they are the correct player
 * 
 * 
*/




    /*if (pid == dealer) {                            // the player that is the dealer does not wait, but signals that the round starts
        printf("I am the dealer: %ld\n", pid);
        shuffleDeck(&deck);

        targetCard = drawCard(&deck);
        printf("Target Card Value: %d\n\n", targetCard->value);
        currentPlayer = (dealer + 1) % NUM_PLAYERS; // make sure it's always one after the dealer
        dealToPlayer = (dealer + 1) % NUM_PLAYERS; // make sure to initialize the next dealToPlayer
        for (dealToPlayer; dealToPlayer != dealer; dealToPlayer = (dealToPlayer + 1) % NUM_PLAYERS) {
            printf("Dealing to player:: %ld\n", dealToPlayer);
            Card *card = drawCard(&deck);
            insertCard(&hands[dealToPlayer], card);
        }
        // Deal card sequence. Draw card from top of deck. Give that card to player hand.
        // be careful below. Don't deal the dealer a card, but cards are also typically dealt in order.
        printf("Dealer %ld is starting the round\n\n", pid);
        roundStart = START;
        pthread_cond_broadcast(&roundCond); // can only start the game is every is ready?
    }

    while(roundStart == NOT_STARTED) // when round start is true, break away from the loop
        printf("Player %ld is waiting for the round to start\n", pid);
        pthread_cond_wait(&roundCond, &playerMutex);     // each player waits until the round has started
    while(pid != currentPlayer && roundStart == START) {
        printf("Player %ld has entered the loop\n", pid);
        pthread_cond_wait(&playerCond, &playerMutex);
    
        if (roundStart == START) {
            Card *card = drawCard(&deck); // player draws a card, then inserts it into their hand
            insertCard(&hands[pid], card);
            printf("\nPlayer turn: %ld\n", pid);
            printDeck(&hands[pid]);

            Card *card1 = drawCard(&hands[pid]);
            Card *card2 = drawCard(&hands[pid]);

            if (card1->value == targetCard->value || card2->value == targetCard->value) { // player won the round
                printf("Player %ld wins this round!!!\n", pid);
                roundStart = COMPLETE;
                pthread_cond_broadcast(&playerCond);
            }
            else { // player did not win the round, put a card at random on the bottom of the deck
                float r = (float)rand() / RAND_MAX;
                if (r >= 0.5) {
                    insertCard(&hands[pid], card1);
                    insertCard(&deck, card2);
                }
                else {
                    insertCard(&hands[pid], card2);
                    insertCard(&deck, card1);
                }
                currentPlayer = (currentPlayer + 1) % NUM_PLAYERS;
                if (currentPlayer == dealer)
                    currentPlayer = (currentPlayer + 1) % NUM_PLAYERS; // this line skips the dealer in case the dealer was chosen
                pthread_cond_broadcast(&playerCond);
            }
        }
        else {
            printf("Player %ld didn't get to play.\n", pid);
        }
    }
    
    printf("END Player: %ld\n", pid);
    pthread_mutex_unlock(&playerMutex);*/