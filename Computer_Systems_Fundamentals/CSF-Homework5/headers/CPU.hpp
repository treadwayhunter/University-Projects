#ifndef CPU_HPP
#define CPU_HPP
#include "ReadyQueue.hpp"

class CPU
{
private:
    bool idle;
    double timeBecameIdle;
    ReadyQueue selfReadyQueue;
    double totalIdleTime;
    // ready queue
    // total time idle
public:
    CPU();
    bool isIdle();
    void setIdle(double currentTime);
    void setBusy(double currentTime);
    double getTimeBecameIdle();
    ReadyQueue getReadyQueue();
    void insertQueue(Process *process);
    Process *popQueue();
};

#endif // CPU_HPP