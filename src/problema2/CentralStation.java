package problema2;

import java.util.LinkedList;
import java.util.List;

public class CentralStation {
	
	public static int getCentralStation(int[] distVec, int[] maxVec) {
		int maxDist = getLower(distVec);
		List<String> line = new LinkedList<>();
		for(int i = 0; i < distVec.length; i++) {
			if(distVec[i] == maxDist) {
				line.add(String.valueOf(i));
			}
		}
		
		if(line.size() == 1) {
			return (Integer.valueOf(line.get(0)) + 1);
		} else {
			int maxVecc = getLower(maxVec);
			for(String s : line) {
				if(maxVec[Integer.valueOf(s)] == maxVecc)
					return (Integer.valueOf(s) + 1);
			}
		}
		return -1;
	}
	
	private static int getLower(int[] vector) {
		int lower = Integer.MAX_VALUE;
		for(int i = 0; i < vector.length; i++) {
			if(vector[i] < lower) {
				lower = vector[i];
			}
		}
		return lower;
	}

}
