package nix.edu.algorithm;

import nix.edu.algorithm.data.Edge;
import nix.edu.algorithm.data.Graph;
import nix.edu.algorithm.data.Node;

import java.util.*;

public class GraphUtil {
    private final List<Node> nodes;
    private final List<Edge> edges;
    private Set<Node> settledNodes = new HashSet<>();
    private Set<Node> unSettledNodes = new HashSet<>();
    private Map<Node, Node> predecessors = new HashMap<>();
    private Map<Node, Integer> distance = new HashMap<>();

    public GraphUtil(Graph graph) {
        this.nodes = new ArrayList<>(graph.getNodes());
        this.edges = new ArrayList<>(graph.getEdges());
    }

    private  void execute(Node source) {
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Node node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Node node) {
        List<Node> adjacentNodes = getNeighbors(node);
        for (Node target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private int getDistance(Node node, Node target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException();
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private Node getMinimum(Set<Node> vertexes) {
        Node minimum = null;
        for (Node vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Node vertex) {
        return settledNodes.contains(vertex);
    }

    private  int getShortestDistance(Node destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    public LinkedList<Node> getPath(Node target) {
        LinkedList<Node> path = new LinkedList<>();
        Node step = target;
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        Collections.reverse(path);
        return path;
    }

    public  int returnTheShortestPath(Node from, Node to){
        execute(from);
        return getShortestDistance(to);
    }
}
