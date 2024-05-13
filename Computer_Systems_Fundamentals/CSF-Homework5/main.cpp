#include <iostream>
#include <vector>
#include <chrono>
#include <fstream>
#include "headers/Process.hpp"
#include "headers/Event.hpp"
#include "headers/EventQueue.hpp"
#include "headers/ReadyQueue.hpp"
#include "headers/CPU.hpp"

bool argumentCheck(int argc, char *argv[], int &arrivalRate, double &serviceTime, int &scenario, int &numCPUs)
{
    if (argc != 5)
    {
        std::cout << "Incorrect number of arguments.\n";
        std::cout << "Should follow >>> ./p1 [50 <= int <= 150] [double] [int == 1 || 2] [int]\n";
        return false;
    }

    try
    {
        arrivalRate = std::stoi(argv[1]);
        serviceTime = std::stod(argv[2]);
        scenario = std::stoi(argv[3]);
        numCPUs = std::stoi(argv[4]);
    }
    catch (std::exception e)
    {
        std::cout << "An argument was not in the expected format.\n";
        std::cout << "Should follow >>> ./p1 [50 <= int <= 150] [double] [int == 1 || 2] [int]\n";

        return false;
    }

    if (scenario != 1 && scenario != 2)
    {
        std::cout << "Scenario argument must be 1 or 2. Entered: " << scenario << "\n";
        std::cout << "Should follow >>> ./p1 [50 <= int <= 150] [double] [int == 1 || 2] [int]\n";
        return false;
    }

    return true;
}

int main(int argc, char *argv[])
{
    int seed = std::chrono::high_resolution_clock::now().time_since_epoch().count(); // generates a seed based on a very precise time
    srand(seed);

    int arrivalRate;
    double serviceTime;
    int scenario;
    int numCPUs;

    // if argument check fails, then exit the program
    if (!argumentCheck(argc, argv, arrivalRate, serviceTime, scenario, numCPUs))
        return 1;

    std::vector<CPU> cpuList(numCPUs);
    for (int i = 0; i < cpuList.size(); i++)
    {
        cpuList[i] = CPU();
    }

    int departureCounter = 0; // keep track of the number of departure events
    int lastEventTime = 0;
    double previousDepartTime = 0; // the time of the last departure, so a CPU was made free at this time
    bool lastEventDeparted = false;
    EventQueue eventQueue = EventQueue(arrivalRate, 1 / serviceTime);
    ReadyQueue readyQueue = ReadyQueue(); // have it ready just in case.

    eventQueue.ArrivalEventBuilder(); // schedule the first event

    while (departureCounter < 10000)
    {
        Event *event = eventQueue.pop();

        if (event == nullptr)
        {
            continue;
        }

        std::cout << event->getProcess()->getID() << ": "
                  << event->getType() << " "
                  << event->getProcess()->getArrivalTime()
                  << " " << event->getProcess()->getRequestedTime()
                  << " " << event->getExecutionTime() << " ";

        if (event->getCPU() > -1)
        {
            std::cout << "[" << event->getCPU() << "]";
        }

        std::cout << "\n";

        if (scenario == 1)
        {
            // std::cout << event->getType() << "\n";
            if (event->getType() == "Arrival")
            {
                // std::cout << "Inside Arrival\n";
                int r = std::rand() % numCPUs;
                // cpuList[r].getReadyQueue().insertProcess(event->getProcess());
                cpuList[r].insertQueue(event->getProcess());
                lastEventDeparted = false;
            }
            else if (event->getType() == "Depart")
            {
                int cpu = event->getCPU();
                double time = event->getExecutionTime();
                cpuList[cpu].setIdle(time);
                previousDepartTime = time;
                departureCounter++;
                //delete event->getProcess();
                lastEventDeparted = true;
            }

            // for each CPU, if it has stuff in the ready queue, ensure it's busy, schedule depart event
            for (int i = 0; i < cpuList.size(); i++)
            {

                if (!cpuList[i].getReadyQueue().isEmpty() && cpuList[i].isIdle())
                {
                    Process *process = cpuList[i].popQueue();
                    cpuList[i].setBusy(previousDepartTime);
                    double departTime = lastEventDeparted
                                            ? previousDepartTime + process->getRequestedTime()
                                            : process->getArrivalTime() + process->getRequestedTime();
                    Event *depart = new Event(process, "Depart", departTime, i);
                    eventQueue.scheduleEvent(depart);
                }
            }
        } // END SCENARIO 1
        if (scenario == 2)
        {
            lastEventTime = event->getExecutionTime();

            if (event->getType() == "Arrival")
            {
                readyQueue.insertProcess(event->getProcess());
                lastEventDeparted = false;
            }
            else if (event->getType() == "Depart")
            {
                lastEventDeparted = true;
                int cpu = event->getCPU();               // the cpu to be freed
                double time = event->getExecutionTime(); // the time the cpu became free
                cpuList[cpu].setIdle(time);
                previousDepartTime = time;
                departureCounter++;
            }

            if (!readyQueue.isEmpty())
            {
                int freeCpu = -1; // so that it isn't accidentally an index
                for (int i = 0; i < cpuList.size(); i++)
                {
                    if (cpuList[i].isIdle())
                    {
                        freeCpu = i;
                        break; // no longer need to search for another cpu, so exit for loop
                    }
                }

                // a CPU was found to be free so schedule an event, else do nothing
                if (freeCpu >= 0)
                {
                    Process *process = readyQueue.pop();
                    cpuList[freeCpu].setBusy(previousDepartTime); // what time was it busy?
                    double departTime = lastEventDeparted
                                            ? previousDepartTime + process->getRequestedTime()
                                            : process->getArrivalTime() + process->getRequestedTime();

                    Event *depart = new Event(process, "Depart", departTime, freeCpu);
                    eventQueue.scheduleEvent(depart);
                }
            }
        } // END SCENARIO 2
        delete event;
        if (eventQueue.getSize() < 100)
            eventQueue.ArrivalEventBuilder();
    }

    std::cout << "\nEvents finished\n";
    std::cout << "Number of CPUs: " << numCPUs << "\n";
    std::cout << "Number of events completed: " << departureCounter << "\n";
    std::cout << "Time Completed: " << previousDepartTime << "\n";

    return 0;
}