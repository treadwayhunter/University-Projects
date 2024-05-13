#ifndef EVENTQUEUE_HPP
#define EVENTQUEUE_HPP
#include "Event.hpp"

class EventQueue {
private:
    Event* head;
    int size;
    double clock;
    int lambda;
    double MU; // mu, the greek letter
    int processCounter;

    Event* insertHelper(Event* current, Event* newEvent);
    double ExpDist(double lambda, double r);
public:
    EventQueue();
    EventQueue(int lambda, double MU);
    void insertEvent(Event* newEvent);
    void scheduleEvent(Event* e); // is this necessary?
    void ArrivalEventBuilder();
    Event* pop();
    int getSize();
    bool isEmpty();
    double getClock();
    void setClock(double clock);
    
    void printQueue(); // for debug help
};

#endif // EVENTQUEUE_HPP