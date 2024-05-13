#ifndef EVENT_HPP
#define EVENT_HPP

#include <string>
#include "Process.hpp"

class Event
{
private:
    Process *process;
    std::string type; // Arrival or Departure
    double executionTime;
    int cpuExecutedOn;
    Event *next;

public:
    Event(Process *process, std::string type, double executionTime);
    Event(Process *process, std::string type, double executionTime, int cpu);
    Process *getProcess();
    std::string getType();
    double getExecutionTime();
    Event *getNext();
    void setNext(Event *next);
    int getCPU();
    void setCPU(int cpu);
};

#endif // EVENT_HPP