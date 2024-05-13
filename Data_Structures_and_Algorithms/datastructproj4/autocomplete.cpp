/*
 * autocomplete.cpp
 * 
 * The file where you will implement your autocomplete code for Project 4.
 *
 * INSTRUCTOR NOTE: Do not change any of the existing function signatures in
 * this file, or the testcases will fail. 
 */

#include "autocomplete.h"
#include <iostream> // for test
/** QUESTION 1: FINDNODE **/

// EFFECTS: Traverses the Tree based on the charactes in the prefix and 
//          returns the TreeNode that we end at. If we cannot find a valid node,
//          we return an empty TreeNode. The index variable keeps track of what 
//          character we're at in prefix.
// 
// PSEUDOCODE:
/**
 * 
 * parameters: node, prefix, index
 * 
 * if node is empty, return empty node
 * if prefix is empty, return the parameter node
 * 
 * for each child of parameter node:
 *    if child[i] == prefix.at(index)
 *        n = index
 * if no node was found with the prefix character, return empty node
 * if index == last character in prefix, return that node
 * 
 * return FindNode(node.GetChildren()[n], prefix, ++index)
 * 
 */
TreeNode<char> FindNode(TreeNode<char> node, std::string prefix, int index) {

  if(node.IsEmpty()) return TreeNode<char>(); // return an empty node

  if(prefix == "") return node; // return the node that was passed into the function
  
  int n = -1; // A value used to store the index of the desired element
  for(int i = 0; i < node.GetChildren().size(); i++) { // check each child node to see if it matches the current prefix character
    if(node.GetChildren()[i].GetValue() == prefix.at(index)) n = i;
  }

  if(n == -1) return TreeNode<char>(); // no node was found, return an empty node

  if(index == prefix.size()-1) return node.GetChildren()[n]; // the desired node was found, return that node

  return FindNode(node.GetChildren()[n], prefix, ++index); // desired node hasn't been found yet, do recursive call
}

/** QUESTION 2: COLLECTWORDS **/

// EFFECTS: Collects all the words starting from a given TreeNode. For each word, 
//          prepends the word with the prefix and adds it to the results vector.
// 
// PSEUDOCODE:
/**
 * 
 * parameters: node, prefix, &results
 * 
 * for each child for current node:
 *    string temp = prefix
 *    if child node != $:
 *       append node value to temp
 *       CollectWords(current child, temp, results)
 *    else
 *        results.push_back(temp) // temp is now a completed word, so add it to results vector
 * 
 */ 
void CollectWords(TreeNode<char> node, std::string prefix, std::vector<std::string> &results) {
  
  for(int i = 0; i < node.GetChildren().size(); i++) { // check each child node
    std::string temp = prefix;  // temp string that is equal to the passed prefix. Don't want to accidentally modify prefix, which is out of the scope.
                                // each time the for loop starts, temp needs to be set equal to prefix again.
    if(node.GetChildren()[i].GetValue() != '$') { // if the child is not $, add it to temp
      temp.push_back(node.GetChildren()[i].GetValue()); // add the character to the end of temp
      CollectWords(node.GetChildren()[i], temp, results); // keep traversing the tree until node value == $
    }
    else {
      results.push_back(temp);
    }    
  }

}

/** QUESTION 3: GETCANDIDATES **/

// EFFECTS: Returns the list of all possible words that can be made starting with
//          the letters in prefix, based on traversing the tree with the given root.
// 
// PSEUDOCODE:
// algorithm GetCandidates
/**
 * paramerters: root, prefix
 * 
 * vector words;
 * CollectWords(FindNode(root, prefix, 0), prefix, words);
 * 
 * return words;
 * 
 */
std::vector<std::string> GetCandidates(TreeNode<char> root, std::string prefix) {

  std::vector<std::string> words; // vector to store the results of CollectWords function
  CollectWords(FindNode(root, prefix, 0), prefix, words); // collect all candidates from given prefix

  return words;
}
