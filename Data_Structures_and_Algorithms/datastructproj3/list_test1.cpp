// Run in repl.it:
// g++ -pthread -std=c++17 -o list_test1 list_test1.cpp; ./list_test1 
// Run in zeus:
//g++ -pthread -o list_test1 list_test1.cpp; ./list_test1 

#include <iostream>
#include <string>

#include "dlist.h"

enum Job {STUDENT, FACULTY, STAFF};

struct Record {
  std::string name;
  std::string uniqname;
  Job job;
};

int main() {
  // TEST DLIST OF INTS
  std::cout << "Creating Dlist test_list\n";
  Dlist<int> test_list; 
  std::cout << "test_list is empty? " << test_list.IsEmpty() << std::endl;
  test_list.InsertFront(2);
  test_list.InsertFront(1);
  test_list.InsertBack(3);
  test_list.InsertBack(4);
  while (!test_list.IsEmpty()) {
    std::cout << test_list.RemoveFront() << std::endl;
  }
  
  //std::terminate();

  test_list.InsertFront(2);
  test_list.InsertFront(1);
  test_list.InsertBack(3);
  test_list.InsertBack(4);
  while (!test_list.IsEmpty()) {
    std::cout << test_list.RemoveBack() << std::endl;
  }

  // TEST DLIST OF RECORD PTRS

  // Records are big, so create a linked list of pointers 
  // instead of a linked list of Records
  std::cout << "\nCreating Dlist bobcat_access\n";
  Dlist<Record*> bobcat_access; 
  std::cout << "bobcat_access is empty? " << bobcat_access.IsEmpty() << std::endl;

  //bobcat_access.first = nullptr;
  // Create a new Record and insert to front of bobcat_access
  Record* p = new Record;
  p->name = "Ghadeer A";
  p->uniqname = "GhadeerA";
  p->job = FACULTY;
  bobcat_access.InsertFront(p);

  // You can test other dlist operators on bobcat_access below

  // Don't forget to delete objects on the heap
  while (!bobcat_access.IsEmpty()) {
    Record *r = bobcat_access.RemoveFront();
    std::cout << r->uniqname << std::endl;
    delete r;
  }

  // MY OWN TESTS
  Dlist<int> my_list1;
  std::cout << "\nList 1 Init\n";
  for(int i = 0; i < 10; i++) {
    my_list1.InsertBack(i);
  }
  std::cout << "Copy Constructor:\n";
  std::cout << "List 2 = List 1\n";
  Dlist<int> my_list2 = Dlist<int>(my_list1);
  std::cout << "Overload Operator:\n";
  std::cout << "List 3 = List 1\n";
  Dlist<int> my_list3 = my_list1;
  std::cout << "\nList 4 init\n";
  Dlist<int> my_list4;
  for(int i = 0; i < 10; i++) {
    my_list4.InsertBack(i*2);
  }
  std::cout << "\nList 4 = List 1\n";
  my_list4 = my_list1;

  std::cout << "\n-------END OF PROGRAM REACHED---------\n";
  return 0;
}