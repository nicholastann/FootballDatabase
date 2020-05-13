package sample.phase4;

import sample.HelperFunctions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WinsByStadium {
    public static void findWinsByStadium(Connection conn, String year, String gameSite, int threshold, boolean export, boolean displayHere, String fileName) {
        String query = null;
        if (year.equalsIgnoreCase("all")) {
            query = "select \"teamID\", \"TeamGameStatistic\".\"gameID\", \"gameTypeID\", \"venueID\", \"points\" from \"Game\", \"TeamGameStatistic\" where\n" +
                    "\"Game\".\"gameID\" = \"TeamGameStatistic\".\"gameID\" and \"gameTypeID\" in (select \"gameTypeID\" from \"GameType\" where name = '" + gameSite + "');";
        } else {
            query = "select \"teamID\", \"TeamGameStatistic\".\"gameID\", \"gameTypeID\", \"venueID\", \"points\" from \"Game\", \"TeamGameStatistic\" where\n" +
                    "\"Game\".\"gameID\" = \"TeamGameStatistic\".\"gameID\" and year = " + year + " and \"gameTypeID\" in (select \"gameTypeID\" from \"GameType\" where name = '" + gameSite + "');";
        }

        HashMap<String, ArrayList<Game>> teamsByGameId = new HashMap<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                String teamID = result.getString("teamID");
                String gameID = result.getString("gameID");
                String gameTypeID = result.getString("gameTypeID");
                String venueID = result.getString("venueID");
                String points = result.getString("points");

                Game game = new Game(teamID, gameTypeID, venueID, points);
                if (!teamsByGameId.containsKey(gameID)) {
                    teamsByGameId.put(gameID, new ArrayList<>());
                }
                teamsByGameId.get(gameID).add(game);
            }
        } catch (Exception e) {
            System.out.println("Error accessing Database.");
        }

        HashMap<String, Integer> winsByVenue = new HashMap<>();
        for (Map.Entry<String, ArrayList<Game>> entry : teamsByGameId.entrySet()) {
            ArrayList<Game> values = entry.getValue();
            for (int i = 0; i < values.size() - 1; i++) {
                String venueID = values.get(i).venueID;
                if (!winsByVenue.containsKey(venueID)) {
                    winsByVenue.put(venueID, 0);
                }

                Game winner = null;
                Game loser = null;
                if (Integer.parseInt(values.get(i).points) > Integer.parseInt(values.get(i + 1).points)) {
                    winner = values.get(i);
                    loser = values.get(i + 1);
                } else if (Integer.parseInt(values.get(i).points) < Integer.parseInt(values.get(i + 1).points)) {
                    winner = values.get(i + 1);
                    loser = values.get(i);
                } else {
                    continue;
                }

                int percentParameter = threshold;
                double change = (Double.parseDouble(winner.points) - Double.parseDouble(loser.points)) / (Double.parseDouble(winner.points));
                double percentChange = change * 100;

                if (percentChange >= percentParameter) {
                    venueID = winner.venueID;
                    int newValue = winsByVenue.get(venueID) + 1;
                    winsByVenue.put(venueID, newValue);
                }
            }
        }

        String toWrite = "Stadium" + "\t" + "Count" + "\n";
        toWrite += "----------------------------------------------" + "\n";
        for (Map.Entry<String, Integer> entry : winsByVenue.entrySet()) {
            String venueID = entry.getKey();
            String venueName = null;
            query = "select name from \"Venue\" where \"venueID\" = " + venueID + ";";
            try {
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery(query);
                while (result.next()) {
                    venueName = result.getString("name");
                }
            } catch (Exception e) {
                System.out.println("Error accessing Database.");
            }
            toWrite += venueName + "\t\t" + entry.getValue() + "\n";
        }

        int maxWins = 0;
        ArrayList<String> winningHometownIds = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : winsByVenue.entrySet()) {
            int currentCount = entry.getValue();
            String currentHometown = entry.getKey();

            if (currentCount > maxWins) {
                maxWins = currentCount;
                winningHometownIds.add(currentHometown);
            }
        }

        toWrite += "\nMost wins:\n";

        for (int i = 0; i < winningHometownIds.size(); i++) {
            query = "select name from \"Venue\" where \"venueID\" = " + winningHometownIds.get(i) + ";";
            try {
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery(query);
                while (result.next()) {
                    String stadium = result.getString("name");
                    toWrite += stadium + "\t\t" + maxWins + "\n";
                }
            } catch (Exception e) {
                System.out.println("Error accessing Database.");
            }
        }

        if (displayHere) {
            HelperFunctions.showResults(toWrite);
        }
        if (export) {
            HelperFunctions.writeToFile(toWrite, fileName);
        }
    }

    static class Game {
        String teamID = null;
        String gameTypeID = null;
        String venueID = null;
        String points = null;

        Game(String teamID, String gameTypeID, String venueID, String points) {
            this.teamID = teamID;
            this.gameTypeID = gameTypeID;
            this.venueID = venueID;
            this.points = points;
        }
    }
}
