# Homework Folder
See [Canvas](https://canvas.txstate.edu/courses/2237214/assignments) homework assignments for HW deadlines.


# Run Jupyter Notebooks on your laptop

## Install Anaconda (includes python) and packages on your laptop

 * [Download](https://www.anaconda.com/products/individual#Downloads) latest Anaconda (python>3.7). 
 * Install on your local laptop using admin privileges 
 * If you have Anaconda installed, update all and check if jupyter notebook exists
  ```
  >>conda update --all --yes
  >>conda update jupyter
  ```
 *If there is no jupyter notebook 
  ```>>conda install -c conda-forge notebook (classic Notebook)```
  * Install standard packages we will use for the class (likely there if new version)
  ```>>conda install numpy scipy sklearn```
  
## Run jupyter Notebook
```>>jupyter notebook notebook.ipynb```
1. [Anaconda Tutorial](https://docs.anaconda.com/anaconda/user-guide/getting-started/)
2. [How to Run Jupyter Notebook](https://jupyter.readthedocs.io/en/latest/running.html#running)
OR start Anaconda Navigator 

# How to run Jupyter notebook on Google colab 
free, no need to install anything on your local computer. 
  1. save the .ipynb files you want to edit on your google drive
  2. Follow [Tutorial](https://towardsdatascience.com/getting-started-with-google-colab-f2fff97f594c) on how to setup, edit, and download jupyter notebook files
  3. Read [Getting Started](https://colab.research.google.com/#scrollTo=GJBs_flRovLc) page. 

# Submission Instruction
  1. Download https://git.txstate.edu/ML/204Spring/blob/main/HW/HW*.ipynb
  2. Follow instructions and fill in the blanks so that all cells compile
  3. Run all cells in HW*.ipynb and make sure there are no errors
  4. Print HW*.ipynb to pdf file - Check pdf file that all the cells are printed correctly
  5. Upload HW*.ipynb and HW*.pdf to your course assigned git repo e.g.: https://git.txstate.edu/ML/netid, before the deadline.

# Acknowledgment
Provided examples have been modified from 
* Introduction to Machine Learning with Python [repository](https://github.com/amueller/introduction_to_ml_with_python).
* Introduction to Data Science [repository](https://github.com/mdekstrand/cs533-web)
