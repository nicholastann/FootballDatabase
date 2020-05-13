package sample.phase4;

import sample.HelperFunctions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MostRushingYards {
    public static void findMostRushingYards(Connection conn, String teamName, String year, boolean export, boolean displayHere, String fileName) {
        String query = null;
        if (!year.equalsIgnoreCase("all")) {
            query = "select \"gameID\" from \"TeamGameStatistic\" where \"teamID\" in (select \"teamID\"\n" +
                    "from \"Team\" where \"school\" = '" + teamName + "') and year = " + year + ";";
        } else {
            query = "select \"gameID\" from \"TeamGameStatistic\" where \"teamID\" in (select \"teamID\"\n" +
                    "from \"Team\" where \"school\" = '" + teamName + "');";
        }

        ArrayList<String> gameIDs = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                String gameID = result.getString("gameID");
                if (!gameIDs.contains(gameID)) {
                    gameIDs.add(gameID);
                }
            }
        } catch (Exception e) {
            System.out.println("Error accessing Database.");
        }

        int maxRushingYards = 0;
        int winningTeamId = -1;
        String winningDate = null;
        int winningYear = -1;
        for (int i = 0; i < gameIDs.size(); i++) {
            String gameID = gameIDs.get(i);
            query = "select \"dateTime\", \"teamID\", \"rushYard\", year from \"TeamGameStatistic\", \"Game\" where \"Game\".\"gameID\" = " + gameID + " and \"TeamGameStatistic\".\"gameID\" = " + gameID + " and \"teamID\" not in (select \"teamID\"\n" +
                    "from \"Team\" where \"school\" = '" + teamName + "');";
            try {
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery(query);
                while (result.next()) {
                    int teamID = result.getInt("teamID");
                    int rushingYards = result.getInt("rushYard");
                    int winYear = result.getInt("year");
                    String date = result.getDate("dateTime").toString();
                    if (rushingYards > maxRushingYards) {
                        maxRushingYards = rushingYards;
                        winningTeamId = teamID;
                        winningDate = date;
                        winningYear = winYear;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error accessing Database.");
            }
        }

        query = "select school from \"Team\" where \"teamID\" = " + winningTeamId + " and year = " + winningYear + " limit 1;";
        String winningSchool = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                winningSchool = result.getString("school");
            }
        } catch (Exception e) {
            System.out.println("Error accessing Database.");
        }
        String toWrite = "Year" + "\t" + "Team" + "\t" + "Rushing Yards" + "\n";
        toWrite += "------------------------------------------------------------------------------------------------------\n";
        toWrite += winningDate + "\t" + winningSchool + "\t" + maxRushingYards;

        if (displayHere) {
            HelperFunctions.showResults(toWrite);
        }

        if (export) {
            HelperFunctions.writeToFile(toWrite, fileName);
        }
    }
}
