package nix.edu.databaseio;

import nix.edu.algorithm.data.Edge;
import nix.edu.algorithm.data.Node;
import nix.edu.model.Location;
import nix.edu.model.Problem;
import nix.edu.model.Route;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DbManager {

    private Connection connection;

    public DbManager(Connection connection) {
        this.connection = connection;
    }

    public List<Node> readLocations() {
        List<Node> locationsList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT id, name FROM locations;")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Location location = new Location();
                location.setId(resultSet.getInt("id"));
                location.setName(resultSet.getString("name"));
                Node newNode = new Node(location.getId(), location.getName());
                locationsList.add(newNode);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locationsList;
    }

    public List<Edge> readRoutes(List<Node> locations) {
        List<Edge> edges = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT id, from_id, to_id, cost  FROM routes;")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Route route = new Route();
                route.setId(resultSet.getInt("id"));
                route.setFromId(resultSet.getInt("from_id"));
                route.setToId(resultSet.getInt("to_id"));
                route.setCost(resultSet.getInt("cost"));

                edges.add(new Edge(route.getId(), getNode(locations, route.getFromId()), getNode(locations, route.getToId()), route.getCost()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return edges;
    }

    public List<Problem> readProblems() {
        List<Problem> problems = new ArrayList<>();
        try (PreparedStatement statement = connection.
                prepareStatement("SELECT id, from_id, to_id FROM problems " +
                        "LEFT JOIN solutions ON problems.id = solutions.problem_id WHERE solutions.problem_id IS NULL;")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Problem problem = new Problem();
                problem.setId(resultSet.getInt("id"));
                problem.setFromId(resultSet.getInt("from_id"));
                problem.setToId(resultSet.getInt("to_id"));
                problems.add(new Problem(problem.getId(), problem.getFromId(), problem.getToId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return problems;
    }

    public Node getNode(List<Node> locationsList, int id) {
        for (Node node : locationsList) {
            if (Integer.valueOf(node.getId()).equals(id)) {
                return node;
            }
        }
        return null;
    }

    public void writeData(int id, int cost) {
        try (PreparedStatement statement = connection.
                prepareStatement("INSERT INTO solutions (problem_id, cost) VALUES (?,?);")) {
            statement.setInt(1, id);
            statement.setInt(2, cost);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
