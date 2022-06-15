package problema2;

public class Main {
	
	public static int[][] d;

	public static void main(String[] args) {
		execute("grafo01.txt", false);
		System.out.println();
		execute("grafo02.txt", true);
	}
	
	public static void execute(String path, boolean directed) {
		Graph g = new Graph(path, directed);
		int size = g.getVertexes().keySet().size();
		d = new int[size][size];
		
		System.out.println("-> Graph: " + path);
		System.out.println("-> Directed: " + g.isDirected());
		
		//computing shortest path ====================================
		System.out.println("\n[2] Computing shortest path...");
		for(int i = 0; i < g.getVertexes().keySet().size(); i++) {
			new ShortestPath(g, i);
		}
		
		//print table d ==============================================
		for(int i = 1; i <= size; i++) {
			if(i == 1) {
				System.out.print("   01");
			} else {
				System.out.print(String.format(" %02d", (i)));
			}
		}
		
		System.out.println();
		
		for(int i = 0; i < size; i++) {
			System.out.print(String.format("%02d", (i + 1)) + "|");
			for(int j = 0; j < size; j++) {
				System.out.print(String.format("%02d", d[i][j]) + " ");
			}
			System.out.println();
		}
		
		//Calculating vectors ======================================
		System.out.println("\n[3] Calculating distance vectors...");
		
		System.out.println("Cost sum vector:");
		int[] distVec = DistanceVectors.getDistVec(d, size);
		for(int i = 0; i < distVec.length; i++) {
			if(i == distVec.length - 1) {
				System.out.print(distVec[i] + "]\n");
			} else if(i == 0){
				System.out.print("[" + distVec[i] + ", ");
			} else {
				System.out.print(distVec[i] + ", ");
			}
		}
		
		System.out.println("Further vertex vector:");
		int[] maxVec = DistanceVectors.getMaxVec(d, size);
		for(int i = 0; i < maxVec.length; i++) {
			if(i == maxVec.length - 1) {
				System.out.print(maxVec[i] + "]");
			} else if(i == 0){
				System.out.print("[" + maxVec[i] + ", ");
			} else {
				System.out.print(maxVec[i] + ", ");
			}
		}
		
		//Defining central station ==================================
		System.out.println("\n\n[4] Defining central station...");
		int centralStation = CentralStation.getCentralStation(distVec, maxVec);
		System.out.println("Central station: " + centralStation);
	}

}
