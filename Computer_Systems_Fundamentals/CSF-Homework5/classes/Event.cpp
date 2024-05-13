#include "../headers/Event.hpp"

Event::Event(Process *process, std::string type, double executionTime) : next(nullptr)
{
    this->process = process;
    this->type = type;
    this->executionTime = executionTime;
    this->cpuExecutedOn = -1; // initially not set with any CPU
}

Event::Event(Process *process, std::string type, double executionTime, int cpu)
{
    this->next = nullptr;
    this->process = process;
    this->type = type;
    this->executionTime = executionTime;
    this->cpuExecutedOn = cpu; // initially not set with any CPU
}

Process *Event::getProcess()
{
    return this->process;
}

std::string Event::getType()
{
    return this->type;
}

double Event::getExecutionTime()
{
    return this->executionTime;
}

Event *Event::getNext()
{
    return this->next;
}

void Event::setNext(Event *next)
{
    this->next = next;
}

int Event::getCPU()
{
    return this->cpuExecutedOn;
}

void Event::setCPU(int cpu)
{
    this->cpuExecutedOn = cpu;
}