/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.MainPageController.paneNum;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AboOod_shbika99
 */
public class AddBorrowerFxmlController implements Initializable {

    @FXML
    private TextField AddBorrowerFxml_FirstNameTextField;
    @FXML
    private TextField AddBorrowerFxml_LastNameTextField;
    @FXML
    private TextField AddBorrowerFxml_PhoneTextField;
    @FXML
    private TextField AddBorrowerFxml_AgeTextField;
    @FXML
    private TextField AddBorrowerFxml_EmailTextField;
    @FXML
    private TextField AddBorrowerFxml_AddressTextField;
    @FXML
    private Button AddBorrowerFxml_ButtonAdd;
    @FXML
    private Button AddBorrowerFxml_ButtonCancel;
    Alert a = new Alert(Alert.AlertType.NONE);
    Statement BorrowerStatement3;
    @FXML
    private ToggleGroup genderR;
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
            this.BorrowerStatement3 = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleAddBorrowerClicks(ActionEvent event) throws IOException, SQLException {
        if (event.getSource() == AddBorrowerFxml_ButtonAdd) {
            a.setHeaderText("");
            a.setContentText("");
            if (AddBorrowerFxml_FirstNameTextField.getText().equals("") || AddBorrowerFxml_LastNameTextField.getText().equals("") || AddBorrowerFxml_PhoneTextField.getText().equals("") || AddBorrowerFxml_AgeTextField.getText().equals("") || AddBorrowerFxml_EmailTextField.getText().equals("") || AddBorrowerFxml_AddressTextField.getText().equals("") || genderR.getSelectedToggle() == null) {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setHeaderText("Don't leave any text field is Empty!");
                a.show();
            } else {
                try {
                    a.setAlertType(Alert.AlertType.CONFIRMATION);
                    a.setHeaderText("are you sure?");
                    Optional<ButtonType> result = a.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        String FirstName = AddBorrowerFxml_FirstNameTextField.getText();
                        String LastName = AddBorrowerFxml_LastNameTextField.getText();
                        Integer Phone = Integer.valueOf(AddBorrowerFxml_PhoneTextField.getText());
                        Integer Age = Integer.valueOf(AddBorrowerFxml_AgeTextField.getText());
                        String Email = AddBorrowerFxml_EmailTextField.getText();
                        String Address = AddBorrowerFxml_AddressTextField.getText();
                        RadioButton selectedRadioButton = (RadioButton) genderR.getSelectedToggle();
                        String toogleGroupValue = selectedRadioButton.getText();
                        String sql = "insert into borrowers values (" + null + ",'" + FirstName + "','" + LastName + "'," + Phone + "," + Age + ",'" + Email + "','" + Address + "','" + toogleGroupValue + "');";
                        int BorrowerAdded = BorrowerStatement3.executeUpdate(sql);
                        if (BorrowerAdded > 0) {
                            a.setAlertType(Alert.AlertType.INFORMATION);
                            a.setHeaderText("The Borrower has been Added Successfully!");
                            MainPageController.log_File("a new borrower has been added successfully, the borrower details:\n " + "First Name: " + FirstName + ", Last Name: " + LastName + ", Phone: " + Phone + ", Age: " + Age + ", Email: " + Email + ", Address: " + Address + ", gender: " + toogleGroupValue + "\n");
                            a.showAndWait();
                            paneNum = 2;
                            Stage stage = (Stage) AddBorrowerFxml_ButtonCancel.getScene().getWindow();
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
                            a.setAlertType(Alert.AlertType.ERROR);
                            a.setHeaderText("The Borrower was not added...");
                            a.show();
                        }
                    }
                } catch (Exception ex) {
                    //Logger.getLogger(AddBorrowerFxmlController.class.getName()).log(Level.SEVERE, null, ex);
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setHeaderText("make sure you filled the inputs correctly");
                    a.show();
                }
            }
        } else if (event.getSource() == AddBorrowerFxml_ButtonCancel) {
            MainPageController.log_File("adding a borrower has been cancelled \n");
            paneNum = 2;
            Stage stage = (Stage) AddBorrowerFxml_ButtonCancel.getScene().getWindow();
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
        }
    }

}
