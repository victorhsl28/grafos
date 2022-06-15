package problema2;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class ShortestPath {
	
	private Graph g;
	private PriorityQueue<Vertex> queue;
	private Set<Integer> settled;
	private int[] dist;
	
	public ShortestPath(Graph g, int source) {
		this.g = g;
		int size = g.getVertexes().keySet().size();
		dist = new int[size];
		queue = new PriorityQueue<Vertex>(size, new Vertex());
		settled = new HashSet<Integer>();
		dijkstra(g, source, size);
	}
	
	public void dijkstra(Graph g, int source, int size) {	
		for(int id = 0; id < size; id++) 
			dist[id] = Integer.MAX_VALUE;
		
		dist[source] = 0;
		queue.add(g.getVertexes().get(source));
		
		while(!queue.isEmpty()) {	
			int u = queue.poll().getId();
			if (!g.isDirected() && !settled.contains(u)) {
				settled.add(u);
			}
			generateChilds(u, g.isDirected());
		}
		
		for(int i = 0; i < dist.length; i++) {
            Main.d[source][i] = dist[i];
		}
	}
	
	private void generateChilds(int u, boolean directed) {
		for(Connection c : g.getVertexes().get(u).getConnections()) {
			int id = c.getVertexFinal().getId();
			int distance = dist[u] + c.getCost();
			if (distance < dist[id]) {
				dist[id] = distance;
				queue.add(c.getVertexFinal());
			}
		}
	}

	public Graph getG() {
		return g;
	}

	public void setG(Graph g) {
		this.g = g;
	}
	
}
