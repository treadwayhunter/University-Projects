/*
 * project2.cpp
 * 
 * The implementation file where you will implement your code for Project 2.
 *
 * INSTRUCTOR NOTE: Do not change any of the existing function signatures in
 * this file. You may add helper functions for 
 * any function other than Sum in Question 1. 
 */

#include "recursive_list.h"
#include "project2.h"

/** QUESTION 1: SUM AND PRODUCT **/

// EFFECTS: returns the sum of each element in list, or zero if the list is
//          empty
// Your implementation of Sum should NOT be tail recursive and should NOT
// use a helper function. 
//
// PSEUDOCODE:
// algorithm Sum
// 
/**
 *  BASE CASE
 *  if list is empty:
 *    return 0
 *  
 *  return (list first element) + Sum(list next element) 
 */
int Sum(RecursiveList list) {

  if(ListIsEmpty(list)) {
    return 0;
  }

  return ListFirst(list) + Sum(ListRest(list));
}

// EFFECTS: returns the product of each element in list, or one if the list is
//          empty
//
// PSEUDOCODE:
// algorithm Product
/**
 *  BASE CASE
 *  if list is empty:
 *    return 1 (if 0 is returned, the product will always be 0, which is bad)
 *  
 *  return (list first element) * Product(list next element)
 * 
 */
int Product(RecursiveList list) {
 
  if(ListIsEmpty(list)) {
    return 1;
  }

  return ListFirst(list) * Product(ListRest(list));
}


/** QUESTION 2: TAIL RECURSIVE SUM **/

// EFFECTS: adds the next element in the list to the sum so far
// Your implementation of TailRecursiveSumHelper MUST be tail recursive.
//
// PSEUDOCODE:
// algorithm TailRecursiveSumHelper
/**
 *  
 *  BASE CASE
 *  if list is empty:
 *    return 0
 *  sum = (list first element) + SumHelper(list next element)
 *  return sum
 * 
 */
int TailRecursiveSumHelper(RecursiveList list, int sum_so_far) {

  if(ListIsEmpty(list)) {
    return 0;
  }
  sum_so_far = ListFirst(list) + TailRecursiveSumHelper(ListRest(list), sum_so_far);
  return sum_so_far;
}

// EFFECTS: returns the sum of each element in list, or zero if the list is
//          empty
// THIS FUNCTION IS PROVIDED AS PART OF THE STARTER CODE.
// DO NOT MODIFY THIS FUNCTION.
// 
// PSEUDOCODE:
// algorithm TailRecursiveSum
//   return TailRecursiveSumHelper(list, 0)
int TailRecursiveSum(RecursiveList list) {
  return TailRecursiveSumHelper(list, 0);
}


/** QUESTION 3: FILTER ODD AND FILTER EVEN **/

// EFFECTS: returns a new list containing only the elements of the original list
//          which are odd in value, in the order in which they appeared in list
// For example, FilterOdd(( 4 1 3 0 )) would return the list ( 1 3 )
//
// PSEUDOCODE:
// algorithm FilterOdd
/**
 *
 *  List odd_list
 *  if list is empty:
 *    return empty list
 *  else
 *    if (list first element) is odd
 *      add element to odd_list
 *      recursive call to FilterOdd
 *    else
 *      return FilterOdd
 * 
 *   return odd_list
 * 
 *
 */
RecursiveList FilterOdd(RecursiveList list) {

  RecursiveList odd_list;

  if(ListIsEmpty(list)) {
    return list;
  }
  else {
    if(ListFirst(list) % 2 == 1) {
      odd_list = MakeList(ListFirst(list), FilterOdd(ListRest(list)));
    }
    else {
      return FilterOdd(ListRest(list));
    }
  }

  return odd_list;
}

// EFFECTS: returns a new list containing only the elements of the original list
//          which are even in value, in the order in which they appeared in list
// For example, FilterEven(( 4 1 3 0 )) would return the list ( 4 0 )
//
// PSEUDOCODE:
// algorithm FilterEven
/**
 *
 *  List even_list
 *  if list is empty:
 *    return empty list
 *  else
 *    if (list first element) is even
 *      add element to even_list
 *      next element is recursive call to FilterEven
 *    else
 *      return FilterEven
 * 
 *   return odd_list
 * 
 *
 */
RecursiveList FilterEven(RecursiveList list) {

  RecursiveList even_list;

  if(ListIsEmpty(list)) {
    return list;
  }
  else {
    if(ListFirst(list) % 2 == 0) {
      even_list = MakeList(ListFirst(list), FilterEven(ListRest(list)));
    }
    else {
      return FilterEven(ListRest(list));
    }
  }

  return even_list;
}


/** QUESTION 4: REVERSE **/

// EFFECTS: returns the reverse of list
// For example, the reverse of ( 3 2 1 ) is ( 1 2 3 )
//
// PSEUDOCODE:
// algorithm Reverse
/**
 *
 *  List reverse_list 
 * 
 *  BASE CASE
 *  if list is empty
 *    return list
 *  else
 *    traverse to last elemt of list using recursive call
 *    add element to reverse list
 *  append current list element to reverse_list
 * 
 *  return reverse_list
 */


RecursiveList ReverseHelper(RecursiveList list, RecursiveList reverse_list) {

  if(ListIsEmpty(list)) {
    return list;
  }
  else {
    // traverse to end of list
    reverse_list = ReverseHelper(ListRest(list), reverse_list);
  }
  // add elements to reverse_list in reverse order
  reverse_list = Append(reverse_list, MakeList(ListFirst(list), MakeList()));


  return reverse_list;
}

RecursiveList Reverse(RecursiveList list) {
  //tail recursive call to ReverseHelper
  return ReverseHelper(list, MakeList());
}


/** QUESTION 5: APPEND **/

// EFFECTS: returns the list (first_list second_list)
// For example, if first_list is ( 1 2 3 ) and second_list is ( 4 5 6 ), then
// returns ( 1 2 3 4 5 6 )
//
// PSEUDOCODE:
// algorithm Append
/**
 * 
 *  if list_one is empty:
 *    return list_two
 *  else
 *    list_one = new list with recursive call to Append list_two
 *  return list_one
 * 
 */
/**
 *
 * The recursive call traverses all the way to the end of the list.
 * Once the end of the list is reached, the second list is returned,
 * appending it to the end of the first list. The first list, having the
 * second list appended to it, is returned, and the function is complete.
 * 
*/

RecursiveList Append(RecursiveList first_list, RecursiveList second_list) {
 
  if(ListIsEmpty(first_list)) {
    // append second_list to last element of first_list
    return second_list;
  }
  else {
    // traverse through list, appending the next element of the list until list is empty
    first_list = MakeList(ListFirst(first_list), Append(ListRest(first_list), second_list));
  }

  return first_list;
}


/** QUESTION 6: CHOP **/

// REQUIRES: list has at least n elements
// EFFECTS: returns the list equal to list without its last n elements
//
// PSEUDOCODE:
// algorithm Chop
/**
 * BASE CASE
 * if n == 0:
 *  return list
 * else:
 *  list = Reverse(list)
 *  if list is not empty: 
 *    (n can be greater than the length of the list
 *     - this is to ensure that no operations are performed
 *      on an empty list)
 *    Traverse to next element, with recursive call to ChopHelper
 *  else: (list was empty before n reached 0)
 *    list = empty list
 *  
 *  return list
 * 
 */
/**
 * 
 * The list is reversed in the function because it was easier
 * to edit the front of the list than the back of the list.
 * Essentially, the reversed list front elements are chopped off, 
 * and then reversed again to be in the correct order.
 * 
 * reverse, chop front, reverse --> new list with n last elements chopped
 * 
 * Another issue is if n is greater than the length of the list. If this
 * is the case, then the resulting list is still empty, and no operations
 * are performed on an empty list that would throw and error.
 * 
 * */

RecursiveList ChopHelper(RecursiveList list, unsigned int n) {

  if(n == 0) {
    return list;
  }
  else {
    
    list = Reverse(list);

    if(!ListIsEmpty(list)) {
      list = ListRest(list);
      list = ChopHelper(Reverse(list), n-1);
    }
    else {
      list = MakeList();
    }
  }

  return list;
}

RecursiveList Chop(RecursiveList list, unsigned int n) {

  return ChopHelper(list, n);
}


/** QUESTION 7: ROTATE **/

// EFFECTS: returns a list equal to the original list with the
//          first element moved to the end of the list n times.
// For example, Rotate(( 1 2 3 4 5 ), 2) yields ( 3 4 5 1 2 )
//
// PSEUDOCODE:
// algorithm Rotate
/**
 *
 * if n is not 0 AND list is not empty:
 *  last element = first element
 *  list = next element in list
 *  list appened last element
 *  recursive call to rotate, n-1
 * 
 * return list
 * 
 */
/**
 * 
 * The first element of the list is stored to later be appended
 * The list becomes equal to the next element in the list
 * The stored element is appended to the end of the list
 * This process continues until n == 0
 * 
 * 
*/

RecursiveList Rotate(RecursiveList list, unsigned int n) {

  if(n != 0 && !ListIsEmpty(list)) {
    int last_elm = ListFirst(list);
    list = ListRest(list); // list becomes equal to next element list
    list = Append(list, MakeList(last_elm, MakeList())); // append last element
    list = Rotate(list, n-1); // recursive call until n == 0
  }
  return list;
}


/** QUESTION 8: INSERT LIST **/

// REQUIRES: n <= the number of elements in first_list
// EFFECTS: returns a list comprising the first n elements of first_list,
//          followed by all elements of second_list, followed by any remaining
//          elements of "first_list"
// For example, InsertList (( 1 2 3 ), ( 4 5 6 ), 2) returns ( 1 2 4 5 6 3 )
//
// PSEUDOCODE:
// algorithm InsertList
/**
 * first_list is divided into two parts:
 *  first_list_rhs (right hand side)
 *  first_list_lhs (left hand side)
 * 
 * if n == 0:
 *  if first_list_rhs is not empty:
 *    append first_list_rhs to second_list
 *  return second_list
 *  else:
 *    first_list_lhs = maintain first element, 
 *        but the next element is a recursive call to InsertList
 *    first_list_rhs = next element in list
 */

/**
 *  To maintain integrity of the first list, it was easiest to
 *  split it into a left half and a right half
 * 
 *  For example, 
 *    list1 = [1 2 3 4 5]
 *    list2 = [11 12 13 14 15]
 * 
 *    if n == 2
 *    then the result should be [1 2] [11 12 13 14 15] [3 4 5]
 *      where [1 2] is the left hand side (lhs) of list1
 *      and [3 4 5] is the right hand side (rhs) of list1
 *      and [11 12 13 14 15] is list2
 * 
 *      the final list results is lhs append list2 append rhs
 * 
 *  The difficulty with some of the code arose when attempting to ensure
 *  that this function worked even if empty lists were passed to, so there
 *  are multiple checks throughout to ensure that operations aren't being
 *  performed on empty lists, and that the correct values are still returned.
 * 
 *  Also, if n is greater than the list length, there are checks to make sure
 *  that the second list is appended to the end of the first list
 */
RecursiveList InsertListHelper(RecursiveList first_list_lhs, RecursiveList first_list_rhs, RecursiveList second_list, unsigned int n) {

  if(n == 0) {
    if(!ListIsEmpty(first_list_rhs)) {
      second_list = Append(second_list, first_list_rhs);
    }
 
    return second_list;
  }

  else {
    // Keep track of right hand and left hand sides
    // check LHS isn't empty
    if(!ListIsEmpty(first_list_lhs)) { // LHS is not empty
      // recursive call to InsertListHelper here
      first_list_lhs = MakeList(ListFirst(first_list_lhs), InsertListHelper(ListRest(first_list_lhs), ListRest(first_list_lhs), second_list, n-1));
    }
    else {  // LHS is empty, so make it the second list
      first_list_lhs = second_list;
    }

    if(!ListIsEmpty(first_list_rhs)) { // RHS is not empty
      // RHS is equal to the next element in the list
      first_list_rhs = ListRest(first_list_rhs);
    }

  }

  return first_list_lhs;
}


RecursiveList InsertList(RecursiveList first_list, RecursiveList second_list, unsigned int n) {
  
  return InsertListHelper(first_list, first_list, second_list, n);
}