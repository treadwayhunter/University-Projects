#include "../headers/Process.hpp"

Process::Process(int id, double arrivalTime, double requestedTime) {
    this->next = nullptr;
    this->id = id;
    this->arrivalTime = arrivalTime;
    this->requestedTime = requestedTime;
}

int Process::getID() {
    return this->id;
}

double Process::getArrivalTime() {
    return this->arrivalTime;
}

double Process::getRequestedTime() {
    return this->requestedTime;
}

Process* Process::getNext() {
    return this->next;
}

void Process::setNext(Process* next) {
    this->next = next;
}
