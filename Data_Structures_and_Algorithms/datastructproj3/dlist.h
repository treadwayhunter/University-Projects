#ifndef __DLIST_H__
#define __DLIST_H__

/**********************************************************
 * INSTRUCTOR NOTE: Do not modify the class declarations! *
 * The class Dlist is a class template.                   *
 *********************************************************/
class emptyList {
  // OVERVIEW: an exception class
};

template <typename T>
class Dlist {
  // OVERVIEW: contains a double-ended list of Objects

 public:
  // Maintenance methods

  Dlist();                                // constructor
  Dlist(const Dlist &l);                  // copy constructor
  Dlist &operator=(const Dlist &l);       // assignment
  ~Dlist();                               // destructor

  // Operational methods

  // EFFECTS: returns true if list is empty, false otherwise
  bool IsEmpty() const;

  // MODIFIES: this
  // EFFECTS: inserts o at the front of the list
  void InsertFront(const T &o);

  // MODIFIES: this
  // EFFECTS: inserts o at the back of the list
  void InsertBack(const T &o);

  // MODIFIES: this
  // EFFECTS: removes and returns last object from non-empty list
  //          throws an instance of emptyList if empty
  T RemoveFront();

  // MODIFIES: this
  // EFFECTS: removes and returns last object from non-empty list
  //          throws an instance of emptyList if empty
  T RemoveBack();


 private:
  // A private type

  struct node {
    node   *next;
    node   *prev;
    T      o;
  };

  // Private member variables 

  node   *first; // The pointer to the first node (NULL if none)
  node   *last;  // The pointer to the last node (NULL if none)
  
  // Private utility methods

  // EFFECTS: called by constructors to establish empty list invariant
  void MakeEmpty();

  // EFFECTS: called by copy constructor/operator= to copy nodes
  //          from a source instance l to this instance
  void CopyAll(const Dlist &l);
    
  // EFFECTS: called by destructor and operator= to remove and destroy
  //          all list nodes
  void RemoveAll();
};

// Constructor
template <typename T>
Dlist<T>::Dlist() {
  MakeEmpty();
}

// Copy constructor
template <typename T>
Dlist<T>::Dlist(const Dlist &l) {
  MakeEmpty();
  CopyAll(l);
}

// Assignment operator
template <typename T>
Dlist<T>& Dlist<T>::operator=(const Dlist<T> &l) {
  if (this != &l) {
    RemoveAll();
    CopyAll(l);
  }
  return *this;
}

// Destructor 
template <typename T>
Dlist<T>::~Dlist() {
  RemoveAll();
}

/**** INSTRUCTOR NOTE: DO NOT MODIFY ABOVE THIS LINE ****/

/**********************************************************
 * INSTRUCTOR NOTE: Implement the member functions below. *
 * These member functions are all function templates.     * 
 *********************************************************/

// EFFECTS: returns true if list is empty, false otherwise
template <typename T>
bool Dlist<T>::IsEmpty() const {
   
  return first == nullptr && last == nullptr ? true : false;
}

// MODIFIES: this
// EFFECTS: inserts o at the front of the list
template <typename T>
void Dlist<T>::InsertFront(const T& o) {
 
  node* new_first_node = new node(); // create new node, prepare to make it the first node
  new_first_node->o = o;  // set new node value to o
  new_first_node->next = first; // make first the next node
  new_first_node->prev = nullptr; // make prev node nullptr, as nothing will come before. new_first_node is ready to become first
  first = new_first_node; // set first as the new_first_node
  
  if(first->next != nullptr) {
    // the original first node's prev was nullptr. 
    // This if statement ensures the next node points back to the new first
    // If first->next is nullptr, then this was the first node inserted into the list
    first->next->prev = first;
  }
 
  if(last == nullptr) {
    // If statement catches the case where the new_first_node was the very first node in the Dlist
    // If it is the first and only node, then the first node is also the last node
    last = first;
  }
}

// MODIFIES: this
// EFFECTS: inserts o at the back of the list
template <typename T>
void Dlist<T>::InsertBack(const T& o) {

  node* new_last_node = new node(); // create new node, prepare to make it the last node
  new_last_node->o = o; // set new node value to o
  new_last_node->next = nullptr; // make the next pointer null, as nothing will come after. 
  new_last_node->prev = last; // make last the previous node. new node is ready to become last
  last = new_last_node; // set last as new_last_node

  if(last->prev != nullptr) {
    // the original last node's next was nullptr.
    // This if statement ensures the next node points back to the new last
    // If last->prev is nullptr, then this was the first node inserted into the list
    last->prev->next = last;
  }
  if(first == nullptr) {
    // if this is the first node being inserted
    // if it is the only node, then the last node is also the first node
    first = last;
  }
}

// MODIFIES: this
// EFFECTS: removes and returns last object from non-empty list
//          throws an instance of emptyList if empty
template <typename T>
T Dlist<T>::RemoveFront() {

  if(IsEmpty()) {
    // If list is empty, throw emptyList class
    throw emptyList();
  }

  T temp_o = first->o; // temporarily store node value
  node* temp_node = first->next; // temporarily store next node
  delete first; // each node was created with new, make sure to delete to free up memory
  first = temp_node; // top node was removed, now make first next node
  if(first != nullptr) {
    first->prev = nullptr; // make sure new first prev is nullptr as nothing comes before it
  }
  else {
    last = nullptr; // if first is nullptr, then the list is empty. Make sure last is also nullptr
  }

  return temp_o; // return the value of the original first node
}

// MODIFIES: this
// EFFECTS: removes and returns last object from non-empty list
//          throws an instance of emptyList if empty
template <typename T>
T Dlist<T>::RemoveBack()
{
  if(IsEmpty()) {
    // If list is empty, throw emptyList class
    throw emptyList();
  }

  T temp_o = last->o; // temporarily store node value
  node* temp_node = last->prev; // temporarily store previous node
  delete last; // each node was created with new, make sure to delete to free up memory
  last = temp_node; // last node was removed, make the previous the last node
  if(last != nullptr) {
    last->next = nullptr; // make sure new last next is nullptr, as nothing comes after it
  }
  else {
    first = nullptr; // if last is nullptr, then the list is empty. Make sure first is also nullptr.
  }

  return temp_o; // return the value of the original last node
}

// EFFECTS: called by constructors to establish empty list invariant
template <typename T>
void Dlist<T>::MakeEmpty() {
  // Called by constructor. Ensures first and last are instantiated to nulltpr
  first = nullptr;
  last = nullptr;
}

// EFFECTS: called by copy constructor/operator= to copy nodes
//          from a source instance l to this instance
template <typename T>
void Dlist<T>::CopyAll(const Dlist &l) {
  // Implement this function.

  node* current_node = l.first; // get first node of Dlist l
  while(current_node != nullptr) {
    // While loop traverses every node of Dlist l
    InsertBack(current_node->o); // insert copy of current node into this, in same order of Dlist l
    current_node = current_node->next; // traverse to next node
  }
}

// EFFECTS: called by destructor and operator= to remove and destroy
//          all list nodes
template <typename T>
void Dlist<T>::RemoveAll() {
  // Implement this function.
  while(first != nullptr) {
    // While loop traverses every node of this list
    RemoveFront(); // while first is not null, pop it
  }
}


/**** INSTRUCTOR NOTE: DO NOT DELETE #endif FROM END OF FILE. ****/
#endif /* __DLIST_H__ */