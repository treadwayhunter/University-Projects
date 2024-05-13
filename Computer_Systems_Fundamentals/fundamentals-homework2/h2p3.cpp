#include <iostream>
#include <chrono>
#include <vector>
#include <cmath>
#include <fstream>
#include <string>
/**
 * If processes arrive with a poisson distribution, then the times between
 * processes arriving follow an exponential distribution
*/

class Process {
private: 
    int id;
    float arrivalTime;
    float serviceTime;
public:
    Process(int id, float arrivalTime, float serviceTime) {
        this->id = id;
        this->arrivalTime = arrivalTime;
        this->serviceTime = serviceTime;
    }

    int getID() {
        return this->id;
    }

    float getArrival() {
        return this->arrivalTime;
    }

    float getService() {
        return this->serviceTime;
    }
};


float ExpDist(float lambda, float r) {
    // r must be between 0 and 1
    // if r == 1, then it'll break, but this is exceptionally unlikely
    float x = 0;
    try {
        //return (1/-lambda)*log(1-r);
        x = (1/-lambda)*log(1-r);
    }
    catch(std::exception e) {
        x = (1/-lambda)*log(1-.999);
    }
    return x;
}

int main () {
    int seed = std::chrono::high_resolution_clock::now().time_since_epoch().count(); // generates a seed based on a very precise time
    srand(seed); // seed random, ensuring it produces different values each successive execution
    std::vector<float> arrivalTimes; // a vector keeping track of all 1000 arrival times since last arrival
    std::vector<float> serviceTimes; // a vector keeping track of all 1000 services times
    std::vector<Process> processList;

    std::ofstream outputFile;
    std::string fileName = "h2p3.txt";
    outputFile.open(fileName);

    outputFile << "Scroll to bottom to see averages.\n\n";
    outputFile << "<Process #, arrival_time, requested_service_time>\n";
    
    float totalElapsedArrivalTime = 0;
    for(int i = 0; i < 1000; i++) {
        float arrivalR = (float(std::rand())) / RAND_MAX; // generate a random percentage for arrival rate
        float serviceR = (float(std::rand())) / RAND_MAX; // generate a random percentage for service rate

        arrivalTimes.push_back(ExpDist(2, arrivalR));
        totalElapsedArrivalTime += arrivalTimes.at(i);
        serviceTimes.push_back(ExpDist(1, serviceR));

        processList.push_back(Process(i+1, totalElapsedArrivalTime, serviceTimes.at(i)));
    }

    for(int i = 0; i < processList.size(); i++) {
        outputFile << "<" << processList.at(i).getID() << ", " << processList.at(i).getArrival() << ", " << processList.at(i).getService() << ">\n";
    }

    float arrivalAvg = 0;
    for(int i = 0; i < arrivalTimes.size(); i++) {
        arrivalAvg += arrivalTimes.at(i);
    }
    arrivalAvg = arrivalAvg / 1000;

    float serviceAvg = 0;
    for(int i = 0; i < serviceTimes.size(); i++) {
        serviceAvg += serviceTimes.at(i);
    }
    serviceAvg = serviceAvg / 1000;

    outputFile << "Arrival Rate Avg: 1 process every " << arrivalAvg << " seconds." << "\nService Rate Avg: 1 process every " << serviceAvg << " seconds" << std::endl;
    outputFile.close();

    std::cout << "Check " << fileName << " for data about Homework 2 problem 3.\n";

    return 0;
}