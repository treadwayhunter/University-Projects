// Try to not use a linkedlist

#ifndef PROCESS_HPP
#define PROCESS_HPP

class Process {
private:
    int id;
    double arrivalTime;
    double requestedTime;
    Process* next; // do I want a linkedlist for processes?

public:
    /*Process(int id, double arrivalTime, double requestedTime) : next(nullptr) {
        this->id = id;
        this->arrivalTime = arrivalTime;
        this->requestedTime = requestedTime;
    }

    int getID() { return this->id; }
    double getArrivalTime() { return this->arrivalTime; }
    double requestedTime() { return this->requestedTime; }
    Process* getNext() { return this-> next; }
    void setNext(Process* next) { this->next = next; }*/

    Process(int id, double arrivalTime, double requestedTime);
    int getID();
    double getArrivalTime();
    double getRequestedTime();
    Process* getNext();
    void setNext(Process* next);

};

#endif // PROCESS_HPP