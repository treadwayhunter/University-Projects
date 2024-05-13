// Run in repl.it:
// g++ -pthread -std=c++17 -o calc calc.cpp; 
//./calc < calc_test1.in
// Run in zeus:
// g++ -pthread -o calc calc.cpp; 
//./calc < calc_test1.in

#include <cstdlib>
#include <iostream>
#include <string>

#include "dlist.h"

/*
  operations: + add
              - subtract
              * multiply 
              / divide
              n negate top item
              d duplicate top item
              r reverse top two items
              p print top item
              c clear entire stack
              a print all items
              q quit
*/

class divZero {};
class badInput {};

/**** INSTRUCTOR NOTE: DO NOT MODIFY ABOVE THIS LINE ****/

/*******************************************************
 * INSTRUCTOR NOTE: Implement the functions below.     *
 * You may throw exception classes emptyList, divZero, *
 * or badInput from these functions as needed.         *
 ******************************************************/

// EFFECTS: performs + operation
void DoAdd(Dlist<double> &stack) {
  /**
   * Adds the top two values of the stack together
   * pop top item, temporarily store value --> double x
   * pop next top item, temporarily store value --> double y
   * insert the addition of the two values
   * 
   * the RemoveFront() comes with built in throw statement
   *    if function fails, it will throw emptyList()
   * 
   * However, if x = stack.RemoveFront() passes, and
   *    y = stack.RemoveFront() fails, value x needs to
   *    be inserted back to the top of the stack. To do 
   *    this, a decision was made to catch the emptyList()
   *    error before the main function. In the catch statement,
   *    x is inserted back to top of the stack, and then the
   *    error is thrown again to be caught by the main function.
   */


  double x, y;
  
  x = stack.RemoveFront(); // if fails, then throws emptyList()

  try { // catch the error before the main function
     y = stack.RemoveFront(); // if fails, throws emptyList()
  }
  catch(emptyList) { // catches emptyList()
    stack.InsertFront(x); // not enough operands, so insert x back to stack
    throw emptyList(); // throw back to main function
  }

  stack.InsertFront(x+y); // no errors occurred, insert value of x+y
}

// EFFECTS: performs - operation
void DoSub(Dlist<double> &stack) {

  /**
   * Subtracts the top two values of the stack together
   * pop top item, temporarily store value --> double x
   * pop next top item, temporarily store value --> double y
   * insert the subtraction of the two values
   * 
   * the RemoveFront() comes with built in throw statement
   *    if function fails, it will throw emptyList()
   * 
   * However, if x = stack.RemoveFront() passes, and
   *    y = stack.RemoveFront() fails, value x needs to
   *    be inserted back to the top of the stack. To do 
   *    this, a decision was made to catch the emptyList()
   *    error before the main function. In the catch statement,
   *    x is inserted back to top of the stack, and then the
   *    error is thrown again to be caught by the main function.
   */


  double x,y;
  
  x = stack.RemoveFront(); // if fails, throw emptyList()

  try { // catch the error before the main function
    y = stack.RemoveFront(); // if fails, throws emptyList()
  }
  catch(emptyList) { // catches emptyList() from above
    stack.InsertFront(x); // not enough operands, so insert x back to stack
    throw emptyList(); // throw back to main function
  }

  stack.InsertFront(y-x); // no errors occurred, insert value of y-x
}

// EFFECTS: performs * operation
void DoMult(Dlist<double> &stack) {
 
  /**
   * Multiplies the top two values of the stack together
   * pop top item, temporarily store value --> double x
   * pop next top item, temporarily store value --> double y
   * insert the product of the two values
   * 
   * the RemoveFront() comes with built in throw statement
   *    if function fails, it will throw emptyList()
   * 
   * However, if x = stack.RemoveFront() passes, and
   *    y = stack.RemoveFront() fails, value x needs to
   *    be inserted back to the top of the stack. To do 
   *    this, a decision was made to catch the emptyList()
   *    error before the main function. In the catch statement,
   *    x is inserted back to top of the stack, and then the
   *    error is thrown again to be caught by the main function.
   */


  double x,y;

  x = stack.RemoveFront(); // if fails, throw emptyList()

  try { // catch the error before the main function
    y = stack.RemoveFront(); // if fails, throws emptyList()
  }
  catch(emptyList) { // catches emptyList() from above
    stack.InsertFront(x); // not enough operands, so insert x back to stack
    throw emptyList(); // throw back to main function
  }

  stack.InsertFront(x*y); // no errors occurred, insert value of x*y
}

// EFFECTS: performs / operation
void DoDiv(Dlist<double> &stack) {

  /**
   * Divides the top two values of the stack together
   * pop top item, temporarily store value --> double x
   * pop next top item, temporarily store value --> double y
   * insert the quotient of the two values
   * 
   * the RemoveFront() comes with built in throw statement
   *    if function fails, it will throw emptyList()
   * 
   * However, if x = stack.RemoveFront() passes, and
   *    y = stack.RemoveFront() fails, value x needs to
   *    be inserted back to the top of the stack. To do 
   *    this, a decision was made to catch the emptyList()
   *    error before the main function. In the catch statement,
   *    x is inserted back to top of the stack, and then the
   *    error is thrown again to be caught by the main function.
   * 
   * There is also error checking for dividing by 0.
   * throws divZero() if dividing by 0.
   */
  
  double x,y;

  x = stack.RemoveFront(); // if fails, throw emptyList()

  try { // catch the error before the main function
    y = stack.RemoveFront(); // if fails, throw emptyList()
  }
  catch(emptyList) { // catches emptyList() from above
    stack.InsertFront(x); // not enough operands, so insert x back to stack
    throw emptyList(); // throw back to main function
  }

  if(!x == 0) {
    stack.InsertFront(y/x); // if x is not zero, insert value of y/x
  }
  else { // if x is equal to zero
    stack.InsertFront(y); // insert y back to the stack
    stack.InsertFront(x); // insert x back to the stack
    throw divZero(); // throw divZero() to main function
  }
}

// EFFECTS: performs n operation
void DoNeg(Dlist<double> &stack) {
  /**
   * Changes the top value to its negation.
   * For example 9 becomes -9.
   * 
   * RemoveFront() comes with built in throw, so if
   *    it fails, it will be caught by main function
   */
  double x = stack.RemoveFront() * -1; // if fails, throw emptyList()
  stack.InsertFront(x); // no errors occurred, insert the negation of value
}

// EFFECTS: performs d operation
void DoDup(Dlist<double> &stack) {
  /**
   * Duplicates the top value and inserts it to the stack.
   * RemoveFront() comes with error handling, and will throw emptyList()
   *    if it fails
   */
  double x = stack.RemoveFront(); // if fails, throw emptyList()
  stack.InsertFront(x); // insert x to stack once
  stack.InsertFront(x); // insert x to stack a second time to "duplicate" it
}

// EFFECTS: performs r operation
void DoRev(Dlist<double> &stack) {
  /**
   * Reverses the order of the top two items in the stack.
   * The two items are popped, then replaced in the opposite order.
   * 
   * However, if x = stack.RemoveFront() passes, and
   *    y = stack.RemoveFront() fails, value x needs to
   *    be inserted back to the top of the stack. To do 
   *    this, a decision was made to catch the emptyList()
   *    error before the main function. In the catch statement,
   *    x is inserted back to top of the stack, and then the
   *    error is thrown again to be caught by the main function.
   */
  double x,y;

  x = stack.RemoveFront(); // if fails, throw emptyList()
  try { // catch error before main function
    y = stack.RemoveFront(); // if fails, throw emptyList()
  }
  catch(emptyList) { // catches error from above
    stack.InsertFront(x); // not enough operands, insert x back into stack
    throw emptyList(); // throw to main function
  }
  stack.InsertFront(x); // x was popped first. Insert it first to reverse its place
  stack.InsertFront(y); // y was popped second. Insert it second to reverse its palce
}

// EFFECTS: performs p operation
void DoPrint(Dlist<double> &stack) {
  /**
   * Peeks at the top value of the stack.
   * To do this, the value needs to be removed, then reinserted.
   * RemoveFront() throws emptyList() if it fails
   */
  double top_value = stack.RemoveFront(); // if fails, throws emptyList()
  std::cout << top_value << std::endl; // print top value
  stack.InsertFront(top_value); // insert top value back into stack
}

// EFFECTS: performs c operation
void DoClear(Dlist<double> &stack) {
  /**
   * "Clears"
   * Removes all elements from the stack.
   * If the stack is empty, throw emptyList()
   * 
   */
  if(stack.IsEmpty()) {
    throw emptyList(); // throw to main function if stack is empty
  }

  while(!stack.IsEmpty()) {
    stack.RemoveFront(); // from each element from stack until stack is empty
  }
}

// EFFECTS: performs a operation
void DoPrintAll(Dlist<double> &stack) {
  /**
   * Prints each element from the stack.
   * If the stack is empty, throw emptyList()
   * 
   * To accomplish printing an entire stack without destroying it,
   *    we create another Dlist called print_stack, and set it equal
   *    to stack. Each element is removed from print_stack and printed,
   *    until print_stack is empty. That way the original stack isn't
   *    altered.
   */
  if(stack.IsEmpty()) { // if stack is empty, throw emptyList()
    throw emptyList();
  }

  Dlist<double> print_stack = stack; // create a new stack and set it equal to stack
  while(!print_stack.IsEmpty()) {
    std::cout << print_stack.RemoveFront() << " "; // print each element of print_stack until print_stack is empty
  }
  std::cout << std::endl; // add a new line character
}

/**** INSTRUCTOR NOTE: DO NOT MODIFY BELOW THIS LINE ****/

bool DoOne(Dlist<double> &stack) {
  std::string s;
  double d;

  std::cin >> s;
  switch (s[0]) {
  case '+':
    DoAdd(stack);
    break;
  case '-':
    DoSub(stack);
    break;
  case '*':
    DoMult(stack);
    break;
  case '/':
    DoDiv(stack);
    break;
  case 'n':
    DoNeg(stack);
    break;
  case 'd':
	  DoDup(stack);
	  break;
  case 'r':
	  DoRev(stack);
	  break;
  case 'p':
	  DoPrint(stack);
	  break;
  case 'c':
	  DoClear(stack);
	  break;
  case 'a':
	  DoPrintAll(stack);
	  break;
  case 'q':
	  return true;
	  break;
  case '0':
  case '1':
  case '2':
  case '3':
  case '4':
  case '5':
  case '6':
  case '7':
  case '8':
  case '9':
	  d = std::atof(s.c_str());
    stack.InsertFront(d);
	  break;
  default:
	  badInput e;
	  throw e;
	  break;
  }
  return false;
}

int main() {
  Dlist<double> stack;
  
  bool done = false;
  while (!done) {
    try {
      done = DoOne(stack);
    } catch (emptyList e) {
      std::cout << "Not enough operands\n";
    } catch (divZero e) {
      std::cout << "Divide by zero\n";
    } catch (badInput e) {
      std::cout << "Bad input\n";
    }
  }
}
