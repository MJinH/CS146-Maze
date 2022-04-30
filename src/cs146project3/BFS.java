package cs146project3;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {
	
	
	BFS(){};
	
	public void testBFS(Maze[][] maze) {
		Queue<int[]> que = new LinkedList<>();
		que.add(new int[] {1,1});
		int level = 0;
		
		while(que.size() > 0) {
			int[] node = que.poll();
			int row = node[0]; int col = node[1];
			int[][] newCell = {{0,2},{2,0},{-2,0},{0,-2}};
			for(int[] cell : newCell) {
				int newRow = row + cell[0];
				int newCol = col + cell[1];
				if(newRow < 0 || newRow >= maze.length || newCol < 0 || newCol >= maze.length || maze[newRow][newCol].getSign() != " ") {
					continue;
				}
				if(newRow == row && newCol > col) {
					if(maze[row][col+1].getSign() != "|") que.add(new int[] {newRow,newCol});
				}
				if(newRow == row && newCol < col) {
					if(maze[row][col-1].getSign() != "|") que.add(new int[] {newRow,newCol});
				}
				if(newCol == col && newRow > row) {
					if(maze[row+1][col].getSign() != "-") que.add(new int[] {newRow,newCol});
				}
				if(newCol == col && newRow < row) {
					if(maze[row-1][col].getSign() != "-") que.add(new int[] {newRow,newCol});
				}
			}
			//  10 -> 0, 11 -> 1, 12 -> 2
			if(level >= 10) {
				int a = level / 10;
				int b = level % (a*10);
				level = b;
				maze[row][col].setSign(Integer.toString(level++));
			} else {
				maze[row][col].setSign(Integer.toString(level++));
			}
			if(row == maze.length -2 && col == maze.length - 2) break;
		}
		
	}
}
