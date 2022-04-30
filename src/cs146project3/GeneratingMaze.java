package cs146project3;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class GeneratingMaze {
	
	private Maze[][] maze;
	private int grid;
	private String[] sign;
	
	GeneratingMaze(int grid) {
		this.grid = grid;
		this.sign = new String[2];
		this.maze = new Maze[2*grid+1][2*grid+1];
		for(int i=0;i<maze.length;i++) {
			for(int j=0;j<maze[i].length;j++) {
				maze[i][j] = new Maze();
			}
		}
	}
	
	public Maze[][] getMaze() {
		findShortestPath();
		generatingMaze();
		putShortestPathInMaze();
		removeWalls();
		removeWallsbetweenShortestPath();
		finalizeMaze();
		
		return maze;
	}
	

	// find the shortest solution path.
	/*
	    1 0 0 0
	    1 1 0 0
	    0 1 1 0
	    0 0 1 1
	 */
	private void findShortestPath() {
		for(int i=0;i<grid;i++) {
			for(int j=0;j<grid;j++) {
				maze[i][j].setShortestPath(0);
			}
		}
		int[] currentCell = {0,0};
		Stack<int[]> stack = new Stack<>();
		
		for(int i=0;i<grid;i++) {
			for(int j=0;j<grid;j++) {
				if(maze[i][j].getShortestPath() == 0 && maze[grid-1][grid-1].getShortestPath() == 0) {
					generatingShortestPath(currentCell,stack);
				}
			}
		}
	}
	
	//generate a maze
	private void generatingMaze() {
		
		for(int i=0;i<maze.length;i++) {
			if(i == 0 || i % 2 == 0) {
				sign[0] = "+"; sign[1] = "-";
			} else {
				sign[0] = "|"; sign[1] = " ";
			}
			int index = 1;
			for(int j=0;j<maze.length;j++) {
				if((i == 0 && j == 1) || (i==2*grid && j == 2*grid -1)) {
					maze[i][j].setSign(" ");
					index *= -1;
					continue;
				}
				if(index == 1) {
					maze[i][j].setSign(sign[0]);
				} else {
					maze[i][j].setSign(sign[1]);
				}
				index *= -1;
			}
		}	
	}
	
	
	
	private void putShortestPathInMaze() {
		/*
		 
		    1 0 0 0
		    1 1 0 0
		    0 1 1 0
		    0 0 1 1    
		    	   
		   + +-+-+-+
		   |1| | | |
		   +-+-+-+-+
		   |1|1| | |
		   +-+-+-+-+
		   | |1|1| |
		   +-+-+-+-+
		   | | |1|1|
		   +-+-+-+ +
		 */
		int row=1;
		for(int i=0;i<grid;i++) {
			int col=1;
			for(int j=0;j<grid;j++) {
				if(maze[i][j].getShortestPath() == 1) {
					maze[i+row][j+col].setSign("1");
				}
				col++;
			}
			row++;
		}
		
		
	}

	
	private void removeWalls() {
		
		for(int i=0;i<maze.length;i++) {
			for(int j=0;j<maze[i].length;j++) {
				if((!(i == 0 && j == 1) && !(i ==2*grid && j == 2*grid -1)) && maze[i][j].getSign() == " ") {
					Queue<int[]> que = new LinkedList<>();
					que.add(new int[] {i,j});
					roomWalls(que);
				}
			}
		}
		
	}
	
	
	// remove the walls between one path solution.
	private void removeWallsbetweenShortestPath() {
		
		for(int i=0;i<maze.length;i++) {
			for(int j=0;j<maze.length;j++) {
				if(maze[i][j].getSign() == "1") {
					Queue<int[]> que = new LinkedList<>();
					que.add(new int[] {i,j});
					shortestPathWalls(que);
				}
			}
		}		
	}

	
	// since a perfect maze is created, this method will remove the unnecessary characters (such as "~", "X", "1")
	private void finalizeMaze() {
		for(int i=0;i<maze.length;i++) {
			for(int j=0;j<maze.length;j++) {
				if(maze[i][j].getSign() == "~" || maze[i][j].getSign() == "X" || maze[i][j].getSign() == "1") {
					maze[i][j].setSign(" ");
				}
			}
		}
	}




	private void generatingShortestPath(int[] currentCell,Stack<int[]> stack)  {
		maze[currentCell[0]][currentCell[1]].setShortestPath(1);
		if(maze[grid-1][grid-1].getShortestPath() == 1) {
			return;
		}
		int[][] newCell = {{0,1},{1,0},{-1,0},{0,-1}};
		ArrayList<int[]> neighbors = new ArrayList();
		for(int[] cell : newCell) {
			int newRow = currentCell[0] + cell[0];
			int newCol = currentCell[1] + cell[1];
			if(newRow < 0 || newRow >= grid || newCol < 0 || newCol >= grid || maze[newRow][newCol].getShortestPath() == 1) {
				continue;
			}
			maze[currentCell[0]][currentCell[1]].addNeighbors(new Maze(newRow,newCol));
		}
		if(maze[currentCell[0]][currentCell[1]].getNeighborsSize() == 0) {
			if(stack.size() > 0) {
				int[] previousCell = stack.pop();
				currentCell[0] = previousCell[0]; currentCell[1] = previousCell[1];
				generatingShortestPath(currentCell,stack);
			}
		} else {
			while (true) {
				int ran = (int)(Math.random() * maze[currentCell[0]][currentCell[1]].getNeighborsSize());
				Maze neighbor = maze[currentCell[0]][currentCell[1]].getRandomNeighbor(ran);
				if(neighbor.getRow() <= currentCell[0] && neighbor.getCol() <= currentCell[1]) {
					continue;
				}
				stack.add(currentCell);
				currentCell[0] = neighbor.getRow(); currentCell[1] = neighbor.getCol();
				generatingShortestPath(currentCell,stack);
				return;
			}
		}
	}
	

	
	private void roomWalls(Queue<int[]> que) {
		
		while(que.size() > 0) {
			int[] node = que.poll();
			int row = node[0]; int col = node[1];
			int[][] newCell = {{0,2},{2,0},{-2,0},{0,-2}};
			maze[row][col].resetNeighbors();
			maze[row][col].resetAdjacent();
			for(int[] cell : newCell) {
				int newRow = row + cell[0];
				int newCol = col + cell[1];
				if(newRow < 0 || newRow >= maze.length || newCol < 0 || newCol >= maze.length || maze[newRow][newCol].getSign() == "X") {
					continue;
				}
				if(maze[newRow][newCol].getSign() == "1") {
					maze[row][col].addAdjacents(new Maze(newRow, newCol));
					continue;
				}
				maze[row][col].addNeighbors(new Maze(newRow, newCol));
				que.add(new int[] {newRow,newCol});
			}
			// mark current position as "X" to prevent revisiting and maximum recursion depth exceeded
			maze[row][col].setSign("X");
			
			if(maze[row][col].getAdjacentsSize() > 0) {
				int ran = (int)(Math.random() * maze[row][col].getAdjacentsSize());
				Maze adjacent = maze[row][col].getRandomAdjacent(ran);
				int newRow = adjacent.getRow(); int newCol = adjacent.getCol();
				if(newRow == row && newCol > col) {
					if(maze[row][col+1].getSign() != "|") return;
					maze[row][col+1].setSign("~");
					return;
				}
				if(newRow == row && newCol < col) {
					if(maze[row][col-1].getSign() != "|") return;
					maze[row][col-1].setSign("~");
					return;
				}
				if(newCol == col && newRow > row) {
					if(maze[row+1][col].getSign() != "-") return;
					maze[row+1][col].setSign("~");
					return;
				}
				if(newCol == col && newRow < row) {
					if(maze[row-1][col].getSign() != "-") return;
					maze[row-1][col].setSign("~");
					return;
				}
			} 
			while (true) {
					int ran = (int)(Math.random() * maze[row][col].getNeighborsSize());
					Maze neighbor = maze[row][col].getRandomNeighbor(ran);
					int newRow = neighbor.getRow(); int newCol = neighbor.getCol();
					if(newRow == row && newCol > col) {
						if(maze[row][col+1].getSign() != "|") return;
						maze[row][col+1].setSign("~");
						return;
					}
					if(newRow == row && newCol < col) {
						if(maze[row][col-1].getSign() != "|") return;
						maze[row][col-1].setSign("~");
						return;
					}
					if(newCol == col && newRow > row) {
						if(maze[row+1][col].getSign() != "-") return;
						maze[row+1][col].setSign("~");
						return;
					}
					if(newCol == col && newRow < row) {
						if(maze[row-1][col].getSign() != "-") return;
						maze[row-1][col].setSign("~");
						return;
					}
			}
		}
	}
	
	
	
	private void shortestPathWalls(Queue<int[]> que) {
		
		while(que.size() > 0) {
			int[] node = que.poll();
			int row = node[0]; int col = node[1];
			int[][] newCell = {{0,2},{2,0},{-2,0},{0,-2}};
			maze[row][col].resetNeighbors();
			for(int[] cell : newCell) {
				int newRow = row + cell[0];
				int newCol = col + cell[1];
				if(newRow < 0 || newRow >= maze.length || newCol < 0 || newCol >= maze.length || maze[newRow][newCol].getSign() == " " || maze[newRow][newCol].getSign() == "X") {
					continue;
				}
				
				maze[row][col].addNeighbors(new Maze(newRow,newCol));
				if(newRow == row && newCol > col) {
					maze[row][col+1].setSign(" ");
				}
				if(newRow == row && newCol < col) {
					maze[row][col-1].setSign(" ");
				}
				if(newCol == col && newRow > row) {
					maze[row+1][col].setSign(" ");
				}
				if(newCol == col && newRow < row) {
					maze[row-1][col].setSign(" ");
				}
				que.add(new int[] {newRow,newCol});
				maze[row][col].setSign("X");
			}
			
		}
	}
	
	
	public int[][] getShortestPath() {
		int[][] shortestPath = new int[grid][grid];
		for(int i=0;i<grid;i++) {
			for(int j=0;j<grid;j++) {
				if(maze[i][j].getShortestPath() == 1) {
					shortestPath[i][j] = 1;
				} else {
					shortestPath[i][j] = 0;
				}
			}
		}
		return shortestPath;
	}
	
	public void resetMaze(Maze[][] maze) {
		for(int i=0;i<maze.length;i++) {
			for(int j=0;j<maze.length;j++) {
				if(maze[i][j].getSign() != "+" && maze[i][j].getSign() != "-" && maze[i][j].getSign() != " " && maze[i][j].getSign() != "|") {
					maze[i][j].setSign(" ");
				}
			}
		}
	}
	
	
	public void printMaze() { 
		for(int i=0;i<maze.length;i++) {
			for(int j=0;j<maze.length;j++) {
				System.out.print(maze[i][j].getSign() + " ");
			}
			System.out.println();
		}
		
	}



}
