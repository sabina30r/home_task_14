package nix.edu;

import nix.edu.algorithm.GraphUtil;
import nix.edu.algorithm.data.Edge;
import nix.edu.algorithm.data.Graph;
import nix.edu.algorithm.data.Node;
import nix.edu.connection.DbConnectionUtil;
import nix.edu.databaseIO.DbManager;
import nix.edu.model.Problem;

import java.util.List;


public class Main {

    public static void main(String[] args) {
        DbConnectionUtil.getConnection();
        Graph graph = buildGraph();
        List<Node> locations = DbManager.readLocations();
        List<Problem> problems = DbManager.readProblems();
        GraphUtil algorithm = new GraphUtil(graph);
        for (Problem problem : problems) {
            DbManager.writeData(problem.getId(),
                    algorithm.returnTheShortestPath(DbManager.getNode(locations, problem.getFromId()),
                            DbManager.getNode(locations, problem.getToId())));
        }
    }

    private static Graph buildGraph() {
        List<Node> locations = DbManager.readLocations();
        List<Edge> routes = DbManager.readRoutes(locations);
        return new Graph(locations, routes);
    }
}
