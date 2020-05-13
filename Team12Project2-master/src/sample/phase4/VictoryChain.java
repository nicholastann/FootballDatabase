package sample.phase4;

import javafx.scene.control.Alert;
import sample.HelperFunctions;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class VictoryChain {
    public static void findVictoryChain(Connection conn, String teamA, String teamB, boolean export, boolean displayHere, String fileName) {
        String teamAID = null;
        String teamBID = null;
        String query = "select \"teamID\" from \"Team\" where \"school\" = '" + teamA + "' limit 1;";

        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                String id = result.getString("teamID");
                teamAID = id;
            }
        } catch (Exception e) {
            System.out.println("Error accessing Database.");
        }

        query = "select \"teamID\" from \"Team\" where \"school\" = '" + teamB + "' limit 1;";

        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                String id = result.getString("teamID");
                teamBID = id;
            }
        } catch (Exception e) {
            System.out.println("Error accessing Database.");
        }

        HashMap<String, ArrayList<Game>> mappings = new HashMap<>();

        Graph graph = new Graph(3000);

        query = "select \"gameID\", school, \"TeamGameStatistic\".\"teamID\", \"points\", \"TeamGameStatistic\".year from" +
                " \"TeamGameStatistic\", \"Team\" where \"Team\".\"teamID\" = \"TeamGameStatistic\".\"teamID\" and \"TeamGameStatistic\".year = \"Team\".year;";

        Game team1Game = null;
        Game team2Game = null;

        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);
            int id = 0;
            while (result.next()) {
                String gameID = result.getString("gameID");
                String teamID = result.getString("teamID");
                String points = result.getString("points");
                String year = result.getString("year");
                String school = result.getString("school");
                Game game = new Game(id, gameID, teamID, points, year, school);
                if (gameID == null) {
                    continue;
                }
                if (teamID.equalsIgnoreCase(teamAID)) {
                    team1Game = game;
                }
                if (teamID.equalsIgnoreCase(teamBID)) {
                    team2Game = game;
                }
                if (!mappings.containsKey(gameID)) {
                    mappings.put(gameID, new ArrayList<>());
                }
                mappings.get(gameID).add(game);
                id++;
            }
        } catch (Exception e) {
            System.out.println("Error accessing Database.");
        }

        for (Map.Entry<String, ArrayList<Game>> entry : mappings.entrySet()) {
            ArrayList <Game> game = entry.getValue();
            for (int i = 0; i < game.size() - 1; i++) {
                if (Integer.parseInt(game.get(i).points) > Integer.parseInt(game.get(i + 1).points)) {
                    addEdge(graph, game.get(i), game.get(i + 1));
                } else {
                    addEdge(graph, game.get(i + 1), game.get(i));
                }
            }
        }

        int team1ID = 1;
        int team2ID = -1;

        try {
            team1ID = Integer.parseInt(teamAID);
            team2ID = Integer.parseInt(teamBID);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Team name invalid.");
            alert.showAndWait();
            return;
        }

        for (Game game : graph.adjListArray[team1ID]) {
            if (game.teamID.equalsIgnoreCase(Integer.toString(team2ID))) {
                String winningTeam = teamA;
                String losingTeam = game.school;
                String toWrite = winningTeam + " beat " + losingTeam + " in " + game.year + "!";
                if (displayHere) {
                    HelperFunctions.showResults(toWrite);
                }
                if (export) {
                    HelperFunctions.writeToFile(toWrite, fileName);
                }
                return;
            }
        }

        LinkedList<Game> teamAWins = graph.adjListArray[team1ID];
        ArrayList<Game> teamBLosers = new ArrayList<>();

        for (Map.Entry<String, ArrayList<Game>> entry : mappings.entrySet()) {
            ArrayList <Game> game = entry.getValue();

            for (int i = 0; i < game.size() - 1; i++) {
                Game team1 = game.get(i);
                Game team2 = game.get(i + 1);

                if (team1.teamID.equalsIgnoreCase(teamBID) || team2.teamID.equalsIgnoreCase(teamBID)) {
                    if (team1.teamID.equalsIgnoreCase(teamBID) && (Integer.parseInt(team2.points) > Integer.parseInt(team1.points))) {
                        teamBLosers.add(team2);
                    } else if (team2.teamID.equalsIgnoreCase(teamBID) && (Integer.parseInt(team1.points) > Integer.parseInt(team2.points))) {
                        teamBLosers.add(team1);
                    }
                }
            }
        }

        for (int i = 0; i < teamAWins.size(); i++) {
           String toWrite = teamA + " beat " + teamAWins.get(i).school + " in " + teamAWins.get(i).year + "\n";
           toWrite += teamAWins.get(i).school + " beat ";
            for (int j = i; j < teamBLosers.size(); j++) {
                for (Game game : graph.adjListArray[Integer.parseInt(teamAWins.get(i).teamID)]) {
                    if (game.teamID.equalsIgnoreCase(teamBLosers.get(j).teamID)) {
                        toWrite += teamBLosers.get(j).school + " in " + game.year + "\n";
                        toWrite += teamBLosers.get(j).school + " beat " + teamB + " in " + teamBLosers.get(j).year + "\n";
                        if (displayHere) {
                            HelperFunctions.showResults(toWrite);
                        }
                        if (export) {
                            HelperFunctions.writeToFile(toWrite, fileName);
                        }
                        return;
                    }
                }
            }
        }

        boolean[] discovered = new boolean[15000];
        LinkedList<Game> path = new LinkedList<>();
        if (!isTherePath(graph, team1Game, team2Game, discovered, path)) {
            String toWrite = "No victory chain found!";
            if (displayHere) {
                HelperFunctions.showResults(toWrite);
            }

            if (export) {
                HelperFunctions.writeToFile(toWrite, fileName);
            }
            return;
        }

        String toWriteLongVictoryChain = "";
        for (int i = 0; i < path.size() - 1; i++) {
            String year = path.get(i).year;
            String winningTeamName = path.get(i).school;
            String losingTeamName = path.get(i + 1).school;
            toWriteLongVictoryChain += winningTeamName + " beat " + losingTeamName + " in " + year + "\n";
        }

        if (displayHere) {
            HelperFunctions.showResults(toWriteLongVictoryChain);
        }

        if (export) {
            HelperFunctions.writeToFile(toWriteLongVictoryChain, fileName);
        }
    }

    static class Game {
        String gameID = "";
        String teamID = "";
        String points = "";
        String year = "";
        String school = "";
        int id = -1;

        Game(int id, String gameID, String teamID, String points, String year, String school) {
            this.gameID = gameID;
            this.teamID = teamID;
            this.points = points;
            this.year = year;
            this.school = school;
            this.id = id;
        }
    }

    // This function was made with the help of https://www.techiedelight.com/find-path-between-vertices-directed-graph/
    private static boolean isTherePath(Graph graph, Game src, Game dest, boolean[] discovered, LinkedList<Game> path) {
        discovered[src.id] = true;
        path.add(src);

        if (src.teamID.equalsIgnoreCase(dest.teamID)) {
            return true;
        }

        for (Game i : graph.adjListArray[Integer.parseInt(src.teamID)]) {
            if (!discovered[i.id]) {
                if (isTherePath(graph, i, dest, discovered, path)) {
                    return true;
                }
            }
        }
        path.removeLast();
        return false;
    }

    // The graph structure was created with the use of https://www.geeksforgeeks.org/graph-and-its-representations/
    static class Graph {
        int nodes;
        LinkedList<Game> adjListArray[];

        Graph(int nodes) {
            this.nodes = nodes;
            adjListArray = new LinkedList[nodes];
            for(int i = 0; i < nodes ; i++) {
                adjListArray[i] = new LinkedList<>();
            }
        }
    }
    static void addEdge(Graph graph, Game src, Game dest) {
        graph.adjListArray[Integer.parseInt(src.teamID)].add(dest);
    }

}
