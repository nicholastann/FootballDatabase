package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import sample.phase4.HometownInformation;
import sample.phase4.MostRushingYards;
import sample.phase4.VictoryChain;
import sample.phase4.WinsByStadium;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static Connection conn = null;

    @FXML
    private TextField player_single_first_name;

    @FXML
    private TextField player_single_last_name;

    @FXML
    private ComboBox<String> player_single_field_of_interest;

    @FXML
    private ComboBox<String> player_single_year_of_interest;

    @FXML
    private RadioButton player_single_export;

    @FXML
    private RadioButton player_single_display_here;

    @FXML
    private TextField player_single_file_name;

    @FXML
    private ComboBox<String> player_all_year_of_interest;

    @FXML
    private RadioButton player_all_export;

    @FXML
    private RadioButton player_all_display_here;

    @FXML
    private TextField player_all_file_name;

    @FXML
    private ComboBox<String> player_all_find;

    @FXML
    private TextField player_all_specific_value;

    @FXML
    private ComboBox<String> player_all_field_of_interest;

    @FXML
    private ComboBox<String> team_single_year_of_interest;

    @FXML
    private RadioButton team_single_export;

    @FXML
    private RadioButton team_single_display_here;

    @FXML
    private TextField team_single_file_name;

    @FXML
    private ComboBox<String> team_single_field_of_interest;

    @FXML
    private TextField team_single_team_name;

    @FXML
    private RadioButton team_all_export;

    @FXML
    private RadioButton team_all_display_here;

    @FXML
    private TextField team_all_file_name;

    @FXML
    private ComboBox<String> team_all_field_of_interest;

    @FXML
    private ComboBox<String> team_all_year_of_interest;

    @FXML
    private TextField team_all_specific_value;

    @FXML
    private DatePicker games_date;

    @FXML
    private TextField games_stadium_name;

    @FXML
    private RadioButton games_export;

    @FXML
    private RadioButton games_display_here;

    @FXML
    private TextField games_file_name;

    @FXML
    private RadioButton wins_by_stadium_export;

    @FXML
    private RadioButton wins_by_stadium_display_here;

    @FXML
    private TextField wins_by_stadium_file_name;

    @FXML
    private TextField wins_by_stadium_threshold;

    @FXML
    private ComboBox<String> wins_by_stadium_game_site;

    @FXML
    private ComboBox<String> wins_by_stadium_year_of_interest;

    @FXML
    private RadioButton hometown_info_export;

    @FXML
    private RadioButton hometown_info_display_here;

    @FXML
    private TextField hometown_info_file_name;

    @FXML
    private ComboBox<String> hometown_info_area_of_interest;

    @FXML
    private RadioButton rushing_yards_export;

    @FXML
    private RadioButton rushing_yards_display_here;

    @FXML
    private TextField rushing_yards_file_name;

    @FXML
    private ComboBox<String> rushing_yards_year_of_interest;

    @FXML
    private TextField rushing_yards_team;

    @FXML
    private RadioButton victory_chain_export;

    @FXML
    private RadioButton victory_chain_display_here;

    @FXML
    private TextField victory_chain_file_name;

    @FXML
    private TextField victory_chain_team_a;

    @FXML
    private TextField victory_chain_team_b;

    private ObservableList<String> player_single_field_of_interest_list = FXCollections.observableArrayList("weight", "height", "hometown", "team", "jersey", "points");

    private ObservableList<String> player_all_field_of_interest_list = FXCollections.observableArrayList("weight", "height");
    private ObservableList<String> player_all_find_list = FXCollections.observableArrayList("greater than", "less than", "equal");

    private ObservableList<String> team_single_field_of_interest_list = FXCollections.observableArrayList("conference", "division", "stadiums played at", "dates played");

    private ObservableList<String> team_all_field_of_interest_list = FXCollections.observableArrayList("conference", "division", "stadiums played at");

    private ObservableList<String> year_list = FXCollections.observableArrayList("2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013");
    private ObservableList<String> year_all_list = FXCollections.observableArrayList("2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "All");

    private ObservableList<String> game_site_list = FXCollections.observableArrayList("Team", "Neutral");

    private ObservableList<String> hometown_info_area_of_interest_list = FXCollections.observableArrayList("Players", "Winners", "Rushing Yards", "ATH", "C", "CB",
            "DB", "DE", "DL", "DS", "DT", "FB", "FL","FS", "HB", "HOLD", "ILB", "K", "LB", "LS", "MLB", "NG", "NT", "OG", "OL", "OLB", "OT", "P", "PK", "QB", "RB", "ROV",
            "RV", "S", "SB", "SE", "SLB", "SN", "SS", "TB", "TE", "WLB", "WR");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        player_single_field_of_interest.setItems(player_single_field_of_interest_list);
        player_single_year_of_interest.setItems(year_list);
        player_all_year_of_interest.setItems(year_list);
        player_all_field_of_interest.setItems(player_all_field_of_interest_list);
        player_all_find.setItems(player_all_find_list);
        team_single_year_of_interest.setItems(year_list);
        team_single_field_of_interest.setItems(team_single_field_of_interest_list);
        team_all_field_of_interest.setItems(team_all_field_of_interest_list);
        team_all_year_of_interest.setItems(year_list);
        wins_by_stadium_year_of_interest.setItems(year_all_list);
        wins_by_stadium_game_site.setItems(game_site_list);
        hometown_info_area_of_interest.setItems(hometown_info_area_of_interest_list);
        rushing_yards_year_of_interest.setItems(year_all_list);
    }

    @FXML
    void playerSingleExecute(ActionEvent event) {
        if ((!player_single_export.isSelected() && !player_single_display_here.isSelected())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("File specifications not filled!");
            alert.showAndWait();
            return;
        }
        if (player_single_export.isSelected() && player_single_file_name.getText().equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("File name not specified!");
            alert.showAndWait();
            return;
        }
        if (player_single_first_name.getText().equalsIgnoreCase("") || player_single_last_name.getText().equalsIgnoreCase("") || player_single_field_of_interest.getValue().equalsIgnoreCase("") || player_single_year_of_interest.getValue().equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Make sure all fields are filled!");
            alert.showAndWait();
            return;
        }
        if (player_single_field_of_interest.getValue().equalsIgnoreCase("weight")) {
            String firstName = player_single_first_name.getText();
            String lastName = player_single_last_name.getText();
            String year = player_single_year_of_interest.getValue();
            String query = "";
            if (!firstName.equalsIgnoreCase("") && !lastName.equalsIgnoreCase("") && !year.equalsIgnoreCase("")) {
                query = "SELECT weight FROM \"WeightHeightYear\" WHERE year = " + year + " AND \"playerID\" IN (SELECT \"playerID\" FROM \"Player\" WHERE \"firstName\" = '" + firstName + "' AND \"lastName\" = '" + lastName + "');";
                boolean export = player_single_export.isSelected();
                boolean displayOnGUI = player_single_display_here.isSelected();
                String fileName = player_single_file_name.getText();
                HelperFunctions.performQuery(conn, query, "weight", "", export, displayOnGUI, fileName);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Make sure you provide first, last names and year.");
                alert.showAndWait();
            }
        }
        if (player_single_field_of_interest.getValue().equalsIgnoreCase("height")) {
            String firstName = player_single_first_name.getText();
            String lastName = player_single_last_name.getText();
            String year = player_single_year_of_interest.getValue();
            String query = "";
            if (!firstName.equalsIgnoreCase("") && !lastName.equalsIgnoreCase("") && !year.equalsIgnoreCase("") && !year.equalsIgnoreCase("")) {
                query = "SELECT height FROM \"WeightHeightYear\" WHERE year = " + year + " AND \"playerID\" IN (SELECT \"playerID\" FROM \"Player\" WHERE \"firstName\" = '" + firstName + "' AND \"lastName\" = '" + lastName + "');";
                boolean export = player_single_export.isSelected();
                boolean displayOnGUI = player_single_display_here.isSelected();
                String fileName = player_single_file_name.getText();
                HelperFunctions.performQuery(conn, query, "height", "", export, displayOnGUI, fileName);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Make sure you provide first, last names and year.");
                alert.showAndWait();
            }
        }
        if (player_single_field_of_interest.getValue().equalsIgnoreCase("hometown")) {
            String firstName = player_single_first_name.getText();
            String lastName = player_single_last_name.getText();
            String query = "";
            if (!firstName.equalsIgnoreCase("") && !lastName.equalsIgnoreCase("")) {
                query = "SELECT city, state FROM \"Location\" WHERE \"locationID\" IN (SELECT \"hometownID\" FROM \"Player\" WHERE \"firstName\" = '" + firstName + "' AND \"lastName\" = '" + lastName + "');";
                boolean export = player_single_export.isSelected();
                boolean displayOnGUI = player_single_display_here.isSelected();
                String fileName = player_single_file_name.getText();
                HelperFunctions.performQuery(conn, query, "city", "state", export, displayOnGUI, fileName);
            } else {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Make sure you provide first, last names and year.");
                alert.showAndWait();
            }
        }
        if (player_single_field_of_interest.getValue().equalsIgnoreCase("team")) {
            String firstName = player_single_first_name.getText();
            String lastName = player_single_last_name.getText();
            String year = player_single_year_of_interest.getValue();
            String query = "";
            if (!firstName.equalsIgnoreCase("") && !lastName.equalsIgnoreCase("") && !year.equalsIgnoreCase("")) {
                query = "SELECT school FROM \"Team\" WHERE \"teamID\" IN (SELECT \"teamID\" FROM \"TeamYear\" WHERE \"playerID\"" +
                        " IN (SELECT \"playerID\" FROM \"Player\" WHERE \"firstName\" = '" + firstName + "' AND \"lastName\" = '" + lastName + "')" +
                        " AND year = " + year + ") AND year = " + year + ";";
                boolean export = player_single_export.isSelected();
                boolean displayOnGUI = player_single_display_here.isSelected();
                String fileName = player_single_file_name.getText();
                HelperFunctions.performQuery(conn, query, "school", "", export, displayOnGUI, fileName);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Make sure you provide first, last names and year.");
                alert.showAndWait();
            }
        }
        if (player_single_field_of_interest.getValue().equalsIgnoreCase("jersey")) {
            String firstName = player_single_first_name.getText();
            String lastName = player_single_last_name.getText();
            String year = player_single_year_of_interest.getValue();
            String query = "";
            if (!firstName.equalsIgnoreCase("") && !lastName.equalsIgnoreCase("") && !year.equalsIgnoreCase("")) {
                query = "SELECT jersey FROM \"JerseyNumberYear\", \"Player\" WHERE \"firstName\" = '" + firstName + "' AND \"lastName\" = '" + lastName + "' AND \"Player\".\"playerID\" = \"JerseyNumberYear\".\"playerID\" AND year = " + year + ";";
                boolean export = player_single_export.isSelected();
                boolean displayOnGUI = player_single_display_here.isSelected();
                String fileName = player_single_file_name.getText();
                HelperFunctions.performQuery(conn, query, "jersey", "", export, displayOnGUI, fileName);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Make sure you provide first, last names and year.");
                alert.showAndWait();
            }
        }
        if (player_single_field_of_interest.getValue().equalsIgnoreCase("points")) {
            String firstName = player_single_first_name.getText();
            String lastName = player_single_last_name.getText();
            String year = player_single_year_of_interest.getValue();
            String query = "";
            if (!firstName.equalsIgnoreCase("") && !lastName.equalsIgnoreCase("") && !year.equalsIgnoreCase("")) {
                query = "SELECT SUM(points) FROM \"PlayerGameStatistic\", \"Player\" WHERE \"firstName\" = '" + firstName + "' AND \"lastName\" = '" + lastName + "' AND \"Player\".\"playerID\" = \"PlayerGameStatistic\".\"playerID\" AND year = " + year + ";";
                boolean export = player_single_export.isSelected();
                boolean displayOnGUI = player_single_display_here.isSelected();
                String fileName = player_single_file_name.getText();
                HelperFunctions.performQuery(conn, query, "sum", "", export, displayOnGUI, fileName);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Make sure you provide first, last names and year.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void playerAllExecute(ActionEvent event) {
        String year = player_all_year_of_interest.getValue();
        String specificValue = player_all_specific_value.getText();
        String specificOperation = player_all_find.getValue();
        if (specificValue.equalsIgnoreCase("") || year.equalsIgnoreCase("") || specificOperation.equalsIgnoreCase("") || player_all_field_of_interest.getValue().equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Make sure to fill in fields!");
            alert.showAndWait();
            return;
        }
        if ((!player_all_export.isSelected() && !player_all_display_here.isSelected())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("File specifications not filled!");
            alert.showAndWait();
            return;
        }
        if (player_all_export.isSelected() && player_all_file_name.getText().equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("File name not specified!");
            alert.showAndWait();
            return;
        }
        if (player_all_field_of_interest.getValue().equalsIgnoreCase("weight")) {
            String query = "";
            if (specificOperation.equalsIgnoreCase("greater than") && specificValue.chars().allMatch(Character::isDigit)
                    && !year.equalsIgnoreCase("")) {
                query = "SELECT \"Player\".\"firstName\", \"Player\".\"lastName\" FROM \"Player\", \"WeightHeightYear\" WHERE \"Player\".\"playerID\" = \"WeightHeightYear\".\"playerID\" AND weight > " + specificValue +
                        " AND year = " + year + ";";
                boolean export = player_all_export.isSelected();
                boolean displayOnGUI = player_all_display_here.isSelected();
                String fileName = player_all_file_name.getText();
                HelperFunctions.performQuery(conn, query, "firstName", "lastName", export, displayOnGUI, fileName);
            } else if (specificOperation.equalsIgnoreCase("less than") && specificValue.chars().allMatch(Character::isDigit)
                    && !year.equalsIgnoreCase("")) {
                query = "SELECT \"Player\".\"firstName\", \"Player\".\"lastName\" FROM \"Player\", \"WeightHeightYear\" WHERE \"Player\".\"playerID\" = \"WeightHeightYear\".\"playerID\" AND weight < " + specificValue +
                        " AND year = " + year + ";";
                boolean export = player_all_export.isSelected();
                boolean displayOnGUI = player_all_display_here.isSelected();
                String fileName = player_all_file_name.getText();
                HelperFunctions.performQuery(conn, query, "firstName", "lastName", export, displayOnGUI, fileName);
            } else if (specificOperation.equalsIgnoreCase("equal") && specificValue.chars().allMatch(Character::isDigit)
                    && !year.equalsIgnoreCase("")) {
                query = "SELECT \"Player\".\"firstName\", \"Player\".\"lastName\" FROM \"Player\", \"WeightHeightYear\" WHERE \"Player\".\"playerID\" = \"WeightHeightYear\".\"playerID\" AND weight = " + specificValue +
                        " AND year = " + year + ";";
                boolean export = player_all_export.isSelected();
                boolean displayOnGUI = player_all_display_here.isSelected();
                String fileName = player_all_file_name.getText();
                HelperFunctions.performQuery(conn, query, "firstName", "lastName", export, displayOnGUI, fileName);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Make sure all fields are filled!");
                alert.showAndWait();
            }
        }
        if (player_all_field_of_interest.getValue().equalsIgnoreCase("height")) {
            String query = "";
            if (specificOperation.equalsIgnoreCase("greater than") && specificValue.chars().allMatch(Character::isDigit)
                    && !year.equalsIgnoreCase("")) {
                query = "SELECT \"Player\".\"firstName\", \"Player\".\"lastName\" FROM \"Player\", \"WeightHeightYear\" WHERE \"Player\".\"playerID\" = \"WeightHeightYear\".\"playerID\" AND height > " + specificValue +
                        " AND year = " + year + ";";
                boolean export = player_all_export.isSelected();
                boolean displayOnGUI = player_all_display_here.isSelected();
                String fileName = player_all_file_name.getText();
                HelperFunctions.performQuery(conn, query, "firstName", "lastName", export, displayOnGUI, fileName);
            } else if (specificOperation.equalsIgnoreCase("less than") && specificValue.chars().allMatch(Character::isDigit)
                    && !year.equalsIgnoreCase("")) {
                query = "SELECT \"Player\".\"firstName\", \"Player\".\"lastName\" FROM \"Player\", \"WeightHeightYear\" WHERE \"Player\".\"playerID\" = \"WeightHeightYear\".\"playerID\" AND height < " + specificValue +
                        " AND year = " + year + ";";
                boolean export = player_all_export.isSelected();
                boolean displayOnGUI = player_all_display_here.isSelected();
                String fileName = player_all_file_name.getText();
                HelperFunctions.performQuery(conn, query, "firstName", "lastName", export, displayOnGUI, fileName);
            } else if (specificOperation.equalsIgnoreCase("equal") && specificValue.chars().allMatch(Character::isDigit)
                    && !year.equalsIgnoreCase("")) {
                query = "SELECT \"Player\".\"firstName\", \"Player\".\"lastName\" FROM \"Player\", \"WeightHeightYear\" WHERE \"Player\".\"playerID\" = \"WeightHeightYear\".\"playerID\" AND height = " + specificValue +
                        " AND year = " + year + ";";
                boolean export = player_all_export.isSelected();
                boolean displayOnGUI = player_all_display_here.isSelected();
                String fileName = player_all_file_name.getText();
                HelperFunctions.performQuery(conn, query, "firstName", "lastName", export, displayOnGUI, fileName);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Make sure all fields are filled!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void teamSingleExecute(ActionEvent event) {
        String query = "";
        if ((!team_single_export.isSelected() && !team_single_display_here.isSelected())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("File specifications not filled!");
            alert.showAndWait();
            return;
        }
        if (team_single_export.isSelected() && team_single_file_name.getText().equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("File name not specified!");
            alert.showAndWait();
            return;
        }
        if (team_single_team_name.getText().equalsIgnoreCase("") || team_single_year_of_interest.getValue().equalsIgnoreCase("") || team_single_field_of_interest.getValue().equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Make sure all fields are filled!");
            alert.showAndWait();
            return;
        }
        if (team_single_field_of_interest.getValue().equalsIgnoreCase("conference")) {
            String year = team_single_year_of_interest.getValue();
            String teamName = team_single_team_name.getText();
            if (!year.equalsIgnoreCase("") && !teamName.equalsIgnoreCase("")) {
                query = "SELECT name from \"Conference\" WHERE \"conferenceID\" IN (SELECT \"conferenceID\" from \"Team\" WHERE school = '" + teamName + "' AND year = " + year + ");";
                boolean export = team_single_export.isSelected();
                boolean displayOnGUI = team_single_display_here.isSelected();
                String fileName = team_single_file_name.getText();
                HelperFunctions.performQuery(conn, query, "name", "", export, displayOnGUI, fileName);
            }
        }
        if (team_single_field_of_interest.getValue().equalsIgnoreCase("division")) {
            String year = team_single_year_of_interest.getValue();
            String teamName = team_single_team_name.getText();
            if (!year.equalsIgnoreCase("") && !teamName.equalsIgnoreCase("")) {
                query = "SELECT name from \"Division\" WHERE \"divisionID\" IN (SELECT \"divisionID\" from \"Team\" WHERE school = '" + teamName + "' AND year = " + year + ");";
                boolean export = team_single_export.isSelected();
                boolean displayOnGUI = team_single_display_here.isSelected();
                String fileName = team_single_file_name.getText();
                HelperFunctions.performQuery(conn, query, "name", "", export, displayOnGUI, fileName);
            }
        }
        if (team_single_field_of_interest.getValue().equalsIgnoreCase("stadiums played at")) {
            String year = team_single_year_of_interest.getValue();
            String teamName = team_single_team_name.getText();
            if (!year.equalsIgnoreCase("") && !teamName.equalsIgnoreCase("")) {
                query = "SELECT name FROM \"Venue\", \"Game\" WHERE \"Venue\".\"venueID\" = \"Game\".\"venueID\" AND \"visitTeamID\" IN (SELECT \"teamID\" FROM \"Team\" WHERE school = '" + teamName + "' AND year = " + year + ") OR \"homeTeamID\" IN (SELECT \"teamID\" FROM \"Team\" WHERE school = '" + teamName + "' AND year = " + year + ");";
                boolean export = team_single_export.isSelected();
                boolean displayOnGUI = team_single_display_here.isSelected();
                String fileName = team_single_file_name.getText();
                HelperFunctions.performQuery(conn, query, "name", "", export, displayOnGUI, fileName);
            }
        }
        if (team_single_field_of_interest.getValue().equalsIgnoreCase("dates played")) {
            String year = team_single_year_of_interest.getValue();
            String teamName = team_single_team_name.getText();
            if (!year.equalsIgnoreCase("") && !teamName.equalsIgnoreCase("")) {
                query = "SELECT \"dateTime\" FROM \"Game\" WHERE \"dateTime\" >= '" + year + "-01-01' and \"dateTime\" <= '" + year + "-12-31' AND \"visitTeamID\" IN (SELECT \"teamID\" FROM \"Team\" WHERE school = '" + teamName + "' AND year = " + year + ") OR \"homeTeamID\" IN (SELECT \"teamID\" FROM \"Team\" WHERE school = '" + teamName + "' AND year = " + year + ");";
                boolean export = team_single_export.isSelected();
                boolean displayOnGUI = team_single_display_here.isSelected();
                String fileName = team_single_file_name.getText();
                HelperFunctions.performQuery(conn, query, "dateTime", "", export, displayOnGUI, fileName);
            }
        }
    }

    @FXML
    void teamAllExecute(ActionEvent event) {
        String query = "";
        if ((!team_all_export.isSelected() && !team_all_display_here.isSelected())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("File specifications not filled!");
            alert.showAndWait();
            return;
        }
        if (team_all_export.isSelected() && team_all_file_name.getText().equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("File name not specified!");
            alert.showAndWait();
            return;
        }
        if (team_all_specific_value.getText().equalsIgnoreCase("") || team_all_year_of_interest.getValue().equalsIgnoreCase("") || team_all_field_of_interest.getValue().equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Make sure all fields are filled!");
            alert.showAndWait();
            return;
        }
        if (team_all_field_of_interest.getValue().equalsIgnoreCase("conference")) {
            String year = team_all_year_of_interest.getValue();
            String value = team_all_specific_value.getText();
            if (!year.equalsIgnoreCase("") && !value.equalsIgnoreCase("")) {
                query = "SELECT school FROM \"Team\" WHERE \"conferenceID\" IN (SELECT \"conferenceID\" FROM \"Conference\" WHERE name = '" + value + "') AND year = " + year + ";";
                boolean export = team_all_export.isSelected();
                boolean displayOnGUI = team_all_display_here.isSelected();
                String fileName = team_all_file_name.getText();
                HelperFunctions.performQuery(conn, query, "school", "", export, displayOnGUI, fileName);
            }
        }
        if (team_all_field_of_interest.getValue().equalsIgnoreCase("division")) {
            String year = team_all_year_of_interest.getValue();
            String value = team_all_specific_value.getText();
            if (!year.equalsIgnoreCase("") && !value.equalsIgnoreCase("")) {
                query = "SELECT school FROM \"Team\" WHERE \"divisionID\" IN (SELECT \"divisionID\" FROM \"Division\" WHERE name = '" + value + "') AND year = " + year + ";";
                boolean export = team_all_export.isSelected();
                boolean displayOnGUI = team_all_display_here.isSelected();
                String fileName = team_all_file_name.getText();
                HelperFunctions.performQuery(conn, query, "school", "", export, displayOnGUI, fileName);
            }
        }
        if (team_all_field_of_interest.getValue().equalsIgnoreCase("stadiums played at")) {
            String year = team_all_year_of_interest.getValue();
            String value = team_all_specific_value.getText();
            if (!year.equalsIgnoreCase("") && !value.equalsIgnoreCase("")) {
                query = "SELECT school FROM \"Team\" WHERE \"venueID\" IN (SELECT \"venueID\" FROM \"Venue\" WHERE name = '" + value + "') AND year = " + year + ";";
                boolean export = team_all_export.isSelected();
                boolean displayOnGUI = team_all_display_here.isSelected();
                String fileName = team_all_file_name.getText();
                HelperFunctions.performQuery(conn, query, "school", "", export, displayOnGUI, fileName);
            }
        }
    }

    @FXML
    void gamesExecute(ActionEvent event) {
        String date = games_date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (!games_export.isSelected() && !games_display_here.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("File specifications not filled!");
            alert.showAndWait();
            return;
        }
        if (games_export.isSelected() && games_file_name.getText().equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("File name not specified!");
            alert.showAndWait();
            return;
        }
        if (games_stadium_name.getText().equalsIgnoreCase("") || date.equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Make sure all fields are filled!");
            alert.showAndWait();
            return;
        }
        String year = date.substring(0, Math.min(date.length(), 4));
        String visitingTeam = "";
        String homeTeam = "";
        String gameType = "";
        if (!games_stadium_name.getText().equalsIgnoreCase("") && !date.equalsIgnoreCase("")) {
            String name = games_stadium_name.getText();
            visitingTeam = "SELECT school FROM \"Team\" WHERE year = " + year + " AND \"teamID\" IN (SELECT \"visitTeamID\" FROM \"Game\" WHERE \"dateTime\" = '" + date + "' AND \"venueID\" IN (SELECT \"venueID\" FROM \"Venue\" WHERE name = '" + name + "'));";
            homeTeam = "SELECT school FROM \"Team\" WHERE year = " + year + " AND \"teamID\" IN (SELECT \"homeTeamID\" FROM \"Game\" WHERE \"dateTime\" = '" + date + "' AND \"venueID\" IN (SELECT \"venueID\" FROM \"Venue\" WHERE name = '" + name + "'));";
            gameType = "SELECT name from \"GameType\" WHERE \"gameTypeID\" IN (SELECT \"gameTypeID\" FROM \"Game\" WHERE \"dateTime\" = '" + date + "' AND \"venueID\" IN (SELECT \"venueID\" FROM \"Venue\" WHERE name = '" + name + "'));";
            boolean export = games_export.isSelected();
            boolean displayOnGUI = games_display_here.isSelected();
            String fileName = games_file_name.getText();
            HelperFunctions.performUniqueQuery(conn, visitingTeam, homeTeam, gameType, export, displayOnGUI, fileName);
        }
    }

    @FXML
    void winsByStadiumExecute(ActionEvent event) {
        if ((!wins_by_stadium_export.isSelected() && !wins_by_stadium_display_here.isSelected())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("File specifications not filled!");
            alert.showAndWait();
            return;
        }

        if (wins_by_stadium_export.isSelected() && wins_by_stadium_file_name.getText().equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("File name not specified!");
            alert.showAndWait();
            return;
        }

        int threshold = Integer.parseInt(wins_by_stadium_threshold.getText());
        String year = wins_by_stadium_year_of_interest.getValue();
        String gameSite = wins_by_stadium_game_site.getValue();
        boolean export = wins_by_stadium_export.isSelected();
        boolean displayHere = wins_by_stadium_display_here.isSelected();
        String fileName = wins_by_stadium_file_name.getText();

        WinsByStadium.findWinsByStadium(conn, year, gameSite, threshold, export, displayHere, fileName);
    }

    @FXML
    void hometownInfoExecute(ActionEvent event) {
        if ((!hometown_info_export.isSelected() && !hometown_info_display_here.isSelected())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("File specifications not filled!");
            alert.showAndWait();
            return;
        }

        if (hometown_info_export.isSelected() && hometown_info_file_name.getText().equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("File name not specified!");
            alert.showAndWait();
            return;
        }

        String areaOfInterest = hometown_info_area_of_interest.getValue();
        boolean export = hometown_info_export.isSelected();
        boolean displayHere = hometown_info_display_here.isSelected();
        String fileName = hometown_info_file_name.getText();

        if (areaOfInterest.equalsIgnoreCase("Players")) {
            HometownInformation.findBasedOnPlayers(conn, export, displayHere, fileName);
        } else if (areaOfInterest.equalsIgnoreCase("Winners")) {
            HometownInformation.findBasedOnWinners(conn, export, displayHere, fileName);
        } else if (areaOfInterest.equalsIgnoreCase("Rushing yards")) {
            HometownInformation.findBasedOnRushingYards(conn, export, displayHere, fileName);
        } else if (!areaOfInterest.equalsIgnoreCase("")) {
            HometownInformation.findBasedOnPosition(conn, areaOfInterest, export, displayHere, fileName);
        }
    }

    @FXML
    void rushingYardsExecute(ActionEvent event) {
        if ((!rushing_yards_export.isSelected() && !rushing_yards_display_here.isSelected())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("File specifications not filled!");
            alert.showAndWait();
            return;
        }

        if (rushing_yards_export.isSelected() && rushing_yards_file_name.getText().equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("File name not specified!");
            alert.showAndWait();
            return;
        }

        String teamName = rushing_yards_team.getText();
        String year = rushing_yards_year_of_interest.getValue();
        boolean export = rushing_yards_export.isSelected();
        boolean displayHere = rushing_yards_display_here.isSelected();
        String fileName = rushing_yards_file_name.getText();

        MostRushingYards.findMostRushingYards(conn, teamName, year, export, displayHere, fileName);
    }

    @FXML
    void victoryChainExecute(ActionEvent event) {
        if ((!victory_chain_export.isSelected() && !victory_chain_display_here.isSelected())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("File specifications not filled!");
            alert.showAndWait();
            return;
        }

        if (victory_chain_export.isSelected() && victory_chain_file_name.getText().equalsIgnoreCase("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("File name not specified!");
            alert.showAndWait();
            return;
        }

        String teamA = victory_chain_team_a.getText();
        String teamB = victory_chain_team_b.getText();
        boolean export = victory_chain_export.isSelected();
        boolean displayHere = victory_chain_display_here.isSelected();
        String fileName = victory_chain_file_name.getText();

        VictoryChain.findVictoryChain(conn, teamA, teamB, export, displayHere, fileName);

    }

    public static void setupDatabaseConnection() {
        DBSetup my = new DBSetup();
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/team905_12_cfb", my.user, my.pswd);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    static class closeConnection extends Thread {
        public void run() {
            try {
                conn.close();
                System.out.println("Connection Closed.");
            } catch(Exception e) {
                System.out.println("Connection NOT Closed.");
            }
        }
    }

}
