package controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static controllers.MainPageController.paneNum;
import entities.borrowers;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AboOod_shbika99
 */
public class SearchBorrowerFxmlController implements Initializable {

    @FXML
    private TextField SearchBorrowerFxml_textField;
    @FXML
    private Button SearchBorrowerFxml_ButtonSearch;
    @FXML
    private Button SearchBorrowerFxml_ButtonCancel;
    Alert a = new Alert(Alert.AlertType.NONE);
    Statement BorrowerStatement1;
    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library_management?serverTimeZone=UTC", "root", "");
            this.BorrowerStatement1 = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleSearchBorrowerClicks(ActionEvent event) throws IOException, SQLException {
        if (event.getSource() == SearchBorrowerFxml_ButtonSearch) {
            a.setHeaderText("");
            a.setContentText("");
            if (SearchBorrowerFxml_textField.getText().equals("")) {
                a.setAlertType(AlertType.WARNING);
                a.setHeaderText("The text field is Empty!");
                a.show();
            } else {
                try {
                    String sql = "select * from borrowers where id = " + SearchBorrowerFxml_textField.getText();
                    ResultSet BorrowerRs = BorrowerStatement1.executeQuery(sql);
                    if (BorrowerRs.next()) {
                        int id = BorrowerRs.getInt("id");
                        String firstName = BorrowerRs.getString("firstName");
                        String lastName = BorrowerRs.getString("lastName");
                        int phone = BorrowerRs.getInt("phone");
                        int age = BorrowerRs.getInt("age");
                        String email = BorrowerRs.getString("email");
                        String address = BorrowerRs.getString("address");
                        String gender = BorrowerRs.getString("gender");
                        borrowers br = new borrowers(id, firstName, lastName, phone, age, email, address, gender);
                        a.setAlertType(AlertType.INFORMATION);
                        a.setHeaderText("The Borrower has been found                                                                                  ");
                        MainPageController.log_File("the user searched for this borrower:\n " + "First Name: " + firstName + ", Last Name: " + lastName + ", Phone: " + phone + ", Age: " + age + ", Email: " + email + ", Address: " + address + ", gender: " + gender + "\n");
                        a.setContentText(br.toString());
                        a.showAndWait();
                        paneNum = 2;
                        Stage stage = (Stage) SearchBorrowerFxml_ButtonCancel.getScene().getWindow();
                        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
                        root.setOnMousePressed(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                xOffset = event.getSceneX();
                                yOffset = event.getSceneY();
                            }
                        });
                        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                stage.setX(event.getScreenX() - xOffset);
                                stage.setY(event.getScreenY() - yOffset);
                            }
                        });
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
                        xOffset = 0;
                        yOffset = 0;
                    } else {
                        a.setAlertType(AlertType.ERROR);
                        a.setHeaderText("The Borrower does not exist!");
                        a.show();
                    }
                } catch (Exception ex) {
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setHeaderText("insert ONLY integer values for the ID text field!");
                    a.show();
                }
            }
        } else if (event.getSource() == SearchBorrowerFxml_ButtonCancel) {
            MainPageController.log_File("searching for a borrower has been cancelled \n");
            paneNum = 2;
            Stage stage = (Stage) SearchBorrowerFxml_ButtonCancel.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
            yOffset = 0;
            xOffset = 0;
        }
    }

}
