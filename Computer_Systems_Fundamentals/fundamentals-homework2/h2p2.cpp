#include <iostream>
#include <chrono>
#include <fstream>
#include <string>

int main() {
    int seed = std::chrono::high_resolution_clock::now().time_since_epoch().count(); // generates a seed based on a very precise time
    srand(seed); // seed random, ensuring it produces different values each successive execution
    const int NUM = 100;
    std::ofstream outputFile;
    std::string fileName = "h2p2.txt";

    outputFile.open(fileName);

    outputFile << "Part (A) Printing " << NUM << " uniformly distributed integers between 0 and 99 inclusive:\n";
    for(int i = 0; i < NUM; i++) {
        outputFile << i+1 << ": " << std::rand() % 100 << "\n";
    }
    outputFile << "Part (A) Complete --------------------\n\n";
    outputFile << "Part (B) Printing " << NUM << " uniformly distributed floating numbers between 0.25 and 0.5\n";
    for(int i = 0; i < NUM; i++) {

        // create a random value between 0 and 1, 
        // divide by 4 to ensure between 0 and 0.25, 
        // add 0.25 to ensure between 0.25 and 0.5
        float r = ((float(std::rand()) / RAND_MAX) / 4) + 0.25;
        outputFile << i+1 << ": " << r << "\n";
    }
    outputFile << "Part (B) Complete ----------------------\n\n";
    outputFile << "Part (C) Printing " << NUM << " numbers in which the number 1 is produced with probability 0.5, the number 2 with probability 0.2, otherwise a floating number between 3 and 4.\n";
    
    int numOnes = 0;
    int numTwos = 0;
    int other = 0;
    for(int i = 0; i < NUM; i++) {
        // generate a random value between 0 and 1
        float r = (float(std::rand())) / RAND_MAX;

        outputFile << i+1 << ": ";
        if(r <= 0.5) { 
            outputFile << "1";
            numOnes++;
        }
        else if(r > 0.5 && r <= 0.7) {
            outputFile << "2";
            numTwos++;
        }
        else {
            // generate a new random value between 0 and 1, and add 3
            float s = ((float(std::rand())) / RAND_MAX) + 3;
            outputFile << s;
            other++;
        }
        //std::cout << std::endl;
        outputFile << std::endl;
    }
    outputFile << "Number of Ones: " << numOnes << std::endl;
    outputFile << "Number of Twos: " << numTwos << std::endl;
    outputFile << "Between 3 and 4: " << other << std::endl;
    outputFile << "Part (C) Complete-----------------------------\n";
    return 0;
}