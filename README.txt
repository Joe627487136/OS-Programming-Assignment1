/* Programming Assignment 1 
 * Authors : Zhou Xuexuan (1001603)
 * Date: 08/03/2017 */

######## Purpose of program ########

The purpose of this program is to construct a directed acyclic graph (DAG) of 
user programs from an input text file. The user will then traverse through this 
DAG and execute the processes which have control and data dependencies between
each other.



######## How to compile your program ########

1) Download the code and unzip

2) In ProcessManagement.java change the "currentDirectory" to your working directory

3) U can try some tst cases:
These test cases include:
graph-file.txt
graph-file1.txt
testproc.txt


4) Run ProcessManagement.java for your own purpose:
  1. Create your "instructionSet" file
  2. Put your each line follow in following format:
     <program name with arguments :list of children ID's : input file : output file>
     eg. cat ATaleOfTwoCities.txt:2 3 4 5:stdin:outentirebook.txt
  3. Remember to rename your "instructionSet" File in ProcessManagement.java

  
  

######## What exactly does the program do? ########

1) Parse user file and generate ProcessGraph

2) Display ProcessGraph

3) Recusively check nodes if its execution finished or not

4) Execute the code with a runnable state.

5) Get your work done!



