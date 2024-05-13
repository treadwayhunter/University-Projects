#include "../headers/EventQueue.hpp"
#include <cmath>
#include <chrono>
#include <iostream> // for debug

Event *EventQueue::insertHelper(Event *current, Event *newEvent)
{
    if (current == nullptr)
    {
        return newEvent;
    }
    if (newEvent->getExecutionTime() < current->getExecutionTime())
    {
        newEvent->setNext(current);
        return newEvent;
    }
    current->setNext(insertHelper(current->getNext(), newEvent));
    return current;
}

double EventQueue::ExpDist(double lambda, double r)
{
    double x = 0;
    try
    {
        x = (1 / -lambda) * log(r);
    }
    catch (std::exception e)
    {
        x = (1 / -lambda) * log(.001);
    }
    return x;
}

EventQueue::EventQueue()
{
    int seed = std::chrono::high_resolution_clock::now().time_since_epoch().count(); // generates a seed based on a very precise time
    srand(seed);
    this->head = nullptr;
    this->size = 0;
    this->clock = 0;
    this->lambda = 50;
    this->MU = 1 / 0.02;
    this->processCounter = 0;
}

EventQueue::EventQueue(int lambda, double MU)
{
    int seed = std::chrono::high_resolution_clock::now().time_since_epoch().count(); // generates a seed based on a very precise time
    srand(seed);
    this->head = nullptr;
    this->lambda = lambda;
    this->MU = MU;
    this->clock = 0;
    this->size = 0;
    this->processCounter = 0;
}

void EventQueue::insertEvent(Event *newEvent)
{
    this->head = insertHelper(this->head, newEvent);
    size++;
}

void EventQueue::scheduleEvent(Event *e)
{
    insertEvent(e);
}

void EventQueue::ArrivalEventBuilder()
{
    double arrivalRand = (double(std::rand())) / RAND_MAX;
    double serviceRand = (double(std::rand())) / RAND_MAX;
    double arrivalTime = ExpDist(lambda, arrivalRand);
    double serviceTime = ExpDist(MU, serviceRand);
    Process *p = new Process(processCounter++, this->getClock() + arrivalTime, serviceTime);
    Event *arrive = new Event(p, "Arrival", this->getClock() + arrivalTime);
    this->scheduleEvent(arrive);
    this->setClock(this->getClock() + arrivalTime);
}

Event *EventQueue::pop()
{
    if (!this->isEmpty())
    {
        Event *temp = head;
        head = head->getNext();

        size--;
        return temp;
    }
    return nullptr;
}

int EventQueue::getSize() { return this->size; }
double EventQueue::getClock() { return this->clock; }
void EventQueue::setClock(double clock) { this->clock = clock; }
bool EventQueue::isEmpty() { return size == 0 ? true : false; }

void EventQueue::printQueue()
{
    Event *temp = head;
    while (temp != nullptr)
    {
        std::cout << temp->getProcess()->getID() << ": " << temp->getProcess()->getArrivalTime() << " " << temp->getProcess()->getRequestedTime() << " " << temp->getType() << " " << temp->getExecutionTime() << "\n";
        temp = temp->getNext();
    }
}