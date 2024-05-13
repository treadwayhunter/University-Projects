#include <stdio.h>
#include <stdlib.h>
#ifndef DECK_H
#define DECK_H
typedef struct Card
{
    int value;
    struct Card *next;
} Card;

typedef struct
{
    int size;   // current size of the deck. As each card is popped, the deck size should shrink.
    Card *head; // the top card of the deck
    // Card *bottom; // keep track of the bottom card
} Deck;

int deckArr[52]; // array is going to be used for shuffling, then pushing to the actual Deck struct

void initDeckArr()
{
    int counter = 0;
    for (int i = 0; i < 52; i++)
    {
        if (i % 4 == 0)
        {
            // printf("mod 4 == 0\n");
            counter++;
        }
        // printf("Value of counter: %d\n", counter);
        deckArr[i] = counter;
    }
}

Card *insertCardHelper(Card *current, Card *newCard)
{
    if (current == NULL)
    {
        return newCard;
    }

    current->next = insertCardHelper(current->next, newCard);
    return current;
}

void insertCard(Deck *deck, Card *newCard)
{
    deck->head = insertCardHelper(deck->head, newCard);
    deck->size++;
}

/**
 * Function that clears the deck
 */
void emptyDeck(Deck *deck)
{
    Card *current = deck->head;
    Card *next;

    while (current != NULL)
    {
        next = current->next;
        free(current);
        current = next;
    }

    deck->head = NULL;
}

/**
 * Simple shuffle algorithm. Does a good job.
 */
void shuffleDeck(Deck *deck)
{
    if (deck->size == 0)
    {
        initDeckArr();
    }
    else
    {
        emptyDeck(deck);
    }

    for (int pass = 0; pass < 3; pass++)
    { // shuffles array 3 times. Once is probably ok.
        for (int i = 0; i < 52; i++)
        {
            int newPos = (int)rand() % 52;
            int temp = deckArr[newPos];
            deckArr[newPos] = deckArr[i];
            deckArr[i] = temp;
        }
    }

    for (int i = 0; i < 52; i++)
    {
        // insert card at bottom
        Card *card = (Card *)malloc(sizeof(Card));
        card->value = deckArr[i];
        card->next = NULL;
        insertCard(deck, card);
    }
}

/**
 * Pops card from the deck
 */
Card *drawCard(Deck *deck)
{
    if (deck->size > 0)
    {
        Card *card = deck->head;
        deck->head = deck->head->next;
        card->next = NULL;
        deck->size--;
        return card;
    }
    else
    {
        printf("The deck was empty!!!\n");
        return NULL;
    }
    // else return NULL;
}

void printDeck(Deck *deck, FILE *file)
{
    // printf("Printing deck...\n");
    Card *current = deck->head;
    printf("DECK: ");
    fprintf(file, "DECK: ");
    while (current != NULL)
    {
        int value = current->value;
        printf("%d ", value);
        fprintf(file, "%d ", value);
        current = current->next;
    }
    printf("\n");
    fprintf(file, "\n");
}

#endif
//