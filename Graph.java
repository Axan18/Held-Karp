import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<String, Map<String,Integer>> edges = new HashMap<>(); //matrix of vertices and their weights
    private List<Vertex> vertices = new ArrayList<>(); //list of vertices

    public void addEdge(String start, String end, int weight) {
        edges.putIfAbsent(start, new HashMap<>());
        edges.get(start).put(end, weight);
    }
    public void addVertex(String label) {
        if(vertices.stream().anyMatch(v -> v.label.equals(label))) {
            return;
        }
        vertices.add(new Vertex(label));
    }
    public Integer getWeight(String start, String end) {
        if(edges.get(start).containsKey(end)) {
            return edges.get(start).get(end);
        }
        else if (edges.get(end).containsKey(start)) {
            return edges.get(end).get(start);
        }
        else {
            return 0;
        }
    }
    public void printGraph() {
        System.out.println("Edghes number: " + edges.size());
        for(String s: edges.keySet()) {
            for(String e: edges.get(s).keySet()) {
                System.out.println(s + " -> " + e + " (" + edges.get(s).get(e) + ")");
            }
        }
    }
    public List<Vertex> getVertices() {
        return vertices;
    }

}
