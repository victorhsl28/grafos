package atvd1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		Double[][] occupancy = getMatrixFromFile("map1.txt");
		int[] robot_pos_c = {9, 0}, robot_pos_d = {0, 2};
		List<int[]> path = robot_path(robot_pos_c,  robot_pos_d, occupancy);
		printPath(path);
	}
	
	public static void printPath(List<int[]> path) {
		for(int[] step : path) {
			System.out.println("[" + step[0] + ", " + step[1] + "]");
		}
	}
	
	public static List<int[]> robot_path(int[] robot_pos, int[] robot_pos_d, Double[][] occupancy) {
		List<int[]> path = new ArrayList<>();
		
		List<int[]> read = new LinkedList<>();
		List<int[]> unread = new LinkedList<>();
		
		unread.add(robot_pos);
		
		while(!isSolution(unread.get(0), robot_pos_d)) {
			List<int[]> childs = generateChilds(unread.get(0), occupancy.length, occupancy.length);
			read.add(unread.get(0));
			path.add(unread.get(0));
			unread.remove(unread.get(0));
			int[] bestChild = null;
			Double shortestDistance = Double.MAX_VALUE;
			for(int[] child : childs) {
				if(!read.contains(child)) {
					if(occupancy[child[0]][child[1]] < shortestDistance) {
						bestChild = child;
						shortestDistance = occupancy[child[0]][child[1]];
					}
				}
			}
			unread.add(bestChild);
		}
		
		return path;
	}
	
	public static boolean isSolution(int[] pos, int[] robot_pos_d) {
		if(pos[0] == robot_pos_d[0] &&  pos[1] == robot_pos_d[1]) {
			return true;
		}
		return false;
	}
	
	public static List<int[]> generateChilds(int[] pos, int climit, int llimit) {
		List<int[]> childs = new LinkedList<>();
		int l = pos[0], c = pos[1];
		//left child
		if(c - 1 > 0) {
			int[] leftChild = {l, c - 1};
			childs.add(leftChild);
		}
		//above child
		if(l - 1 >= 0) {
			int[] aboveChild = {l - 1, c};
			childs.add(aboveChild);
		}
		//right child
		if(c + 1 < climit) {
			int[] rightChild = {l, c + 1};
			childs.add(rightChild);
		}
		//below child
		if(l + 1 < llimit) {
			int[] belowChild = {l + 1, c};
			childs.add(belowChild);
		}
		return childs;
	}
	
	public static Double[][] getMatrixFromFile(String path) { //get double matrix
		String textMap = "";
		int llen = 0, clen = 0;
		Double[][] map = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			for(String line; (line = br.readLine()) != null; textMap += line); //write the file into textMap as a String
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		textMap = textMap.substring( 1, textMap.length() - 1 ); //remove first and last '[' ']'
		textMap = textMap.trim().replaceAll(" +", " "); //remove large spaces
		//separate with only ';'
		textMap = textMap.replace("[ ", "").replace("[", "").replace(" ]", ";").replace("]", ";").replace("; ", ";");
		textMap = textMap.substring(0, textMap.length() - 1 ); //remove last ';'
		
		String[] lines = textMap.split(";");
		llen = lines.length;
		clen = (lines[0].split(" ")).length;
		map = new Double[llen][clen];
		int l = 0;
		for(String line : lines) {
			int c = 0;
			for(String colum : line.split(" ")) {
				if(colum.equalsIgnoreCase("inf")) {
					map[l][c] = Double.MAX_VALUE;
				} else {
					map[l][c] = Double.valueOf(colum);
				}
				c++;
			}
			l++;
		}
				
		return map;
	}

}
