// Run in Repl.it
// clang++-7 -pthread -std=c++17 -o bst_lca bst_lca.cpp; ./bst_lca < bst_lca1.in
//Run in zeus.cs.txstate.edu:
//g++ -pthread -o bst_lca bst_lca.cpp; ./bst_lca < bst_lca1.in
#include <iostream>
#include <sstream>
#include <vector>

/** QUESTION 6: LOWEST COMMON ANCESTOR **/

// EFFECTS: Returns the value of the lowest common ancestor of nodes l and m.
// 
// PSEUDOCODE:
// algorithm GetLCA
/**
 * 
 * int depth_1 = 0; // used to store height of node l
 * int index_l = 0; // used to store index of node l
 * int depth_m = 0; // used to store height of node m
 * int index_m = 0; // used to store index of node m
 * 
 * for each element in vector bst:
 *    if bst[i] == l, index_1 = i
 *    if bst[i] == m, index_m = i;
 * 
 * temp_index = index_l
 * while temp_index > 0 // used to determine height of node l
 *    temp_index = index of parent node
 *    depth_l++
 * 
 * temp_index = index_m
 * while temp_index > 0 // used to determine height of node m
 *    temp_index = index of parent node
 *    depth_m++
 * 
 * while depth_l != depth_m
 *    bring each node to the same height. Preferably the larger height to the lower height.
 *    
 * 
 * while bst[index_l] != bst[index_m]
 *    raise each node up to its parent. If they are the same node, then that's the LCA
 * 
 */
// COMMENTS:
  /**
   * 
   * Determine the index of each node.
   * Determine the depth of each node.
   * If nodes are on different depths, bring them to the same depth
   *    (bring depth of lowest one to the higher one)
   * Keep raising up each node to it's parent
   *    if the nodes are equal, that's the LCA
   *    
  */
int GetLCA(std::vector<int> &bst, int l, int m) {

  // parent = (i-1) / 2
  // left node = (i*2) + 1
  // right node = (i*2) + 2
  int depth_l = 0;
  int index_l = 0;
  int depth_m = 0;
  int index_m = 0;

  // determine the index of each node
  for(int i = 0; i < bst.size(); i++) {
    if(bst[i] == l) index_l = i;
    if(bst[i] == m) index_m = i;
  } // both indeces should be good

  // while loops determine the depth of each node
  // while the index is greater than 0, keep looping until it's the root node == index 0
  int temp_index = index_l; // create temp index to take on value of index of node l
  while(temp_index > 0) {
    temp_index = (temp_index - 1) / 2; // 
    depth_l++;
  }
  temp_index = index_m; // set temp index to take on value of index of node m
  while(temp_index > 0) {
    temp_index = (temp_index - 1) / 2;
    depth_m++;
  }

  // bring each node to the same depth.
  while(depth_l != depth_m) {
    if(depth_m < depth_l) {
      // bring l to same height as m
      index_l = (index_l - 1)/2;
      depth_l--;
    }
    else {
      // bring m to same height as l
      index_m = (index_m - 1)/2;
      depth_m--;
    }
  }

  // keep checking parent node until the resulting nodes are the same.
  while(bst[index_l] != bst[index_m]) {
    index_l = (index_l - 1)/2;
    index_m = (index_m - 1)/2;
  }

  return bst[index_l];
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
  int l, m;
  std::cin >> l;
  std::cin >> m;
  
  std::vector<int> path;
  int lca = GetLCA(bst, l, m);

  std::cout << "lca = " << lca;
  return 0;
}
