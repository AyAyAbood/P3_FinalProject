/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.MainPageController.paneNum;
import static controllers.UpdateBookFxmlController.globalUpdateBookId;
import static controllers.UpdateBorrowerFxmlController.globalUpdateBorrowerId;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AboOod_shbika99
 */
public class UpdateBorrowerFxml2Controller implements Initializable {

    @FXML
    private TextField UpdateBorrowerFxml_FirstNameTextField;
    @FXML
    private TextField UpdateBorrowerFxml_LastNameTextField;
    @FXML
    private TextField UpdateBorrowerFxml_PhoneTextField;
    @FXML
    private TextField UpdateBorrowerFxml_AgeTextField;
    @FXML
    private TextField UpdateBorrowerFxml_EmailTextField;
    @FXML
    private TextField UpdateBorrowerFxml_AddressTextField;
    @FXML
    private Button UpdateBorrowerFxml_ButtonUpdate2;
    @FXML
    private Button UpdateBorrowerFxml_ButtonCancel2;
    Alert a = new Alert(Alert.AlertType.NONE);
    Statement BorrowerStatement5;
    @FXML
    private ToggleGroup genderRR;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library_management?serverTimeZone=UTC", "root", "");
            this.BorrowerStatement5 = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateBorrowerClicks2(ActionEvent event) throws IOException, SQLException {
        if (event.getSource() == UpdateBorrowerFxml_ButtonUpdate2) {
            a.setHeaderText("");
            a.setContentText("");
            if (UpdateBorrowerFxml_FirstNameTextField.getText().equals("") || UpdateBorrowerFxml_LastNameTextField.getText().equals("") || UpdateBorrowerFxml_PhoneTextField.getText().equals("") || UpdateBorrowerFxml_AgeTextField.getText().equals("") || UpdateBorrowerFxml_EmailTextField.getText().equals("") || UpdateBorrowerFxml_AddressTextField.getText().equals("") || genderRR.getSelectedToggle() == null) {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setHeaderText("Don't leave any text field is Empty!");
                a.show();
            } else {
                try {
                    String FirstName = UpdateBorrowerFxml_FirstNameTextField.getText();
                    String LastName = UpdateBorrowerFxml_LastNameTextField.getText();
                    Integer Phone = Integer.valueOf(UpdateBorrowerFxml_PhoneTextField.getText());
                    Integer Age = Integer.valueOf(UpdateBorrowerFxml_AgeTextField.getText());
                    String Email = UpdateBorrowerFxml_EmailTextField.getText();
                    String Address = UpdateBorrowerFxml_AddressTextField.getText();
                    RadioButton selectedRadioButtonRR = (RadioButton) genderRR.getSelectedToggle();
                    String toogleGroupValueRR = selectedRadioButtonRR.getText();
                    String sql = "update borrowers set firstName = '" + FirstName + "',lastName = '" + LastName + "',phone = " + Phone + ",age = " + Age + ",email = '" + Email + "',address = '" + Address + "',Gender = '" + toogleGroupValueRR + "' where id = " + globalUpdateBorrowerId;
                    int BorrowerUpdated = BorrowerStatement5.executeUpdate(sql);
                    if (BorrowerUpdated > 0) {
                        a.setAlertType(Alert.AlertType.INFORMATION);
                        a.setHeaderText("The Borrower has been Updated Successfully!");
                        a.showAndWait();
                        paneNum = 2;
                        Stage stage = (Stage) UpdateBorrowerFxml_ButtonCancel2.getScene().getWindow();
                        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
                    } else {
                        a.setAlertType(Alert.AlertType.ERROR);
                        a.setHeaderText("The Book was not added...");
                        a.show();
                    }
                } catch (Exception ex) {
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setHeaderText("something went wrong......");
                    a.show();
                }
            }
        } else if (event.getSource() == UpdateBorrowerFxml_ButtonCancel2) {
            paneNum = 2;
            Stage stage = (Stage) UpdateBorrowerFxml_ButtonCancel2.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        }
    }

}
