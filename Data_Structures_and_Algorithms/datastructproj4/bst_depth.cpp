// Run in Repl.it
// clang++-7 -pthread -std=c++17 -o bst_depth bst_depth.cpp; ./bst_depth < bst_depth1.in
//Run in zeus.cs.txstate.edu:
//g++ -pthread -o bst_depth bst_depth.cpp; ./bst_depth < bst_depth1.in

#include <iostream>
#include <sstream>
#include <vector>

/** QUESTION 5: DEPTH AND PATH **/

// EFFECTS: Returns the depth of the target node. Modifies the path vector
//          so that it contains the node values in the path from the root to
//          the target node.
// 
// PSEUDOCODE:
/**
 *
 * int target_index = -1 // Set to -1, in case the target node doesn't exist.
 * int ancestor_index = 0 // Set to first index of bst
 * 
 * // for loop determines the index of the target node
 * for each element in bst vector:
 *    if target == bst[i]:
 *      target_index = i 
 * 
 * if target_index > -1, then the element was found in bst vector:
 *    // traverse the tree starting from the root, 
 *    //    inserting each ancestor of target node in path vector
 *    do:
 *        path.push_back(bst[ancestor_index]) // adds ancestor node to path vector
 *        if target node < ancestor node:
 *            traverse to left child (i*2) + 1
 *        else:
 *            traverse to right child (i*2) + 2
 *    while(ancestor_index < target_index)
 *  
 * 
 * // The depth of the target node is the path vector size - 1
 * return path.size() - 1
**/
int GetDepthAndPath(std::vector<int> &bst, int target, std::vector<int> &path) {

  int target_index = -1;
  int ancestor_index = 0;
  for(int i = 0; i < bst.size(); i++) {
    if(target == bst[i]) {
      target_index = i;
    }
  }
  
  if(target_index > -1) {
    do {
      path.push_back(bst[ancestor_index]);

      if(bst[target_index] < bst[ancestor_index]) {
        // traverse left
        ancestor_index = (ancestor_index*2) + 1;
      }
      else {
        // traverse right
        ancestor_index = (ancestor_index*2) + 2;
      }
    }
    while(ancestor_index <= target_index);
  }


  return path.size()-1;
}

/**** INSTRUCTOR NOTE: DO NOT MODIFY BELOW THIS LINE ****/

int main() {
  std::vector<int> bst;
  std::string input;
  getline(std::cin, input);
  std::stringstream iss(input);
  int number;
  while (iss >> number) {
    bst.push_back(number);
  }
  int target;
  std::cin >> target;
  
  std::vector<int> path;
  int depth = GetDepthAndPath(bst, target, path);

  std::cout << "depth = " << depth << std::endl;
  std::cout << "path = ";
  for(int i = 0; i < path.size(); i++) {
    std::cout << path[i] ;
    if (i < path.size() - 1) {
      std::cout << " ";
    }
  }
  return 0;
}
