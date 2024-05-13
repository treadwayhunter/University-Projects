/**
 * This file contains helper functions to maintain cleanliness in main.c
 */

#ifndef PLAYERS_H
#define PLAYERS_H
#include "deck.h"
#include <stdio.h>

#define NUM_PLAYERS 6

/**
 * Function for printing a player's hand.
 */
void printHand(Deck *playerHand, long pid, FILE *file)
{
    Card *card1, *card2;

    if (playerHand->size == 1)
    {
        card1 = drawCard(playerHand);
        printf("Player %ld: hand (%d)\n", pid, card1->value);
        fprintf(file, "Player %ld: hand (%d)\n", pid, card1->value);
        insertCard(playerHand, card1);
    }
    else if (playerHand->size == 2)
    {
        card1 = drawCard(playerHand);
        card2 = drawCard(playerHand);
        printf("Player %ld: hand (%d,%d)\n", pid, card1->value, card2->value);
        fprintf(file, "Player %ld: hand (%d,%d)\n", pid, card1->value, card2->value);
        insertCard(playerHand, card1);
        insertCard(playerHand, card2);
    }
}

/**
 * Function for a player thread to check if either of their cards matches the target card.
 * Returns true if a match was found.
 */
bool checkCardMatch(long pid, Deck *playerHand, Card *targetCard, FILE *file)
{
    bool match = false;

    Card *card1 = drawCard(playerHand);
    Card *card2 = drawCard(playerHand);

    if (card1->value == targetCard->value || card2->value == targetCard->value)
    {
        // return true;
        printf("Player %ld: hand (%d,%d) <> Target Card is %d\n", pid, card1->value, card2->value, targetCard->value);
        fprintf(file, "Player %ld: hand (%d,%d) <> Target Card is %d\n", pid, card1->value, card2->value, targetCard->value);
        match = true;
    }
    else
    {
        printHand(playerHand, pid, file);
    }

    insertCard(playerHand, card2);
    insertCard(playerHand, card1);

    return match;
}

/**
 * Function for player threads to return a random card from there hand
 * to the bottom of the deck. Insertions into the deck are placed at the
 * bottom of the deck by default.
 */
void returnRandomCard(long pid, Deck *deck, Deck *playerHand, FILE *file)
{

    Card *card1 = drawCard(playerHand);
    Card *card2 = drawCard(playerHand);

    float r = (float)rand() / RAND_MAX;
    if (r > 0.5)
    {
        insertCard(deck, card1);
        insertCard(playerHand, card2);
        printf("Player %ld: discards %d at random\n", pid, card1->value);
        fprintf(file, "Player %ld: discards %d at random\n", pid, card1->value);
    }
    else
    {
        insertCard(deck, card2);
        insertCard(playerHand, card1);
        printf("Player %ld: discards %d at random\n", pid, card2->value);
        fprintf(file, "Player %ld: discards %d at random\n", pid, card2->value);

    }
}

/**
 * At the end of the round, a player should return their cards to the deck.
 * Technically, the deck is regenerated each round during the shuffle() function,
 * so the cards only need to be removed from the player's hand using the drawCard() function.
 */
void returnHand(Deck *playerHand)
{
    while (playerHand->size > 0)
    {
        drawCard(playerHand);
    }
}

/**
 * Function for the dealer to deal cards to each player's hand, starting with
 * the player next to the dealer.
 */
void dealCards(Deck *deck, Deck *playerHands, int dealer)
{
    int dealTo = (dealer + 1) % NUM_PLAYERS;
    for (dealTo; dealTo != dealer; dealTo = (dealTo + 1) % NUM_PLAYERS)
    {
        Card *card = drawCard(deck);
        insertCard(&playerHands[dealTo], card);
    }
}

/**
 * Function for checking the readiness of all players.
 * There are multiple times in the code where the status of players needs
 * to be checked, and won't progress until all are ready.
 */
bool allPlayersCheck(bool *playersReady)
{
    for (int i = 0; i < NUM_PLAYERS; i++)
    {
        if (!playersReady[i])
            return false;
    }
    return true;
}

/**
 * Function for player threads to set the next player in order to signal
 * them to start their turn. In case the currentPlayer == dealer, the dealer is skipped
 * because they are not playing cards.
 */
void setNextPlayer(int *currentPlayer, int dealer)
{
    *currentPlayer = (*currentPlayer + 1) % NUM_PLAYERS;
    if (*currentPlayer == dealer)
    {
        *currentPlayer = (*currentPlayer + 1) % NUM_PLAYERS; // skip the dealer
    }
}

#endif

