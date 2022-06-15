package problema2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph {
	
	private Map<Integer, Vertex> vertexes;
	private boolean directed;
	
	public Graph(String path, boolean directed) {
		this.vertexes = new HashMap<>();
		this.directed = directed;
		readFile(path);
	}
		
	private void readFile(String path) {
		System.out.println("[1] Reading file...");
		boolean firstLine = true;
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			for(String line; (line = br.readLine()) != null; ) {
				if(firstLine) { //setup the vertexes
					firstLine = false;
					int vertexesQuantity = Integer.valueOf(line.split(" ")[0]);
					for(int i = 0; i < vertexesQuantity; i++) {
						vertexes.put(i, new Vertex(i, 0));
					}
				} else { //setup connections
					String[] colums = line.split(" ");
					Vertex vertexInitial = vertexes.get(Integer.valueOf(colums[0]) - 1), vertexFinal = vertexes.get(Integer.valueOf(colums[1]) - 1);
					int cost = Integer.valueOf(colums[2]);
					
					if(directed) { //if the vertex is directioned, connect only once
						connect(vertexInitial, vertexFinal, cost);
					} else { //if not, connect both vertexes
						connect(vertexInitial, vertexFinal, cost);
						connect(vertexFinal, vertexInitial, cost);
					}
				}				
		    }
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void connect(Vertex vertexInitial, Vertex vertexFinal, int cost) {
		vertexInitial.getConnections().add(new Connection(vertexFinal, cost));
	}
	
	public Map<Integer, Vertex> getVertexes() {
		return vertexes;
	}

	public void setVertexes(Map<Integer, Vertex> vertexes) {
		this.vertexes = vertexes;
	}

	public boolean isDirected() {
		return directed;
	}

	public void setDirected(boolean directed) {
		this.directed = directed;
	}
	
}

class Vertex implements Comparator<Vertex> {
	
	private int id, cost;
	private List<Connection> connections;
	
	public Vertex(){};
	
	public Vertex(int id, int cost) {
		this.id = id;
		this.cost = cost;
		this.connections = new LinkedList<>();
	}
	
	public List<Connection> getConnections() {
		return connections;
	}
	
	public void setConnections(List<Connection> connections) {
		this.connections = connections;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	@Override
	public int compare(Vertex V1, Vertex V2) {
 
        if (V1.cost < V2.cost)
            return -1;
 
        if (V1.cost > V2.cost)
            return 1;
 
        return 0;
    }
}

class Connection {
	
	private Vertex vertexFinal;
	private int cost;
	
	public Connection(Vertex vertexFinal, int cost) {
		this.vertexFinal = vertexFinal;
		this.cost = cost;
	}

	public Vertex getVertexFinal() {
		return vertexFinal;
	}

	public void setVertexFinal(Vertex vertexFinal) {
		this.vertexFinal = vertexFinal;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

}
