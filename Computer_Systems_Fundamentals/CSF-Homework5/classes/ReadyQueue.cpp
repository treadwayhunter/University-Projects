#include "../headers/ReadyQueue.hpp"

Process *ReadyQueue::insertProcessHelper(Process *current, Process *newProcess)
{
    if (current == nullptr)
    {
        return newProcess;
    }

    current->setNext(insertProcessHelper(current->getNext(), newProcess));
    return current;
}

ReadyQueue::ReadyQueue()
{
    this->head = nullptr;
    this->size = 0;
}

void ReadyQueue::insertProcess(Process *newProcess)
{
    this->head = insertProcessHelper(this->head, newProcess);
    size++;
}

Process *ReadyQueue::pop()
{
    Process *temp = this->head;
    this->head = this->head->getNext();
    this->size--;
    return temp;
}

bool ReadyQueue::isEmpty() { return this->size == 0 ? true : false; }
int ReadyQueue::getSize() { return this->size; }