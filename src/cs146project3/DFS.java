package cs146project3;

public class DFS {

	private int level;
	
	
	DFS() {
		level = 0;
	}
	
	public void testDFS(Maze[][] maze) {
		dfs(maze,1,1);
	}
	
	
	private void dfs(Maze[][] maze,int row,int col) {
		// 10 -> 0, 11 -> 1, 12 -> 2
		if(level >= 10) {
			int a = level / 10;
			int b = level % (a*10);
			level = b;
		}
		maze[row][col].setSign(Integer.toString(level++)); 
		if(row == maze.length -2 && col == maze.length - 2) {
			return;
		}
		
		int[][] newCell = {{0,2},{2,0},{-2,0},{0,-2}};
		for(int[] cell : newCell) {
			int newRow = row + cell[0];
			int newCol = col + cell[1];
			if(newRow < 0 || newRow >= maze.length || newCol < 0 || newCol >= maze.length || maze[newRow][newCol].getSign() != " ") {
				continue;
			}
			if(newRow == row && newCol > col) {
				if(maze[row][col+1].getSign() == "|") continue;
			}
			if(newRow == row && newCol < col) {
				if(maze[row][col-1].getSign() == "|") continue;
			}
			if(newCol == col && newRow > row) {
				if(maze[row+1][col].getSign() == "-") continue;
			}
			if(newCol == col && newRow < row) {
				if(maze[row-1][col].getSign() == "-") continue;
			}
			dfs(maze,newRow,newCol);
			if(maze[maze.length - 2][maze.length - 2].getSign() != " ") return;
		}
		
	}
}
