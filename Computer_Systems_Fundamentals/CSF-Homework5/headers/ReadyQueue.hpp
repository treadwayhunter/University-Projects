#ifndef READYQUEUE_HPP
#define READYQUEUE_HPP
#include "Process.hpp"

class ReadyQueue {
private:
    Process* head;
    int size;

    Process* insertProcessHelper(Process* current, Process* newProcess);
public:
    ReadyQueue();
    void insertProcess(Process* newProcess);
    Process* pop();
    bool isEmpty();
    int getSize();
};

#endif // READYQUEUE_HPP