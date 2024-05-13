// Run in Repl.it
// clang++-7 -pthread -std=c++17 -o auto_test1 autocomplete.cpp auto_test1.cpp; ./auto_test1

/*
 * auto_test1.cpp
 * 
 * Simple test cases for verifying the functionality of your code in
 * autocomplete.cpp.
 *
 * INSTRUCTOR NOTE: This main function contains some test cases from the project 
 * instructions in order to help you get started with testing your code. You can
 * update or add test cases below to help you verify that your code is functional. 
 * Please test and debug your code!
 * 
 * These may not be the exact test cases we are using in the autograder in
 * Gradescope. The Gradescope test cases are more numerous than the basic test
 * cases provided below, and they will be used to determine your correctness
 * grade. We will NOT be running this main function as part of the autograding
 * process.
 * 
 * It is expected that the main function will result in a segfault if you have not
 * yet implemented the functions in autocomplete.cpp. If you want to test only part
 * of the main function, you can comment out the rest.
 */

#include <iostream>
#include <vector>

#include "autocomplete.h"
#include "tree_node.h"
using namespace std;

TreeNode<char> MakeExample() {
  return (
    TreeNode<char>('*', std::vector< TreeNode<char> > { 
      TreeNode<char>('a', std::vector< TreeNode<char> > { 
        TreeNode<char>('c', std::vector< TreeNode<char> > { 
          TreeNode<char>('e', std::vector< TreeNode<char> > { 
            TreeNode<char>('$')
          }),
          TreeNode<char>('n', std::vector< TreeNode<char> > { 
            TreeNode<char>('e', std::vector< TreeNode<char> > { 
              TreeNode<char>('$')
            })
          })
        }),
        TreeNode<char>('n', std::vector< TreeNode<char> > { 
          TreeNode<char>('d', std::vector< TreeNode<char> > { 
            TreeNode<char>('$'),
            TreeNode<char>('r', std::vector< TreeNode<char> > { 
              TreeNode<char>('e', std::vector< TreeNode<char> > { 
                TreeNode<char>('w', std::vector< TreeNode<char> > { 
                  TreeNode<char>('$')
                })
              })
            })
          })
        }),
      }),
      TreeNode<char>('b', std::vector< TreeNode<char> > {
        TreeNode<char>('e', std::vector< TreeNode<char> > {
          TreeNode<char>('a', std::vector< TreeNode<char> > {
            TreeNode<char>('m', std::vector< TreeNode<char> > {
              TreeNode<char>('$')
            })
          }),
          TreeNode<char>('e', std::vector< TreeNode<char> > {
            TreeNode<char>('f', std::vector< TreeNode<char> > {
              TreeNode<char>('$')
            }),
            TreeNode<char>('s', std::vector< TreeNode<char> > {
              TreeNode<char>('$')
            })
          })
        })
      }),
      TreeNode<char>('c', std::vector< TreeNode<char> > {
        TreeNode<char>('a', std::vector< TreeNode<char> > {
          TreeNode<char>('t', std::vector< TreeNode<char> > {
            TreeNode<char>('$')
          })
        }),
        TreeNode<char>('o', std::vector< TreeNode<char> > {
          TreeNode<char>('w', std::vector< TreeNode<char> > {
            TreeNode<char>('$')
          })
        }),
        TreeNode<char>('u', std::vector< TreeNode<char> > {
          TreeNode<char>('t', std::vector< TreeNode<char> > {
            TreeNode<char>('$')
          })
        })
      })
    })
  );
}

template <typename T>
void PrintVector(const std::vector<T> &vec) {
  std::cout << "[ ";
  for (int i = 0; i < vec.size(); i++) {
    std::cout << "\"" << vec[i] << "\" ";
  }
  std::cout << "]" << std::endl;
}

int main() {
  TreeNode<char> example = MakeExample();
  int count2 = 0;
  
   // Q1 FindNode
  TreeNode<char> end_node = FindNode(example, "acn", 0);
  if (end_node.GetValue() == 'n' && end_node.GetChildren()[0].GetValue() == 'e'){
    count2++;
  }

  TreeNode<char> end_node2 = FindNode(example, "notInTree", 0);
  if (end_node2.IsEmpty()){
    count2++;
  }

  std::cout<<"FindNode: passed "<<count2<<" of 2 basic test cases"<<endl;
  
  count2 = 0;
  
  TreeNode<char> end_node3 = FindNode(example, "cut", 0);
  if (end_node3.GetValue() == 't'){
    count2++;
  }

  TreeNode<char> end_node4 = FindNode(example, "bean", 2);
  if (end_node4.GetValue() == 'n' && end_node4.GetChildren()[0].GetValue() == 'd'){
    count2++;
  }
  
  std::cout<<"GetCandidates: passed "<<count2<<" of 2 advance test cases"<<endl;
  
  count2 = 0;
  // Q2 CollectWords
  vector<string> ans6 = {"eam", "eef", "ees"};
  vector<string> example_list = vector<string>();
  CollectWords(example.GetChildren()[1], "", example_list);
  if (ans6 == example_list){
    count2++;
  }
  example_list.clear();

  vector<string> ans7 = {"beam", "beef", "bees"};
  CollectWords(example.GetChildren()[1], "b", example_list);
  if (ans7 == example_list){
    count2++;
  }
  example_list.clear();
  vector<string> ans8 = {"mmeam", "mmeef", "mmees"};
  CollectWords(example.GetChildren()[1], "mm", example_list);
  if (ans8 == example_list){
    count2++;
  }
  example_list.clear();
  std::cout<<"CollectWords: passed "<<count2<<" of 3 basic test cases"<<endl;
  
  count2 = 0;
  vector<string> ans9 = {"ce", "cne", "nd", "ndrew"};
  CollectWords(example.GetChildren()[0], "", example_list);
  if (ans9 == example_list){
    count2++;
  }
  example_list.clear();

  vector<string> ans10 = {"long_stringce", "long_stringcne", "long_stringnd", "long_stringndrew"};
  CollectWords(example.GetChildren()[0], "long_string", example_list);
  if (ans10 == example_list){
    count2++;
  }
  example_list.clear();

  vector<string> ans11 = {"bat", "bow", "but"};
  CollectWords(example.GetChildren()[2], "b", example_list);
  if (ans11 == example_list){
    count2++;
  }
  example_list.clear();
  std::cout<<"CollectWords: passed "<<count2<<" of 3 advance test cases"<<endl;
 

  // Q3 
  count2 = 0;
  vector<string> ans = {"cat", "cow", "cut"};
  if (ans == GetCandidates(example, "c")){
    count2++;
  }
  vector<string> ans2 = {"ace", "acne", "and", "andrew", "beam", "beef", "bees", "cat", "cow", "cut"};
  if (ans2 == GetCandidates(example, "")){
    count2++;
  }
  vector<string> ans3 = {};
  if (ans3 == GetCandidates(example, "bean")){
    count2++;
  }
  std::cout<<"GetCandidates: passed "<<count2<<" of 3 basic test cases"<<endl;
  count2 = 0;

  vector<string> ans4 = {"cat"};
  if (ans4 == GetCandidates(example, "cat")){
    count2++;
  }

  vector<string> ans5 = {"ace", "acne"};
  if (ans5 == GetCandidates(example, "ac")){
    count2++;
  }
  
  if (ans3 == GetCandidates(example, " ")){
    count2++;
  }

  std::cout<<"GetCandidates: passed "<<count2<<" of 3 advance test cases"<<endl;

  return 0;
}