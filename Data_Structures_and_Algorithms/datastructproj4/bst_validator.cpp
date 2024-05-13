// Run in Repl.it 
// clang++-7 -pthread -std=c++17 -o bst_validator bst_validator.cpp; ./bst_validator < bst_validator1.in
//Run in zeus.cs.txstate.edu:
//g++ -pthread -o bst_validator bst_validator.cpp; ./bst_validator < bst_validator1.in

#include <iostream>
#include <sstream>
#include <vector>

/** QUESTION 4: BST VALIDATOR **/

// EFFECTS: Returns true if the tree is a valid BST, or false otherwise.
// 
// PSEUDOCODE:
/**
 * for each element in vector:
 *    Left Child Index = (i*2) + 1, where i is the index of the parent node.
 *    Right Child Index = (i*2) + 1, where i is the index of the parent node.
 * 
 *    if index of left child is less than vector size:
 *        if value of left child is greater than value of parent:
 *            return false
 *    if index of right child is less than vector size:
 *        if value of right child is less than value of parent:
 *            return false
 * 
 * return true, the whole vector was traversed without any errors.
**/
bool IsBST(std::vector<int> &binary_tree) {

  for(int i = 0; i < binary_tree.size(); i++) {

    int left_child_index = (i*2) + 1;
    int right_child_index = (i*2) + 2;

    if(left_child_index < binary_tree.size()) {
      if(binary_tree[(i*2) + 1] > binary_tree[i]) return false;
    }

    if(right_child_index < binary_tree.size()) {
      if(binary_tree[(i*2) + 2] < binary_tree[i]) return false;
    }
  }

  return true;
}

/**** INSTRUCTOR NOTE: DO NOT MODIFY BELOW THIS LINE ****/

int main() {
  std::vector<int> binary_tree;
  std::string input;
  getline(std::cin, input);
  std::stringstream iss(input);
  int number;
  while (iss >> number) {
    binary_tree.push_back(number);
  }
  if (IsBST(binary_tree)) {
    std::cout << "True";
  } else {
    std::cout << "False";
  }
  return 0;
}
