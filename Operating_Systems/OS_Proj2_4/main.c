
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>
#include <pthread.h>
#include "deck.h"
#include "players.h"

#define NUM_PLAYERS 6
pthread_t players[NUM_PLAYERS];
pthread_mutex_t readyCheck;
pthread_mutex_t currentPlayerCheck;
pthread_mutex_t finishedCheck;
pthread_mutex_t playerFinishCheck;

pthread_cond_t playerTurn[NUM_PLAYERS];
pthread_cond_t waitAllPlayers;
pthread_cond_t waitRoundFinish;
pthread_cond_t waitAllFinished;

Deck deck;
Deck playerHands[NUM_PLAYERS]; // each player's hand is a Deck of 2.
Card *targetCard;
int dealer = 0;
int currentPlayer = 1;
bool playersReady[NUM_PLAYERS];
bool roundStart = false;
bool roundFinished = false;
bool playerFinished[NUM_PLAYERS];

FILE *file;

void *dealerThread(void *id)
{

    long pid = (long)id;
    //file = fopen("gamelog.txt", "w");

    // ROUND INIT
    // Dealer waits for all players to be ready
    pthread_mutex_lock(&readyCheck);
    playersReady[pid] = true;
    while (!allPlayersCheck(playersReady))
        pthread_cond_wait(&waitAllPlayers, &readyCheck);
    pthread_mutex_unlock(&readyCheck);

    // Dealer prepares to start the round
    pthread_mutex_lock(&currentPlayerCheck);
    shuffleDeck(&deck);           // shuffle the deck
    targetCard = drawCard(&deck); // draw the target card
    printf("Player %ld: Target Card %d\n", pid, targetCard->value);
    fprintf(file, "Player %ld: Target Card %d\n", pid, targetCard->value);
    dealCards(&deck, playerHands, dealer);      // deal cards to each of the players
    currentPlayer = (dealer + 1) % NUM_PLAYERS; // ensure the current player is the player next to the dealer
    roundStart = true;                          // start the round
    pthread_mutex_unlock(&currentPlayerCheck);
    pthread_cond_signal(&playerTurn[currentPlayer]); // signal the current player that it is their turn

    // The Round Started
    playerFinished[pid] = true; // set the dealer to finished. They have nothing else to do this round other than wait.
    pthread_mutex_lock(&finishedCheck);
    while (!roundFinished)
        pthread_cond_wait(&waitRoundFinish, &finishedCheck);

    printf("Player %ld: Round ends\n", pid);
    fprintf(file, "Player %ld: Round ends\n", pid);
    pthread_mutex_unlock(&finishedCheck);

    pthread_cond_broadcast(&waitAllFinished);
} // DEALER THREAD

void *playerThread(void *id)
{
    long pid = (long)id;
    //file = fopen("gamelog.txt", "w");

    // ROUND INIT
    pthread_mutex_lock(&readyCheck);
    playersReady[pid] = true; // set player ready to play the round
    if (allPlayersCheck(playersReady))
        pthread_cond_signal(&waitAllPlayers);

    pthread_mutex_unlock(&readyCheck);

    // PLAY ROUND
    while (!allPlayersCheck(playerFinished))
    {

        pthread_mutex_lock(&currentPlayerCheck);
        while (pid != currentPlayer || !roundStart)
            pthread_cond_wait(&playerTurn[pid], &currentPlayerCheck);
        pthread_mutex_unlock(&currentPlayerCheck);

        if (!roundFinished)
        { 
            printHand(&playerHands[pid], pid, file);
            Card *card = drawCard(&deck); // Player draws a card
            printf("Player %ld: draws %d\n", pid, card->value);
            fprintf(file, "Player %ld: draws %d\n", pid, card->value);
            insertCard(&playerHands[pid], card); // and inserts it in their hand

            if (checkCardMatch(pid, &playerHands[pid], targetCard, file))
            { // player checks if either card is a match
                printf("Player %ld: wins round %d\n", pid, dealer);
                fprintf(file, "Player %ld: wins round %d\n", pid, dealer);
                roundFinished = true;
                pthread_mutex_lock(&finishedCheck);
                playerFinished[pid] = true;
                pthread_mutex_unlock(&finishedCheck);
            }
            else
            { // neither card matched, so return a random card
                returnRandomCard(pid, &deck, &playerHands[pid], file);
                printHand(&playerHands[pid], pid, file);
            }
            printDeck(&deck, file);
        }
        else
        { // the round was already finished when the player took their turn.
            printf("Player %ld: lost round %d\n", pid, dealer);
            fprintf(file, "Player %ld: lost round %d\n", pid, dealer);
            pthread_mutex_lock(&readyCheck);
            if (!playerFinished[pid])
                playerFinished[pid] = true;

            pthread_mutex_unlock(&readyCheck);
        }

        pthread_mutex_lock(&currentPlayerCheck);
        setNextPlayer(&currentPlayer, dealer); // set the next player
        pthread_mutex_unlock(&currentPlayerCheck);
        pthread_cond_signal(&playerTurn[currentPlayer]); // signal the next player it's their turn

        if (playerFinished[pid])
        { // Hold the player here after they have finished.
            pthread_mutex_lock(&playerFinishCheck);

            while (!allPlayersCheck(playerFinished))
                pthread_cond_wait(&waitAllFinished, &playerFinishCheck);

            pthread_cond_signal(&waitRoundFinish);

            pthread_cond_broadcast(&waitAllFinished);
            pthread_mutex_unlock(&playerFinishCheck);
        }
    } // round finished

    if (!playerFinished[pid])
    { // weird issue where the players didn't get made to wait. previously.
        pthread_mutex_lock(&playerFinishCheck);

        playerFinished[pid] = true;
        while (!allPlayersCheck(playerFinished))
            pthread_cond_wait(&waitAllFinished, &playerFinishCheck);

        pthread_cond_broadcast(&waitAllFinished);
        pthread_mutex_unlock(&playerFinishCheck);
    }
    pthread_cond_broadcast(&waitAllFinished); 
    returnHand(&playerHands[pid]);
} // PLAYER THREAD

int rounds()
{
    file = fopen("gamelog_treadway.txt", "w");
    for (dealer; dealer < NUM_PLAYERS; dealer++)
    { // this for loop might need to be replaced with a while loop

        roundStart = false;
        roundFinished = false;

        for (int i = 0; i < NUM_PLAYERS; i++)
        {
            playersReady[i] = false;
            playerFinished[i] = false;
        }

        for (long i = 0; i < NUM_PLAYERS; i++)
        {
            int create;
            if (i != dealer)
                create = pthread_create(&players[i], NULL, playerThread, (void *)i);
            else
                create = pthread_create(&players[i], NULL, dealerThread, (void *)i);
            if (create != 0)
            {
                printf("Fatal error: thread %ld was not created.\n", i);
                return -1;
            }
        }

        for (int i = 0; i < NUM_PLAYERS; i++)
        {
            int join = pthread_join(players[i], NULL);
            if (join != 0)
                printf("Error joining");
        }
    }
    fclose(file);
    return 0;
}

int main()
{
    srand((unsigned int)time(NULL));
    pthread_mutex_init(&readyCheck, NULL);
    pthread_mutex_init(&finishedCheck, NULL);
    pthread_mutex_init(&currentPlayerCheck, NULL);
    pthread_mutex_init(&playerFinishCheck, NULL);
    pthread_cond_init(&waitAllPlayers, NULL);
    pthread_cond_init(&waitRoundFinish, NULL);
    pthread_cond_init(&waitAllFinished, NULL);

    return rounds();
}

///
