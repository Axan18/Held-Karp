import java.util.*;

public class Main {
    private static String startingCity;
    private static int numberOfVertices;
    public static void main(String[] args) {
        Graph graph = new Graph();
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> input = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().strip();
            if (line.equals("")) break; // Zakończenie wczytywania po pustej linii
            input.add(line);
        }
        numberOfVertices = Integer.parseInt(input.get(0).split(" ")[0]);
        startingCity = input.get(0).split(" ")[1];
        input.remove(0);
        int index = 0;
        for(int i = 0; i<numberOfVertices-1;i++)
        {
            String[] edge = null;
            for(int j=0; j<numberOfVertices-i-1; j++ )
            {
                edge = input.get(index).split(" ");
                if(edge.length==4)
                {
                    if(Objects.equals(edge[0], "Gorzów")) {
                        graph.addEdge(edge[0]+" "+edge[1], edge[2], Integer.parseInt(edge[3]));
                        graph.addVertex(edge[0]+" "+edge[1]);
                    }
                    else {
                        graph.addEdge(edge[0], edge[1] + " " + edge[2], Integer.parseInt(edge[3]));
                        graph.addVertex(edge[1] + " " + edge[2]);
                    }
                    index++;
                    continue;
                }
                graph.addEdge(edge[0], edge[1], Integer.parseInt(edge[2]));
                index++;
                graph.addVertex(edge[0]);
                graph.addVertex(edge[1]);
            }
        }
        scanner.close();
        HeldKarp(graph);
    }
    public static int HeldKarp(Graph graph)
    {
        Map<Integer, Map<String, Integer>> g = new HashMap<>();
        for(Vertex v: graph.getVertices())
        {
            if(v.label.equals(startingCity)) continue;
            Map<String,Integer> g1 = new HashMap<>();
            g1.put(v.label, graph.getWeight(startingCity, v.label));
            g.put(1, g1);
        }
        Map<String,Map<String,Integer>> g2 = new HashMap<>();
        Set<Set<Vertex>> powerSet = powerSet(new HashSet<>(graph.getVertices()));
        Map<Integer,Set<Vertex>> setsBySizes= new HashMap<>();

        for(Set<Vertex> set: powerSet)
        {
            if(set.size()<2) continue;
            setsBySizes.put(set.size(), set);
        }
        for(int i=2; i<=graph.getVertices().size(); i++)
        {
            for(Set<Vertex> set: setsBySizes.values())
            {
                if(set.size()!=i) continue;
                for(Vertex v: set)
                {
                    if(v.label.equals(startingCity)) continue;
                    Map<String,Integer> g2v = new HashMap<>();
                    for(Vertex u: set)
                    {
                        if(u.label.equals(v.label)) continue;
                        g2v.put(u.label, graph.getWeight(u.label, v.label)+g1.get(u.label));
                    }
                    g2.put(v.label, g2v);
                }
            }
        }

        return 0;
    }
    public static Set<Set<Vertex>> powerSet(Set<Vertex> originalSet) {
        Set<Set<Vertex>> sets = new HashSet<>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<>());
            return sets;
        }
        List<Vertex> list = new ArrayList<>(originalSet);
        Vertex head = list.get(0);
        Set<Vertex> rest = new HashSet<>(list.subList(1, list.size()));
        for (Set<Vertex> set : powerSet(rest)) {
            Set<Vertex> newSet = new HashSet<>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }
}
