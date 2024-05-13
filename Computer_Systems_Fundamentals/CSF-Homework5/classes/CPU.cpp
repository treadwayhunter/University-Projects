#include "../headers/CPU.hpp"

CPU::CPU()
{
    this->idle = true; // the CPU isn't doing anything at first, so it is not idle
    this->timeBecameIdle = 0;
    this->selfReadyQueue = ReadyQueue();
}

bool CPU::isIdle()
{
    return this->idle; // true if idle, false if busy
}

void CPU::setIdle(double currentTime)
{
    this->timeBecameIdle = currentTime;
    this->idle = true;
}

void CPU::setBusy(double currentTime)
{
    // if it becomes busy, tally up whether it's been idle for awhile
    this->totalIdleTime += (currentTime - this->timeBecameIdle);
    this->idle = false;
}

double CPU::getTimeBecameIdle()
{
    return this->timeBecameIdle;
}

ReadyQueue CPU::getReadyQueue()
{
    return this->selfReadyQueue;
}

void CPU::insertQueue(Process *process)
{
    this->selfReadyQueue.insertProcess(process);
}

Process *CPU::popQueue()
{
    return this->selfReadyQueue.pop();
}
