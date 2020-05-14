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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AboOod_shbika99
 */
public class UpdateBorrowerFxmlController implements Initializable {

    @FXML
    private TextField UpdateBorrowerFxml_textField;
    @FXML
    private Button UpdateBorrowerFxml_ButtonLookFor;
    @FXML
    private Button UpdateBorrowerFxml_ButtonCancel;
    Alert a = new Alert(Alert.AlertType.NONE);
    Statement BorrowerStatement4;
    public static String globalUpdateBorrowerId;
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
            this.BorrowerStatement4 = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateBorrowerClicks(ActionEvent event) throws IOException, SQLException {
        if (event.getSource() == UpdateBorrowerFxml_ButtonLookFor) {
            a.setHeaderText("");
            a.setContentText("");
            if (UpdateBorrowerFxml_textField.getText().equals("")) {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setHeaderText("The text field is Empty!");
                a.show();
            } else {
                try {
                    String sql = "select * from borrowers where id = " + UpdateBorrowerFxml_textField.getText();
                    ResultSet BorroweRs = BorrowerStatement4.executeQuery(sql);
                    if (BorroweRs.next()) {
                        globalUpdateBorrowerId = UpdateBorrowerFxml_textField.getText();
                        a.setAlertType(Alert.AlertType.INFORMATION);
                        a.setHeaderText("The Borrower has been found");
                        a.setContentText("you shall update it now");
                        a.showAndWait();
                        paneNum = 2;
                        Stage stage = (Stage) UpdateBorrowerFxml_ButtonCancel.getScene().getWindow();
                        Parent root = FXMLLoader.load(getClass().getResource("/fxml/UpdateBorrowerFxml2.fxml"));
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
                        a.setHeaderText("The Borrower does not exist!");
                        a.show();
                    }
                } catch (SQLException ex) {
                    //Logger.getLogger(UpdateBookFxmlController.class.getName()).log(Level.SEVERE, null, ex);
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setHeaderText("insert ONLY integer values for the ID text field!");
                    a.show();
                }
            }
        } else if (event.getSource() == UpdateBorrowerFxml_ButtonCancel) {
            paneNum = 2;
            Stage stage = (Stage) UpdateBorrowerFxml_ButtonCancel.getScene().getWindow();
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
