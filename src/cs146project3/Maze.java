package cs146project3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Maze {
	private Integer row;
	private Integer col;
	private Integer shortestPath;
	private String sign;
	private ArrayList<Maze> neighbors;
	private ArrayList<Maze> adjacents;
	
	public Maze() {
		row = null;
		shortestPath = null;
		sign = null;
		neighbors = new ArrayList<>();
		adjacents = new ArrayList<>();
	}
	
	public Maze(Integer row, Integer col) {
		this.row = row;
		this.col = col;
	}
	
	public Integer getRow() {
		return this.row;
	}
	
	public Integer getCol() {
		return this.col;
	}
	
	
	public void setShortestPath(Integer n) {
		this.shortestPath = n;
	}
	
	public Integer getShortestPath() {
		return this.shortestPath;
	}
	
	public void setSign(String s) {
		this.sign = s;
	}
	
	public String getSign() {
		return this.sign;
	}
	
	public void addNeighbors(Maze neighbor) {
		this.neighbors.add(neighbor);
	}
	
	public void addAdjacents(Maze adjacent) {
		this.adjacents.add(adjacent);
	}
	
	public int getNeighborsSize() {
		return this.neighbors.size();
	}
	
	public int getAdjacentsSize() {
		return this.adjacents.size();
	}
	
	public Maze getRandomNeighbor(int rand) {
		return this.neighbors.get(rand);
	}
	
	public Maze getRandomAdjacent(int rand) {
		return this.adjacents.get(rand);
	}
	
	public void resetNeighbors() {
		this.neighbors = new ArrayList<>();
	}
	
	public void resetAdjacent() {
		this.adjacents = new ArrayList<>();
	}
}
