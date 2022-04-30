# CS146-Maze

The goal of this project is to write a program that will automatically generate and solve mazes. 
Each  time you run the program, it will generate and print a new random maze and the solution. 
You will  use depth-first search (DFS) and breadth-first search (BFS).

A perfect maze is defined as a maze which has one and only one  path from any point in the maze to any other point. 
This means that the maze has no inaccessible  sections, no circular paths, no open areas. 


Each search algorithm will begin at the starting room and search for the finishing room by traversing  wall openings. The search should terminate as soon as the finishing room is found. For each search  algorithm, 
you will output the order in which rooms were visited and indicate the shortest solution  path from starting to finishing room.  

For the DFS and BFS solutions, the maze should be output twice, one for each algorithm. 
The first  maze output for each algorithm should show the order that the rooms were visited by the algorithm.  


The second maze output for each algorithm should show the shortest solution  path, using hash ‘#’ character for rooms and wall openings on the solution path.
