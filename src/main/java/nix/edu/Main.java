package nix.edu;

import nix.edu.algorithm.GraphUtil;
import nix.edu.algorithm.data.Edge;
import nix.edu.algorithm.data.Graph;
import nix.edu.algorithm.data.Node;
import nix.edu.connection.DbConnectionUtil;
import nix.edu.databaseio.DbManager;
import nix.edu.model.Problem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        try (Connection connection = DbConnectionUtil.getConnection()) {
            DbManager dbManager = new DbManager(connection);
            Graph graph = buildGraph();
            List<Node> locations = dbManager.readLocations();
            List<Problem> problems = dbManager.readProblems();
            GraphUtil algorithm = new GraphUtil(graph);
            for (Problem problem : problems) {
                dbManager.writeData(problem.getId(),
                        algorithm.returnTheShortestPath(dbManager.getNode(locations, problem.getFromId()),
                                dbManager.getNode(locations, problem.getToId())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Graph buildGraph() {
        DbManager dbManager = new DbManager(DbConnectionUtil.getConnection());
        List<Node> locations = dbManager.readLocations();
        List<Edge> routes = dbManager.readRoutes(locations);
        return new Graph(locations, routes);
    }
}
