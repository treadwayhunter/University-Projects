#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>
#include <pthread.h>
#include "deck.h"

#define NUM_PLAYERS 6

pthread_t players[NUM_PLAYERS];
pthread_mutex_t safePrintMutex;
pthread_mutex_t gameInitMutex;
pthread_mutex_t playerCardsMutex;
pthread_mutex_t checkAllReadyMutex;
pthread_mutex_t roundMutex;

pthread_cond_t dealerWaitingCond;
pthread_cond_t playerTurn[NUM_PLAYERS];
pthread_cond_t roundFinishedCond;
pthread_cond_t playerWaitingCond;

Deck deck = {0, NULL};
Deck playerHands[NUM_PLAYERS]; // each player's hand is a Deck of 2 cards
Card *targetCard;

int dealer = 0;
int currentPlayer = 1;
bool playerReady[NUM_PLAYERS];
bool playerCanPlay[NUM_PLAYERS];
bool cardsDealt = false;
bool roundReady = false;
bool roundFinished = false;

void safePrint(long pid, char* string, pthread_mutex_t *safePrintMutex) {
    pthread_mutex_lock(safePrintMutex);
    printf("%s: %ld\n", string, pid);
    pthread_mutex_unlock(safePrintMutex);
}

void returnRandCard(Deck *deck, Deck *playerHand, Card *card1, Card *card2, long pid) {
    float r = (float)rand() / RAND_MAX;
    if (r > 0.5) {
        insertCard(deck, card1); // place card 1 in the deck
        insertCard(playerHand, card2); // place card 2 back in the player's hand
    }
    else {
        insertCard(deck, card2); // place card 2 in the deck
        insertCard(playerHand, card1); // place card 1 back in the player's hand
    }
}

void dealCards(int dealer, Deck *deck, Deck *playerHands) {
    int dealToPlayer = (dealer + 1) % NUM_PLAYERS;
    for(dealToPlayer; dealToPlayer != dealer; dealToPlayer = (dealToPlayer + 1) % NUM_PLAYERS) {
        Card *card1 = drawCard(deck);
        insertCard(&playerHands[dealToPlayer], card1);
    }
}

bool checkAllReady(long pid, bool *playerReady) {
    bool flag = true;
    for (int i = 0; i < NUM_PLAYERS; i++) {
        if (!playerReady[i]) {
            //printf("Player %d not ready!: %ld\n", i, pid);
            flag = false;
        }
    }
    return flag;
}

void *player(void *id) {
    long pid = (long)id;
    
    if(pid == dealer) {
        //safePrint(pid, "I am dealer", &safePrintMutex);
    }
    else {
        //safePrint(pid, "I am player", &safePrintMutex);
    }

    // game initialization
    if(pid == dealer) {
        pthread_mutex_lock(&checkAllReadyMutex);
        playerReady[pid] = true;
        while(!checkAllReady(pid, playerReady)) {
            // wait for all players to become ready.
            safePrint(pid, "Dealer waiting for players to be ready", &safePrintMutex);
            pthread_cond_wait(&dealerWaitingCond, &checkAllReadyMutex);
        }
        safePrint(pid, "Dealer was signalled", &safePrintMutex);
        shuffleDeck(&deck);
        targetCard = drawCard(&deck);
        dealCards(dealer, &deck, playerHands);
        pthread_mutex_unlock(&checkAllReadyMutex);
    }
    else {
        pthread_mutex_lock(&checkAllReadyMutex);
        playerReady[pid] = true;
        if(checkAllReady(pid, playerReady)) {
            // signal dealer
            pthread_cond_signal(&dealerWaitingCond);
        }
        pthread_mutex_unlock(&checkAllReadyMutex);
    }


    // play the round
    
    if (pid == dealer) {
        pthread_mutex_lock(&roundMutex);
        safePrint(pid, "Dealer waits for the round to finish", &safePrintMutex);
        currentPlayer = (dealer + 1) % NUM_PLAYERS;
        roundReady = true;

        printf("---Target Card == %d\n", targetCard->value);
        
        pthread_cond_signal(&playerTurn[currentPlayer]); // player 1 cant go until the dealer is ready to wait for the round to end. At this point, the cards have been dealt

        //while(!roundFinished) {
        //    pthread_cond_wait(&roundFinishedCond, &roundMutex);
        //}
        pthread_mutex_unlock(&roundMutex);
    }
    else {
        safePrint(pid, "I have arrived at the round", &safePrintMutex);
        pthread_mutex_lock(&roundMutex);
        //while(!roundFinished) {
            while(pid != currentPlayer || !roundReady) {
                safePrint(pid, "I am waiting for my turn", &safePrintMutex);
                pthread_cond_wait(&playerTurn[pid], &roundMutex); // if the pid isn't the current player, or the round hasn't started, then wait
            }
            if(!roundFinished) {
                safePrint(pid, "My turn", &safePrintMutex);

                // player draws a card from the deck
                Card *card = drawCard(&deck);
                insertCard(&playerHands[pid], card);

                // player looks at the cards in their hands
                Card *card1 = drawCard(&playerHands[pid]);
                Card *card2 = drawCard(&playerHands[pid]);

                // player compares the cards in their hands with the target
                if (card1->value == targetCard->value || card2->value == targetCard->value) {
                    safePrint(pid, "Player wins!!!", &safePrintMutex);
                    roundFinished = true;
                }
                else {
                    returnRandCard(&deck, &playerHands[pid], card1, card2, pid);
                }
            }
            else {
                safePrint(pid, "Round finished before my turn", &safePrintMutex);
            }
            
            currentPlayer = (currentPlayer + 1) % NUM_PLAYERS; // set next player
            if (currentPlayer == dealer) {
                currentPlayer = (currentPlayer + 1) % NUM_PLAYERS; // skip dealer
            }

            pthread_cond_signal(&playerTurn[currentPlayer]);
            playerCanPlay[pid] = false;
        //}

        pthread_mutex_unlock(&roundMutex);
    }
}

int main() {
    srand((unsigned int)time(NULL));    

    pthread_mutex_init(&safePrintMutex, NULL);
    pthread_mutex_init(&gameInitMutex, NULL);
    pthread_mutex_init(&playerCardsMutex, NULL);
    pthread_mutex_init(&roundMutex, NULL);

    pthread_cond_init(&dealerWaitingCond, NULL);
    pthread_cond_init(&roundFinishedCond, NULL);
    pthread_cond_init(&playerWaitingCond, NULL);

    for (int i = 0; i < NUM_PLAYERS; i++) {
        playerReady[i] = false; // ensure that the values are set to false
        playerCanPlay[i] = false; // ensure that the values are set to false
        pthread_cond_init(&playerTurn[i], NULL);
    }



    for (long i = 0; i < NUM_PLAYERS; i++) {
        int create = pthread_create(&players[i], NULL, player, (void *)i);
        if (create != 0) {
            printf("Fatal error: thread %ld was not created.\n", i);
            return -1;
        }
    }

    for (int i = 0; i < NUM_PLAYERS; i++) {
        int join = pthread_join(players[i], NULL);
        if (join != 0) printf("Error joining");
    }
}