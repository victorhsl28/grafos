package problema2;

public class DistanceVectors {
	
	//Lendo coluna por coluna
	/*public static int[] getDistVec(int[][] d, int size) {
		int[] distVec = new int[size];
		for(int col = 0; col < size; col++) {
			int sum = 0;
			for(int lin = 0; lin < size; lin++) {
				sum += d[lin][col];
			}
			distVec[col] = sum;
		}
		return distVec;
	}
	
	public static int[] getMaxVec(int[][] d, int size) {
		int[] maxVec = new int[size];
		for(int col = 0; col < size; col++) {
			int higher = Integer.MIN_VALUE;
			for(int lin = 0; lin < size; lin++) {
				if(d[lin][col] > higher)
					higher = d[lin][col];
			}
			maxVec[col] = higher;
		}
		return maxVec;
	}*/
	
	//Lendo linha por linha
	public static int[] getDistVec(int[][] d, int size) {
		int[] distVec = new int[size];
		for(int lin = 0; lin < size; lin++) {
			int sum = 0;
			for(int col = 0; col < size; col++) {
				sum += d[lin][col];
			}
			distVec[lin] = sum;
		}
		return distVec;
	}
	
	public static int[] getMaxVec(int[][] d, int size) {
		int[] maxVec = new int[size];
		for(int lin = 0; lin < size; lin++) {
			int higher = Integer.MIN_VALUE;
			for(int col = 0; col < size; col++) {
				if(d[lin][col] > higher)
					higher = d[lin][col];
			}
			maxVec[lin] = higher;
		}
		return maxVec;
	}
	
	

}
