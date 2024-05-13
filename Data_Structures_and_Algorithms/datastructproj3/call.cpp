// Run in repl.it:
// g++ -pthread -std=c++17 -o call call.cpp; ./call < call_test1.in
// Run in zeus:
//g++ -pthread -o call call.cpp; ./call < call_test1.in

#include <iostream>
#include <string>
#include <cassert>

#include "dlist.h"

/*
 * Format of input line:
 *
 *  <timestamp> <Name> <status> <duration>
 *
 * Note: first four fields terminated by whitespace
 *       request terminated by newline.
 *
 * Format of input file:
 * 
 * A single line with the number of requests
 * zero or more input lines followed by EOF.
 */

enum Status {
  NONE = 0, SILVER = 1, GOLD = 2, PLATINUM = 3
};

struct reqNode {
  int         timestamp;
  std::string name;
  Status      status;
  int         duration;
};

/**** INSTRUCTOR NOTE: DO NOT MODIFY ABOVE THIS LINE ****/

/***************************************************
 * INSTRUCTOR NOTE: Implement the functions below. *
 **************************************************/

// EFFECTS: Reads and parses each request from the standard input stream
// std::cin. For each request, creates a reqNode struct and enqueues their
// pointers in the requests queue, which is a Dlist of pointers to reqNode
// structs. 
void ObtainRequests(Dlist<reqNode*> &requests) {
  /**
   * First number input is the number of call requests to be made
   * Each request string entered will follow the pattern:
   *    <timestamp> <name> <status> <duration>
   *    Each field is delimeted with 1 space
   *  
   * Error handling was also built in to the request strings.
   *    Each request has to be typed in a very specific manner,
   *    else the program won't work as intended. The error handling
   *    is a try/catch block, and throw events that throws an error
   *    if the user types in an incorrectly formatted string.
   *    It also gives a string briefly explaining what the error
   *    might be.
   */
  int num_requests; // the number of requests to input
  std::cin >> num_requests;

  for(int i = 0; i < num_requests; i++) {
    std::string event;
    std::string delimiter = " ";
    std::cin >> std::ws; // ensure any whitespaces are renmoved before getline
    std::getline(std::cin, event);
    
    try {
      /**
       * Create a new reqNode* to input into the requests queue
       * Check the user's input string, ensuring that each portion was entered correctly.
       *    Input each field into the reqNode after it is verified.  
       * 
       * Once the reqNode* has been correctly built, insert it to the back of the queue
       * 
       */
      reqNode* new_node = new reqNode(); // Create new node

      // if statement is to check for correct string input
      // if there are no spaces in the string, there is an error.
      if(event.find(delimiter) == std::string::npos) {
        std::string bad_string = "Bad event input. Maybe too few or too many arguments.";
        throw bad_string;
      }

      // get timestamp from event string, and check for errors.
      int ts = std::stoi(event.substr(0, event.find(delimiter))); // stoi throws invalid_argument if field value is not an integer
      if(ts < 0) { // Can't have a negative timestamp. If timestamp is negative, throw error
        std::string bad_string = "Timestamp can't be negative.";
        throw bad_string;
      }
      new_node->timestamp = ts; // no errors, so input into node
      event.erase(0, event.find(delimiter) + delimiter.length()); // erase the string up to the first delimeter
      
      // if statement is to check to make sure the event string is still valid after erasure
      if(event.find(delimiter) == std::string::npos) {
        std::string bad_string = "Bad event input. Maybe too few or too many arguments.";
        throw bad_string;
      }

      // get name from event string
      std::string n = event.substr(0, event.find(delimiter)); // get name from string
      new_node->name = n; // no errors, place in node
      event.erase(0, event.find(delimiter) + delimiter.length()); // erase the string up to the next delimeter

      // if statement is to check to make sure the event string is still valid after erasure
      if(event.find(delimiter) == std::string::npos) {
        std::string bad_string = "Bad event input. Maybe too few or too many arguments.";
        throw bad_string;
      }
      
      // get status from event string
      std::string s = event.substr(0, event.find(delimiter)); // get status from event string
      event.erase(0, event.find(delimiter) + delimiter.length()); // erase the string up to the next delimeter
      
      // Status check, ensure status is one of the correct enums.
      // If status is of a correct type, then input that type into the node.
      if(s == "none") {
        new_node->status = NONE;
      }
      else if(s == "silver") {
        new_node->status = SILVER;
      }
      else if(s == "gold") {
        new_node->status = GOLD;
      }
      else if(s == "platinum") {
        new_node->status = PLATINUM;
      }
      else {
        // None of the correct statuses were provided in the string. Statuses are case senstive.
        std::string bad_string = "Bad status input. Ensure correct status is entered, or try all lowercase.";
        throw bad_string;
      }

      // don't need to check for the delimiter
      // should be at end of string
      int d = std::stoi(event);
      if(d < 1) { // check that duration is not less than 1.
        std::string bad_string = "Duration can't be less than 1.";
        throw bad_string;
      }
      new_node->duration = d; // no errors, input into node.

      /**
       * All fields have been input into the node. If there are
       * any additional arguments after the duration, they will be
       * ignored.
       */

      requests.InsertBack(new_node); // insert node into requests 
    // end try block
    }
    catch(std::invalid_argument) {
      // Catches errors from std::stoi
      std::cout << "Bad event input." << std::endl;
      i--; // force reiterate to make user make correct input
    }
    catch(std::string s) {
      std::cout << s << std::endl;
      i--; // force reiterate to make user make correct input
    }
  }
}

// EFFECTS: Insert any callers with timestamps equal to the tick number
// (the clock param) into their appropriate queues. When a caller is
// inserted, you should print a message that exactly matches the following 
// example (with the correct name and status):
// 
// Call from Jeff a silver member
//
// You can get the appropriate status by calling status_names[status]. 
// Note: If two (or more) calls have the same timestamp, they should be
// printed in input file-order, not in priority-order.
void InsertRequests(Dlist<reqNode*> &requests, Dlist<reqNode*> queues[], int clock, std::string status_names[]) {
  /**
   * 
   * requests queue needs to be sorted into the different queues 
   *
   * queues[] is an array of 4 separate queues
   * queues[0] == queues[NONE]
   * queues[1] == queues[SILVER]
   * queues[2] == queues[GOLD]
   * queues[3] == queues[PLATINUM]
   * 
   * The request queue is not guaranteed to be ordered by timestamp or status.
   *    All nodes in the request queue must be checked against 2 conditions:
   *    <timestamp> = clock parameter, to determine if this iteration is the correct time to place the element
   *    <status>, to determine which queue to insert into.
   * 
   * As the request queue gets checked, it will be changed as elements are
   *    removed. All elements that aren't able to placed into a queue need to
   *    be placed back in the request queue.   
   */

  Dlist<reqNode*> temp_request_queue; // create a temporary request queue. Elements that can't be placed in queue are placed here.
  while(!requests.IsEmpty()) { // loop to iterate through entire requests queue.
    reqNode* current_request = requests.RemoveFront();  // pop the first element from requests queue.

    if(current_request->timestamp == clock) { // if time is correct, place in correct queue
      if(current_request->status == NONE) {
        queues[NONE].InsertBack(current_request);
      }
      else if(current_request->status == SILVER) {
        queues[SILVER].InsertBack(current_request);
      }
      else if(current_request->status == GOLD) {
        queues[GOLD].InsertBack(current_request);
      }
      else if(current_request->status == PLATINUM) {
        queues[PLATINUM].InsertBack(current_request);
      }
      std::cout << "Call from " << current_request->name << " a " << status_names[current_request->status] << " member" << std::endl;
    }
    else {
      // The timestamp does not match clock time. Place in temp queue.
      temp_request_queue.InsertBack(current_request);
    }
    current_request = nullptr; // the current_request is no longer needed.
    delete current_request; // delete current_request node to free up memory
  }

  requests = temp_request_queue; // requests queue was empty after iterating through it. Temp queue holds all unused nodes.
  // set requests equal to temp queue
}

// EFFECTS: Simulate the actions of the agent at this tick. The agent must
// follow these rules:
// 1. If the agent is not busy, the agent checks each queue, in priority order
// from Platinum to None. If the agent finds a call, the agent answers the
// call, and you should print a message that exactly matches the following 
// example (with the correct name):
// 
// Answering call from Jeff
// 
// You will need to decrement time_agent_busy by 1. 
// 
// 2. If the agent was already busy at the beginning of this tick, the agent
// continues servicing the current client. You will need to decrement
// time_agent_busy by 1. 
// 
// 3. If the agent is not busy, and there are no current calls, the agent
// does nothing. 
//
// If all calls have been placed, answered, and completed, then return true
// to end the program. Otherwise, return false.
bool SimulateAgent(Dlist<reqNode*> &requests, Dlist<reqNode*> queues[], int &time_agent_busy) {
  // if time_agent_busy is 0, then take a call. If not 0, then decrement until 0...
  // return true when all calls have been answered and completed
  //    meaning that every single queue is empty

  /**
   * If time_agent_busy == 0, then take a call. If not 0, then time_agent_busy-- each function call
   * Function returns true when:
   *    all status queues (queue[]) are empty
   *    requests queue is empty
   *    time_agent_busy == 0
   * If the queues aren't empty, keep accepting calls when time_agent_busy == 0
   */


  bool done = false; // value for function return
  reqNode* call_answered = nullptr; // create a node to keep track of the answered call 
    /**
     * Queues are listed in order of descending priority
     *    PLATINUM, GOLD, SILVER, NONE
     * 
     * When time_agent_busy == 0, check each queue in descending priority.
     *    if PLATINUM is not empty, then take a PLATINUM call first
     *    then check GOLD
     *    then check SILVER
     *    then check NONE
     */
  if(queues[NONE].IsEmpty() && queues[SILVER].IsEmpty() && queues[GOLD].IsEmpty() && queues[PLATINUM].IsEmpty() && time_agent_busy == 0 && requests.IsEmpty()) {
    done = true; // returns true when done. All queues are empty, and time_agent_busy == 0
  }

  // time is equal to 0, so take a call, if any are available.
  if(time_agent_busy == 0) {
    if(!queues[PLATINUM].IsEmpty()) {
      call_answered = queues[PLATINUM].RemoveFront();
    }
    else if(!queues[GOLD].IsEmpty()) {
      call_answered = queues[GOLD].RemoveFront();
    }
    else if(!queues[SILVER].IsEmpty()) {
      call_answered = queues[SILVER].RemoveFront();  
    }
    else if(!queues[NONE].IsEmpty()) {
      call_answered = queues[NONE].RemoveFront();
    }
    if(call_answered != nullptr) {
      std::cout << "Answering call from " << call_answered->name << std::endl;
      time_agent_busy = call_answered->duration;
      time_agent_busy--; // decrement, because agent is fielding the call
      delete call_answered;
    }
  }
  else {
    time_agent_busy--; //if no new calls were taken, decrement because agent is busy fielding a current call.
  }

  return done;
}

/**** INSTRUCTOR NOTE: DO NOT MODIFY BELOW THIS LINE ****/

int main() {
  // The current time, starting at tick 0. 
  int clock = 0;

  // The remaining time that the agent is busy with the current
  // caller, or 0 if the agent is not busy. 
  int time_agent_busy = 0;

  // An array of four queues, one for each status: regular, silver,
  // gold, platinum. Each queue is a DList of pointers to reqNode
  // structs. 
  Dlist<reqNode*> queues[4]; 

  // A queue of requests matching the input. The queue is a DList of 
  // pointers to reqNode structs.
  Dlist<reqNode*> requests;

  // Flag for whether or not the program should terminate. 
  bool done = false;

  // Array of status name strings for each Status. 
  std::string status_names[4];
  status_names[NONE] = "regular";
  status_names[SILVER] = "silver";
  status_names[GOLD] = "gold";
  status_names[PLATINUM] = "platinum";

  // Run call center simulation.
  ObtainRequests(requests);
  while (!done) {
    std::cout << "Starting tick #" << clock << std::endl;
    InsertRequests(requests, queues, clock, status_names);
    done = SimulateAgent(requests, queues, time_agent_busy);
    clock++;
  }

  return 0;
}



