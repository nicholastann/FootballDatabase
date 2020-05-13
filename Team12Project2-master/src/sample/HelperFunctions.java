package sample;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HelperFunctions {
    public static void showResults(String toWrite) {
        JFrame frame = new JFrame ("Results");
        frame.setSize(500,500);
        frame.setResizable(false);

        JTextArea textArea = new JTextArea(toWrite);
        textArea.setSize(400,400);

        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setVisible(true);

        JScrollPane scroll = new JScrollPane (textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        frame.add(scroll);
        frame.setVisible(true);
    }

    public static void writeToFile(String toWrite, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".txt"));
            writer.write(toWrite);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void performUniqueQuery(Connection conn, String query1, String query2, String query3, boolean export, boolean displayOnGUI, String fileName) {
        try {
            if (export == true) {
                Statement stmt1 = conn.createStatement();
                Statement stmt2 = conn.createStatement();
                Statement stmt3 = conn.createStatement();
                ResultSet result1 = stmt1.executeQuery(query1);
                ResultSet result2 = stmt2.executeQuery(query2);
                ResultSet result3 = stmt3.executeQuery(query3);
                String write1 = "";
                String write2 = "";
                String write3 = "";
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".txt"));
                writer.write("visiting team" + "\t" + "home team" + "\t" + "game type" + "\n");
                writer.write("---------------------------------------------------------------\n");
                while (result1.next()) {
                    write1 = result1.getString("school");
                }
                while (result2.next()) {
                    write2 = result2.getString("school");
                }
                while (result3.next()) {
                    write3 = result3.getString("name");
                }
                writer.write(write1 + "\t");
                writer.write(write2 + "\t");
                writer.write(write3);
                writer.close();
            }
            if (displayOnGUI == true) {
                Statement stmt1 = conn.createStatement();
                Statement stmt2 = conn.createStatement();
                Statement stmt3 = conn.createStatement();
                ResultSet result1 = stmt1.executeQuery(query1);
                ResultSet result2 = stmt2.executeQuery(query2);
                ResultSet result3 = stmt3.executeQuery(query3);
                String write1 = "";
                String write2 = "";
                String write3 = "";
                String toWrite = "";
                toWrite += "visiting team" + "\t" + "home team" + "\t" + "game type" + "\n";
                toWrite += "----------------------------------------------------------\n";
                while (result1.next()) {
                    write1 = result1.getString("school");
                }
                while (result2.next()) {
                    write2 = result2.getString("school");
                }
                while (result3.next()) {
                    write3 = result3.getString("name");
                }
                toWrite += write1 + "\t";
                toWrite += write2 + "\t";
                toWrite += write3 + "\t";

                HelperFunctions.showResults(toWrite);
            }
        } catch (Exception e) {
            System.out.println("Error accessing Database.");
        }
    }

    public static void performQuery(Connection conn, String sqlStatement, String columnLabel1, String columnLabel2, boolean export, boolean displayOnGUI, String fileName) {
        try {
            Statement stmt = conn.createStatement();
            if (export == true && !columnLabel1.equalsIgnoreCase("") && !columnLabel2.equalsIgnoreCase("")) {
                ResultSet result = stmt.executeQuery(sqlStatement);
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".txt"));
                writer.write(columnLabel1 + '\t' + columnLabel2 + "\n");
                while (result.next()) {
                    String write1 = result.getString(columnLabel1);
                    String write2 = result.getString(columnLabel2);
                    writer.write(write1 + "\t" + write2 + "\n");
                    System.out.println(write1 + write2);
                }
                writer.close();
            }
            if (export == true && !columnLabel1.equalsIgnoreCase("") && columnLabel2.equalsIgnoreCase("")) {
                ResultSet result = stmt.executeQuery(sqlStatement);
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".txt"));
                writer.write(columnLabel1 + "\n");
                while (result.next()) {
                    String write1 = result.getString(columnLabel1);
                    writer.write(write1 + "\n");
                    System.out.println(write1);
                }
                writer.close();
            }
            if (export == true && columnLabel1.equalsIgnoreCase("") && !columnLabel2.equalsIgnoreCase("")) {
                ResultSet result = stmt.executeQuery(sqlStatement);
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".txt"));
                writer.write(columnLabel2 + "\n");
                while (result.next()) {
                    String write1 = result.getString(columnLabel2);
                    writer.write(write1 + "\n");
                    System.out.println(write1);
                }
                writer.close();
            }
            if (displayOnGUI == true && !columnLabel1.equalsIgnoreCase("") && !columnLabel2.equalsIgnoreCase("")) {
                ResultSet result = stmt.executeQuery(sqlStatement);
                String toWrite = columnLabel1 + '\t' + columnLabel2 + "\n";
                toWrite += "---------------------------------- \n";

                while (result.next()) {
                    String write1 = result.getString(columnLabel1);
                    String write2 = result.getString(columnLabel2);
                    toWrite += (write1 + "\t");
                    toWrite += write2;
                    toWrite += "\n";
                }
                HelperFunctions.showResults(toWrite);
            }
            if (displayOnGUI == true && !columnLabel1.equalsIgnoreCase("") && columnLabel2.equalsIgnoreCase("")) {
                ResultSet result = stmt.executeQuery(sqlStatement);
                String toWrite = columnLabel1 + "\n";
                toWrite += "---------------------------------- \n";
                while (result.next()) {
                    String write1 = result.getString(columnLabel1);
                    toWrite += (write1 + "\n");
                }
                HelperFunctions.showResults(toWrite);
            }
            if (displayOnGUI == true && columnLabel1.equalsIgnoreCase("") && !columnLabel2.equalsIgnoreCase("")) {
                ResultSet result = stmt.executeQuery(sqlStatement);
                String toWrite = columnLabel2 + "\n";
                toWrite += "---------------------------------- \n";

                while (result.next()) {
                    String write1 = result.getString(columnLabel2);
                    toWrite += (write1 + "\n");
                }
                HelperFunctions.showResults(toWrite);
            }
        } catch (Exception e) {
            System.out.println("Error accessing Database.");
        }
    }
}
