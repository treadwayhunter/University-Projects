#include <iostream>
#include <chrono>
#include <cmath>
#include <vector>
#include <string>
#include <fstream>

// Process definition
class Process {
    // <id, arrival Time, requestedTime>
private:
    int id;
    double arrivalTime; // the actual arrival time
    double requestedTime; // the requested time on the processor
    Process* next; // next process in the Ready Queue

public: 
    Process(int id, double arrivalTime, double requestedTime) : next(nullptr) {
        this->id = id;
        this->arrivalTime = arrivalTime;
        this->requestedTime = requestedTime;
    }

    int getID() { return this->id; }
    double getArrivalTime() { return this->arrivalTime; }
    double getRequestedTime() { return this->requestedTime; }
    Process* getNext() { return this->next; }
    void setNext(Process* next) { this->next = next; }
};

// Event definition
class Event {
    // <process, type, execution time>
private:    
    Process* process;
    std::string type;
    double executionTime;   // the time the event will trigger
    Event* next;            // next event in Event Queue

public:
    Event(Process* process, std::string type, double executionTime) : next(nullptr) {
        this->process = process;
        this->type = type;
        this->executionTime = executionTime;
    }

    Process* getProcess() { return this->process; }
    std::string getType() { return this->type; }
    double getExecutionTime() { return this->executionTime; }
    Event* getNext() { return this->next; }
    void setNext(Event* next) { this->next = next; }
};


// Event Queue definition
class EventQueue {
private:    
    Event* head;
    int size;
    double clock;
    int lambda;
    double MU;
    int processCounter;

    Event* insertHelper(Event* current, Event* newEvent) {
        //std::cout << "Insert Helper entered\n";
        if(current == nullptr) {
            return newEvent;
        }
        if(newEvent->getExecutionTime() < current->getExecutionTime()) {
            newEvent->setNext(current);
            return newEvent;
        }

        current->setNext(insertHelper(current->getNext(), newEvent));
        return current;
    }

    double ExpDist(double lambda, double r) {
    // r must be between 0 and 1
    // if r == 1, then it'll break, but this is exceptionally unlikely
        double x = 0;
        try {
            x = (1/-lambda)*log(1-r);
        }
        catch(std::exception e) {
            x = (1/-lambda)*log(1-.999);
        }
        return x;
    }

public:
    EventQueue() : head(nullptr), size(0), clock(0), lambda(10), MU(1/0.04), processCounter(0) {
        int seed = std::chrono::high_resolution_clock::now().time_since_epoch().count(); // generates a seed based on a very precise time
        srand(seed);
    }

    EventQueue(int lambda, double MU) : head(nullptr), size(0), clock(0), processCounter(0) {
        int seed = std::chrono::high_resolution_clock::now().time_since_epoch().count(); // generates a seed based on a very precise time
        srand(seed);
        this->lambda = lambda;
        this->MU = MU;
    }

    void insertEvent(Event* newEvent) {
        head = insertHelper(head, newEvent);
        size++;
    }

    // for deparature events
    void scheduleEvent(Event* e) {

        insertEvent(e);
    }

    void ArrivalEventBuilder() {
        double arrivalRand = (double(std::rand())) / RAND_MAX;
        double serviceRand = (double(std::rand())) / RAND_MAX;
        double arrivalTime = ExpDist(lambda, arrivalRand);
        double serviceTime = ExpDist(MU, serviceRand);
        Process* p = new Process(processCounter++, this->getClock() + arrivalTime, serviceTime);
        Event* arrive = new Event(p, "Arrival", this->getClock() + arrivalTime);
        this->scheduleEvent(arrive);
        this->setClock(this->getClock() + arrivalTime);
    }

    Event* pop() {
        if(!this->isEmpty()) {
            Event* temp = head;
            head = head->getNext();

            size--;
            return temp;
        }
        return nullptr;
    }

    int getSize() { return this->size; }
    double getClock() { return this->clock; }
    void setClock(double clock) {this->clock = clock;}
    bool isEmpty() { return size == 0 ? true : false; }


    void printQueue() {
        Event* temp = head;
        while(temp != nullptr) {
            std::cout << temp->getProcess()->getID() << ": " << temp->getProcess()->getArrivalTime() << " " << temp->getProcess()->getRequestedTime() << " " << temp->getType() << " " << temp->getExecutionTime() << "\n";
            temp = temp->getNext();
        }
    }

};


// Ready Queue definition
class ReadyQueue {
private:
    Process* head;
    int size;

    Process* insertProcessHelper(Process* current, Process* newProcess) {
        if(current == nullptr) {
            return newProcess;
        }

        current->setNext(insertProcessHelper(current->getNext(), newProcess));
        return current;
    }

public:
    ReadyQueue() : head(nullptr), size(0) {}

    void insertProcess(Process* newProcess) {
        head = insertProcessHelper(head, newProcess);
        size++;
    }

    Process* pop() {
        Process* temp = head;
        head = head->getNext();
        size--;
        return temp;
    }

    bool isEmpty() { return size == 0 ? true : false; }
    int getSize() { return this->size; }
};

int main() {
    int lambda; // User will input lambda
    double ts; // User will input ts

    std::ofstream outputFile; // file for storing program information.
    outputFile.open("p1.csv"); // store in csv file so Microsoft Excel can process the file
    outputFile << "Lambda,Avg Turnaround Time,Total Throughput,CPU Utilization, Avg Processes in Ready Queue\n";

    while(true) {
        do {
            std::cout << "\nInput Arrival Rate: ";
            std::cin >> lambda;
            if(lambda < 10 || lambda > 30) {
                std::cout << "Error: Times must be between 10 and 30 inclusive\n";
            }
        }
        while(lambda < 10 || lambda > 30);

        std::cout << "Input Service Time: ";
        std::cin >> ts;
    

        EventQueue eventQueue = EventQueue(lambda, 1/ts);   // The Event Queue. Takes an arrival rate and service rate.
        ReadyQueue readyQueue = ReadyQueue();               // The Ready Queue holds Process objects.
        int processCounter = 0;         // this is an ID for processes.
        int departCounter = 0;          // counts the number of processes that have departed.
        double timeCpuBecameIdle = 0;   // a helper variable that stores the moment the CPU became idle
        double totalIdleTime = 0;       // a variable used to store the total time amount of time the CPU was idle during a run
        double previousDepartTime = 0;      // a helper variable for storing the time of Depart Events
        double previousEventTime = 0;    // a helper variable to measure the time when the last event was removed from the queue
        bool lastEventDeparted = false; // a helper variable that flags whether the last event Departed
        bool cpuIdle = true;            // if CPU is busy, then a Process cannot be popped from Ready Queue
        std::vector<double> turnAroundTimes;    
        std::vector<int> readyQueueSizes;        


        eventQueue.ArrivalEventBuilder();       // schedule the first event

        while(departCounter < 10000) {
            Event* event = eventQueue.pop();    // pop the top event from the event queue, and prepare the event for processing

            if(event != nullptr) {              // if the event queue is empty, then a nullptr event is returned

                previousEventTime = event->getExecutionTime();   // store last pop time for end of program

                if(event->getType() == "Arrival") {
                    readyQueue.insertProcess(event->getProcess());  // push process to ready queue
                    lastEventDeparted = false;                      // last event popped was an arrival
                    
                    if(cpuIdle && event->getProcess()->getID() != 0)  // check that cpu is idle, but don't calculate idle time before first process arrives
                        totalIdleTime += (event->getProcess()->getArrivalTime() - timeCpuBecameIdle); 

                }
                else if(event->getType() == "Depart") {
                    cpuIdle = true;                             // a process completed, so the CPU is no longer busy
                    lastEventDeparted = true;                   // last pop was a departure
                    previousDepartTime = event->getExecutionTime(); // store last departure time 

                    // push turnaround time to vector
                    turnAroundTimes.push_back(event->getExecutionTime() - event->getProcess()->getArrivalTime());
                    departCounter++; // a process completed, so increment counter

                    delete event->getProcess(); // the process is no longer needed, so it is safe to be deleted.
                }
            }

            if(!readyQueue.isEmpty() && cpuIdle) {
                Process* process = readyQueue.pop();    // pop Process from the Ready Queue  
                cpuIdle = false;                        // a Process is being moved to the CPU.
      
                // the below logic is to calculate the appropriate departure time for a process coming out of the ready queue
                double departTime = lastEventDeparted 
                    ? previousDepartTime + process->getRequestedTime() 
                    : process->getArrivalTime() + process->getRequestedTime();
                     
                Event* depart = new Event(process, "Depart", departTime);   // schedule a depart event at the departTime
                eventQueue.scheduleEvent(depart);
                readyQueueSizes.push_back(readyQueue.getSize());    // push ready queue size to vector
            }
            else if(readyQueue.isEmpty() && cpuIdle) {      
                timeCpuBecameIdle = previousDepartTime;     // store the time the cpu became idle
            }
            
            if(eventQueue.getSize() < 100) eventQueue.ArrivalEventBuilder(); // throttle the number of new arrival events scheduled
            delete event;
        }

        // Average turnaround time is calculated by summing all of the turnaround times then dividing by the number of turnaround times
        // This is found to be equivalent to the equation Tq = Tw + Ts to within 3 sigfigs
        double avgTurnaround = 0;
        for(int i = 0; i < turnAroundTimes.size(); i++) {
            avgTurnaround += turnAroundTimes.at(i); // add all Turnaround Times together, then divide by the total number of Turnaround Times
        }
        avgTurnaround = avgTurnaround / turnAroundTimes.size();

        // Calculate avg size of the ready queue
        double avgReadyQueueSize = 0;
        for(int i = 0; i < readyQueueSizes.size(); i++) {
            avgReadyQueueSize += readyQueueSizes.at(i);
        }
        avgReadyQueueSize = avgReadyQueueSize / readyQueueSizes.size();

        double cpuUtil = (1-(totalIdleTime/previousEventTime)); // 1 minus the percentage of time cpu was idle
        double throughput = departCounter / previousEventTime;

        std::cout << "---------------------------------------------\n";
        std::cout << "Average Turnaround: " << avgTurnaround << "\n";
        std::cout << "Total Throughput:   " << throughput << " processes per second\n";
        std::cout << "CPU Utilization:    " << cpuUtil*100 << "%\n";
        std::cout << "Average # processes in Ready Queue: " << avgReadyQueueSize << "\n";
        std::cout << "---------------------------------------------\n";

        outputFile << lambda << "," << avgTurnaround << "," << departCounter/previousEventTime << "," << (1-(totalIdleTime/previousEventTime)) << "," << avgReadyQueueSize << "\n";

        std::cout << "\nRun program again?: (0 == no, 1 == yes) ";
        int runAgain;
        std::cin >> runAgain;
        if(runAgain == 0) {
            break;
        }
    }
    outputFile.close();
}