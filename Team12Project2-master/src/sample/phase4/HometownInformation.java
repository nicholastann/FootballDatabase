package sample.phase4;

import sample.HelperFunctions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HometownInformation {
    public static void findBasedOnPlayers(Connection conn, boolean export, boolean displayHere, String fileName) {
        String query = "select \"hometownID\" from \"Player\";";
        HashMap<String, Integer> occurrences = new HashMap<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                String hometownID = result.getString("hometownID");
                if (hometownID == null) {
                    continue;
                }
                if (occurrences.containsKey(hometownID)) {
                    int newValue = occurrences.get(hometownID) + 1;
                    occurrences.put(hometownID, newValue);
                } else {
                    occurrences.put(hometownID, 1);
                }
            }
        } catch (Exception e) {
            System.out.println("Error accessing Database.");
        }
        int max = 0;
        for (Map.Entry<String, Integer> entry : occurrences.entrySet()) {
            int value = entry.getValue();
            if (value > max) {
                max = value;
            }
        }
        ArrayList<String> allHometowns = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : occurrences.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            if (value == max) {
                allHometowns.add(key);
            }
        }

        String city = null;
        String state = null;
        String toWrite = "City" + "\t" + "State" + "\t" + "Count" + "\n";
        toWrite += "------------------------------------------------------------------------------------------\n";
        for (int i = 0; i < allHometowns.size(); i++) {
            query = "select city, state from \"Location\" where \"locationID\" = " + allHometowns.get(i) + ";";
            try {
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery(query);
                while (result.next()) {
                    city = result.getString("city");
                    state = result.getString("state");
                }
            } catch (Exception e) {
                System.out.println("Error accessing Database.");
            }
            toWrite += city + "\t" + state + "\t" + max + "\n";
        }

        if (displayHere) {
            HelperFunctions.showResults(toWrite);
        }

        if (export) {
            HelperFunctions.writeToFile(toWrite, fileName);
        }
    }

    public static void findBasedOnWinners(Connection conn, boolean export, boolean displayHere, String fileName) {
        HashMap<String, Integer> hometownWins = new HashMap<>();
        for (int year = 2005; year <= 2013; year++) {
            String query = "select \"gameID\", \"teamID\", \"points\", year from \"TeamGameStatistic\" where year = " + year + ";";
            HashMap<String, ArrayList<Game>> games = new HashMap<>();
            HashMap<String, Integer> teamWins = new HashMap<>();
            try {
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery(query);
                while (result.next()) {
                    String gameID = result.getString("gameID");
                    String teamID = result.getString("teamID");
                    String points = result.getString("points");
                    Game game = new Game(gameID, teamID, points);
                    if (gameID == null || teamID == null || points == null) {
                        continue;
                    }
                    if (!games.containsKey(gameID)) {
                        games.put(gameID, new ArrayList<>());
                    }
                    games.get(gameID).add(game);
                }
            } catch (Exception e) {
                System.out.println("Error accessing Database.");
            }
            for (Map.Entry<String, ArrayList<Game>> entry : games.entrySet()) {
                ArrayList<Game> game = entry.getValue();
                for (int i = 0; i < game.size() - 1; i++) {
                    if (Integer.parseInt(game.get(i).points) > Integer.parseInt(game.get(i + 1).points)) {
                        if (teamWins.containsKey(game.get(i).teamID)) {
                            int newValue = teamWins.get(game.get(i).teamID) + 1;
                            teamWins.put(game.get(i).teamID, newValue);
                        } else {
                            teamWins.put(game.get(i).teamID, 1);
                        }
                    } else {
                        if (teamWins.containsKey(game.get(i + 1).teamID)) {
                            int newValue = teamWins.get(game.get(i + 1).teamID) + 1;
                            teamWins.put(game.get(i + 1).teamID, newValue);
                        } else {
                            teamWins.put(game.get(i + 1).teamID, 1);
                        }
                    }
                }
            }
            HashMap<String, Integer> playerWinnings = new HashMap<>();
            query = "select \"playerID\", \"teamID\" from \"TeamYear\" where year = " + year + ";";
            try {
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery(query);
                while (result.next()) {
                    String playerID = result.getString("playerID");
                    String teamID = result.getString("teamID");
                    if (playerID == null || teamID == null) {
                        continue;
                    }
                    if (teamWins.containsKey(teamID)) {
                        int playerWins = teamWins.get(teamID);
                        playerWinnings.put(playerID, playerWins);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error accessing Database.");
            }
            query = "select \"playerID\", \"hometownID\" from \"Player\";";
            try {
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery(query);
                while (result.next()) {
                    String playerID = result.getString("playerID");
                    String hometownID = result.getString("hometownID");
                    if (playerID == null || hometownID == null) {
                        continue;
                    }
                    if (hometownWins.containsKey(hometownID) && playerWinnings.containsKey(playerID)) {
                        int newValue = hometownWins.get(hometownID) + playerWinnings.get(playerID);
                        hometownWins.put(hometownID, newValue);
                    } else if (playerWinnings.containsKey(playerID)) {
                        hometownWins.put(hometownID, playerWinnings.get(playerID));
                    }
                }
            } catch (Exception e) {
                System.out.println("Error accessing Database.");
            }
        }
        int max = 0;
        for (Map.Entry<String, Integer> entry : hometownWins.entrySet()) {
            int value = entry.getValue();
            if (value > max) {
                max = value;
            }
        }
        ArrayList<String> allHometowns = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : hometownWins.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            if (value == max) {
                allHometowns.add(key);
            }
        }
        String city = null;
        String state = null;
        String toWrite = "City" + "\t" + "State" + "\t" + "Count" + "\n";
        toWrite += "------------------------------------------------------------------------------------------\n";
        for (int i = 0; i < allHometowns.size(); i++) {
            String query = "select city, state from \"Location\" where \"locationID\" = " + allHometowns.get(i) + ";";
            try {
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery(query);
                while (result.next()) {
                    city = result.getString("city");
                    state = result.getString("state");
                }
            } catch (Exception e) {
                System.out.println("Error accessing Database.");
            }
            toWrite += city + "\t" + state + "\t" + max + "\n";
        }

        if (displayHere) {
            HelperFunctions.showResults(toWrite);
        }

        if (export) {
            HelperFunctions.writeToFile(toWrite, fileName);
        }
    }

    static class Game {
        String gameID = "";
        String teamID = "";
        String points = "";

        Game(String gameID, String teamID, String points) {
            this.gameID = gameID;
            this.teamID = teamID;
            this.points = points;
        }
    }

    public static void findBasedOnRushingYards(Connection conn, boolean export, boolean displayHere, String fileName) {
        String query = "select \"PlayerGameStatistic\".\"playerID\", \"rushYard\", \"hometownID\" from \"Player\", \"PlayerGameStatistic\" where \n" +
                "\"Player\".\"playerID\" = \"PlayerGameStatistic\".\"playerID\";";
        HashMap<String, Integer> occurrences = new HashMap<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                String rushYard = result.getString("rushYard");
                String hometownID = result.getString("hometownID");
                if (rushYard == null || hometownID == null) {
                    continue;
                }
                if (occurrences.containsKey(hometownID)) {
                    int newValue = occurrences.get(hometownID) + Integer.parseInt(rushYard);
                    occurrences.put(hometownID, newValue);
                } else {
                    occurrences.put(hometownID, Integer.parseInt(rushYard));
                }
            }
        } catch (Exception e) {
            System.out.println("Error accessing Database.");
        }
        int max = 0;
        for (Map.Entry<String, Integer> entry : occurrences.entrySet()) {
            int value = entry.getValue();
            if (value > max) {
                max = value;
            }
        }
        ArrayList<String> allHometowns = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : occurrences.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            if (value == max) {
                allHometowns.add(key);
            }
        }
        String city = null;
        String state = null;
        String toWrite = "City" + "\t" + "State" + "\t" + "Count" + "\n";
        toWrite += "------------------------------------------------------------------------------------------\n";
        for (int i = 0; i < allHometowns.size(); i++) {
            query = "select city, state from \"Location\" where \"locationID\" = " + allHometowns.get(i) + ";";
            try {
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery(query);
                while (result.next()) {
                    city = result.getString("city");
                    state = result.getString("state");
                }
            } catch (Exception e) {
                System.out.println("Error accessing Database.");
            }
            toWrite += city + "\t" + state + "\t" + max + "\n";
        }

        if (displayHere) {
            HelperFunctions.showResults(toWrite);
        }

        if (export) {
            HelperFunctions.writeToFile(toWrite, fileName);
        }
    }

    public static void findBasedOnPosition(Connection conn, String position, boolean export, boolean displayHere, String fileName) {
        HashMap<String, Integer> occurrences = new HashMap<>();
        for (int year = 2005; year <= 2013; year++) {
            String query = "select \"PositionYear\".\"playerID\", \"year\", \"positionID\", \"hometownID\" from \"PositionYear\", \"Player\" where year = " + year + " and \"positionID\" in\n" +
                    "(select \"positionID\" from \"Position\" where name = '" + position + "') and \"PositionYear\".\"playerID\" = \"Player\".\"playerID\";";
            try {
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery(query);
                while (result.next()) {
                    String hometownID = result.getString("hometownID");
                    if (hometownID == null) {
                        continue;
                    }
                    if (occurrences.containsKey(hometownID)) {
                        int newValue = occurrences.get(hometownID) + 1;
                        occurrences.put(hometownID, newValue);
                    } else {
                        occurrences.put(hometownID, 1);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error accessing Database.");
            }
        }
        int max = 0;
        for (Map.Entry<String, Integer> entry : occurrences.entrySet()) {
            int value = entry.getValue();
            if (value > max) {
                max = value;
            }
        }
        ArrayList<String> allHometowns = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : occurrences.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            if (value == max) {
                allHometowns.add(key);
            }
        }
        String city = null;
        String state = null;
        String toWrite = "City" + "\t" + "State" + "\t" + "Count" + "\n";
        toWrite += "------------------------------------------------------------------------------------------\n";
        for (int i = 0; i < allHometowns.size(); i++) {
            String query = "select city, state from \"Location\" where \"locationID\" = " + allHometowns.get(i) + ";";
            try {
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery(query);
                while (result.next()) {
                    city = result.getString("city");
                    state = result.getString("state");
                }
            } catch (Exception e) {
                System.out.println("Error accessing Database.");
            }
            toWrite += city + "\t" + state + "\t" + max + "\n";
        }

        if (displayHere) {
            HelperFunctions.showResults(toWrite);
        }

        if (export) {
            HelperFunctions.writeToFile(toWrite, fileName);
        }
    }
}
