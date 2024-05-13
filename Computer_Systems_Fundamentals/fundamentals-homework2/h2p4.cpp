#include <iostream>
#include <chrono>
#include <cmath>
#include <vector>
#include <string>
#include <fstream>

class Event {
private:
    bool isDown; // true if down, false if alive
    float timeOccurred;
    std::string id;
    Event* nextEvent;
public:
    Event(std::string id, float time, bool down) : nextEvent(nullptr) {
        this->id = id;
        timeOccurred = time;
        isDown = down;
    }
    
    bool getIsDown() {
        return this->isDown;
    }

    float getTime() {
        return this->timeOccurred;
    }

    void setNext(Event* newEvent) {
        this->nextEvent = newEvent;
    }

    Event* getNext() {
        return this->nextEvent;
    }

    std::string getID() {
        return this->id;
    }
};

class EventQueue {
private:
    float s1TimeElapsed, s2TimeElapsed; // keep track of server1 and server2 times separately
    Event* head;
    const float LAMBDA = 0.002;
    const int MAX_TIME = 175200; // this is 20 years in hours
    int size; // the number of Event objects in the EventQueue

    /**
     * A helper function for insertEvent
     * Places an Event in the correct timeslot in the queue
    */
    Event* insertHelper(Event* currentEvent, Event* newEvent) {
        if(currentEvent == nullptr) {
            return newEvent;
        }
        
        if(newEvent->getTime() < currentEvent->getTime()) {
            newEvent->setNext(currentEvent);
            return newEvent;
        }

        currentEvent->setNext(insertHelper(currentEvent->getNext(), newEvent));
        return currentEvent;    
    }

    float ExpDist(float lambda, float r) {
        // r must be between 0 and 1
        // if r == 1, then it'll break, but this is exceptionally unlikely
        float x = 0;
        try {
            x = (1/-lambda)*log(1-r);
        }
        catch(std::exception e) {
            x = (1/-lambda)*log(1-.999);
        }
        return x;
    }

    // only for part A
    void initQueue() {
        int seed = std::chrono::high_resolution_clock::now().time_since_epoch().count(); // generates a seed based on a very precise time
        srand(seed); // seed random, ensuring it produces different values each successive execution
        while(s1TimeElapsed < MAX_TIME) {
            float rand1 = (float(std::rand())) / RAND_MAX;
            float s1TBF = ExpDist(LAMBDA, rand1);
            s1TimeElapsed += s1TBF;
            Event* s1UpEvent = new Event("S1", s1TimeElapsed, false); // not down, in up state
            insertEvent(s1UpEvent);

            s1TimeElapsed += 10;
            Event* s1RecoverEvent = new Event("S1", s1TimeElapsed, true); // is down, in recovery state
            insertEvent(s1RecoverEvent);
        }
    }

public:
    EventQueue() : s1TimeElapsed(0), s2TimeElapsed(0), head(nullptr), size(0) {
        int seed = std::chrono::high_resolution_clock::now().time_since_epoch().count(); // generates a seed based on a very precise time
        srand(seed); // seed random, ensuring it produces different values each successive execution
    }

    // For part A, generates a queue for one server over 20 years
    EventQueue(bool initialize) : s1TimeElapsed(0), s2TimeElapsed(0), head(nullptr), size(0) {
        if(initialize) {
            initQueue();
        }

    }

    ~EventQueue() {
        while(head != nullptr) {
            Event* temp = head;
            head = head->getNext();
            delete temp;
        }
    }

    /**
     * Inserts newEvent into the Queue
    */
    void insertEvent(Event* newEvent) {
        head = insertHelper(head, newEvent);
        size++;
    }

    /**
     * Pops the top Event* off of the queue.
     * Deletes the Event*, but returns an Event with the same data.
    */
    Event pop() {
        Event* temp = head;
        if(head != nullptr) {
            head = head->getNext();
            Event poppedEvent = Event(temp->getID(), temp->getTime(), temp->getIsDown());
            delete temp;
            size--;
            return poppedEvent;
        }
        return Event("null", 0, false);
    }

    /**
     * scheduleEvents() will schedule a "Time Between Failure/up" event as well
     * as schedule a "Recover" event for each/either server.
    */
    void scheduleEvents() {
        float rand1 = (float(std::rand())) / RAND_MAX;
        float rand2 = (float(std::rand())) / RAND_MAX;
        float s1TBF = ExpDist(LAMBDA, rand1);
        float s2TBF = ExpDist(LAMBDA, rand2);
        if(s1TimeElapsed == s2TimeElapsed || size == 0) {
            s1TimeElapsed += s1TBF;
            Event* s1UpEvent = new Event("S1", s1TimeElapsed, false); // not down, in up state
            insertEvent(s1UpEvent);

            s1TimeElapsed += 10;
            Event* s1RecoverEvent = new Event("S1", s1TimeElapsed, true); // is down, in recovery state
            insertEvent(s1RecoverEvent);

            s2TimeElapsed += s2TBF;
            Event* s2UpEvent = new Event("S2", s2TimeElapsed, false);
            insertEvent(s2UpEvent);

            s2TimeElapsed += 10;
            Event* s2RecoverEvent = new Event("S2", s2TimeElapsed, true);
            insertEvent(s2RecoverEvent);
        }
        else if(s1TimeElapsed < s2TimeElapsed) {
            s1TimeElapsed += s1TBF;
            Event* s1UpEvent = new Event("S1", s1TimeElapsed, false); // not down, in up state
            insertEvent(s1UpEvent);

            s1TimeElapsed += 10;
            Event* s1RecoverEvent = new Event("S1", s1TimeElapsed, true); // is down, in recovery state
            insertEvent(s1RecoverEvent);
        }
        else {
            s2TimeElapsed += s2TBF;
            Event* s2UpEvent = new Event("S2", s2TimeElapsed, false);
            insertEvent(s2UpEvent);

            s2TimeElapsed += 10;
            Event* s2RecoverEvent = new Event("S2", s2TimeElapsed, true);
            insertEvent(s2RecoverEvent);
        }
    }

    Event* getHead() {
        return head;
    }
    /**
     * For debugging
     * Prints the contents of each event in the Queue
    */
    void printQueue() {
        Event* current = head;
        while(current != nullptr) {
            //std::cout << "Iterate\n";
            std::cout << "{" << current->getID() << ", " << current->getTime() << ", ";
            current->getIsDown() ? std::cout << "Recovers} " : std::cout << "Fails} ";
            current = current->getNext();
        }
        std::cout << "\n";
    }
};

int main() {

    std::ofstream outputFile;
    outputFile.open("h2p4.txt");
    std::vector<float> failureTimes;

    // Homework 4 Part A
    EventQueue eventQueueA = EventQueue(true);
    Event* current = eventQueueA.getHead();
    outputFile << "Problem 4 Part A--------------------------\n";
    while(current != nullptr) {
        outputFile << "{" << current->getID() << ", " << current->getTime() << ", ";
        current->getIsDown() ? outputFile << "Recovers}\n" : outputFile << "Fails}\n";
        current = current->getNext();
    }
    delete current;
    outputFile << "End Problem 4 Part A--------------------------\n\n";

    outputFile << "Problem 4 Part B--------------------------\n";
    outputFile << "Calculating average time until whole computing system fails with 1000 Iterations.\n";

    outputFile << "Scroll to bottom of file to see average failure time!\n\n";

    // Homework 4 Part B
    for(int i = 0; i < 1000; i++) {
        EventQueue eventQueueB = EventQueue();
        bool criticalFailure = false;

        while(!criticalFailure) {
            eventQueueB.scheduleEvents();
            Event e1 = eventQueueB.pop();
            Event e2 = eventQueueB.pop();
            if(e1.getIsDown() == true && e2.getIsDown() == true) {
                criticalFailure = true;
                outputFile << "[Iteration " << i+1 << "] Critical Failure at [" << e1.getID() << ", " << e1.getTime() << "] && [" << e2.getID() << ", " << e2.getTime() << "]\n";
                failureTimes.push_back(e1.getTime());
            }
        }
    }

    float avg = 0;
    for(int i = 0; i < failureTimes.size(); i++) {
        avg += failureTimes.at(i);
    }
    avg = avg / failureTimes.size();

    outputFile << "Average failure time: " << avg << " hours\n";
    outputFile << "End Problem 4 Part B--------------------------\n";

    outputFile.close();
    return 0;
}