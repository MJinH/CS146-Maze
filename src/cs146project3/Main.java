package cs146project3;

import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		
		int grid = 6;
		GeneratingMaze maze = new GeneratingMaze(grid); 
		
		Maze[][] testMaze = maze.getMaze();
		int[][] shortestPath = maze.getShortestPath();
		Shortest shortest = new Shortest();
		BFS bfs = new BFS();
		bfs.testBFS(testMaze);
		maze.printMaze();
		
		// reset maze
		maze.resetMaze(testMaze);
		shortest.shortestPath(testMaze, shortestPath);
		
		System.out.println("================================");
		System.out.println("================================");
		System.out.println("================================");
		maze.printMaze();
	
		// reset maze
		maze.resetMaze(testMaze);
		DFS dfs = new DFS();
		dfs.testDFS(testMaze);
		
		System.out.println("================================");
		System.out.println("================================");
		System.out.println("================================");
		
		maze.printMaze();
		
		// reset maze
		maze.resetMaze(testMaze);
		
		shortest.shortestPath(testMaze, shortestPath);
		
		System.out.println("================================");
		System.out.println("================================");
		System.out.println("================================");
		
		maze.printMaze();
		
	}

}
