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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AboOod_shbika99
 */
public class DeleteBorrowerFxmlController implements Initializable {

    @FXML
    private TextField DeleteBorrowerFxml_textField;
    @FXML
    private Button SearchBorrowerFxml_ButtonDelete;
    @FXML
    private Button DeleteBorrowerFxml_ButtonCancel;
    Alert a = new Alert(Alert.AlertType.NONE);
    Statement BorrowerStatement2;
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
            this.BorrowerStatement2 = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteBorrowerClicks(ActionEvent event) throws IOException, SQLException {
        if (event.getSource() == SearchBorrowerFxml_ButtonDelete) {
            a.setHeaderText("");
            a.setContentText("");
            if (DeleteBorrowerFxml_textField.getText().equals("")) {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setHeaderText("The text field is Empty!");
                a.show();
            } else {
                try {
                    a.setAlertType(Alert.AlertType.CONFIRMATION);
                    a.setHeaderText("are you sure?");
                    Optional<ButtonType> result = a.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        String sql = "delete from borrowers where id = " + DeleteBorrowerFxml_textField.getText();
                        int BorrowerDeleted = BorrowerStatement2.executeUpdate(sql);
                        if (BorrowerDeleted > 0) {
                            a.setAlertType(Alert.AlertType.INFORMATION);
                            a.setHeaderText("The Borrower has been Deleted Successfully!");
                            MainPageController.log_File("a borrower has been deleted successfully, the borrower id is: " + DeleteBorrowerFxml_textField.getText() + "\n");
                            a.showAndWait();
                            paneNum = 2;
                            Stage stage = (Stage) DeleteBorrowerFxml_ButtonCancel.getScene().getWindow();
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
                        } else {
                            a.setAlertType(Alert.AlertType.ERROR);
                            a.setHeaderText("The Borrower does not exist!");
                            a.show();
                        }
                    }
                } catch (Exception ex) {
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setHeaderText("You can't delete a book if there's an Order to it");
                    a.setContentText("and make sure to insert ONLY integer values");
                    a.show();
                }
            }
        } else if (event.getSource() == DeleteBorrowerFxml_ButtonCancel) {
            MainPageController.log_File("deleting a borrower has been cancelled \n");
            paneNum = 2;
            Stage stage = (Stage) DeleteBorrowerFxml_ButtonCancel.getScene().getWindow();
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
