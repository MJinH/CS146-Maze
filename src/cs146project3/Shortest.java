package cs146project3;

public class Shortest {

	
	Shortest(){};
	
	public void shortestPath(Maze[][] maze, int[][] shorestPath) {
		int row=1;
		for(int i=0;i<shorestPath.length;i++) {
			int col=1;
			for(int j=0;j<shorestPath.length;j++) {
				if(shorestPath[i][j] == 1) {
					maze[i+row][j+col].setSign("#");
				}
				col++;
			}
			row++;
		}
		
	}
}
