# TCP Server - Client
JAVA 

A server that can perform different algorithmic operations depending on the type of TASK received, 
the handling of clients on the server may be performed in concurrently.
Each problem is a task that the facilitator has to perform and return the result of the calculation to the customer 
The specific problems will be transferred on the SOCKET client to the server and
each request will be handled in a separate THREAD using a concrete HANDLER

Task 1 - Find all the achievable indices groups including diagonals 
input : 2D array of INT or INTEGER 
* The matrix is binary - if there is an index value of 1 it means that there is a side between the indexes in the matrix
A value of 0 indicates that there is no side between them
output: List of All Index Groups Achievements are sorted by number of indices and without duplicates


Task 2 - Finding more routes from a source index to a target index
input : 2D array up to size 50X50 , source index , target index 

* The matrix is binary - if there is an index value of 1 it means that there is a side between the indexes in the matrix
A value of 0 indicates that there is no side between them

The matrix can be relied upon to be square
output : List of the easiest routes from the source index to the destination index
There can be several easiest routes and not just one 
For this task alone we will use one THREAD

Task 3 - submarine game 
 input : 2D array of INT or INTEGER 
output : The number of proper submarines on the game board 
rules :
1. Submarines can be two sequences vertically
2. Submarines could be two sequences horizontally
3. There cannot be two ones diagonally unless for both the previous sections are met
4. The minimum distance between two submarines is one slot

Task 4 - 
