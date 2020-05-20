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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AboOod_shbika99
 */
public class AddOrderFxmlController implements Initializable {

    @FXML
    private Button AddOrderFxml_ButtonAdd;
    @FXML
    private Button AddOrderFxml_ButtonCancel;
    Alert a = new Alert(Alert.AlertType.NONE);
    Statement OrderStatement3;
    @FXML
    private DatePicker AddOrderFxml_RDDatePicker;
    @FXML
    private DatePicker AddOrderFxml_BDDatePicker;
    @FXML
    private ComboBox<Integer> AddOrderFxml_BookIDComboBox = new ComboBox();
    @FXML
    private ComboBox<Integer> AddOrderFxml_BorrowerIDComboBox = new ComboBox();
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
            this.OrderStatement3 = connection.createStatement();

            String sql1 = "select id from books";
            ResultSet rs1 = OrderStatement3.executeQuery(sql1);
            ArrayList<Integer> Books_ids = new ArrayList<>();
            while (rs1.next()) {
                Books_ids.add(rs1.getInt("id"));
            }
            rs1.close();
            for (int i = 0; i < Books_ids.size(); i++) {
                AddOrderFxml_BookIDComboBox.getItems().add(Books_ids.get(i));
            }

            String sql2 = "select id from borrowers";
            ResultSet rs2 = OrderStatement3.executeQuery(sql2);
            ArrayList<Integer> Borrowers_ids = new ArrayList<>();
            while (rs2.next()) {
                Borrowers_ids.add(rs2.getInt("id"));
            }
            rs2.close();
            for (int i = 0; i < Borrowers_ids.size(); i++) {
                AddOrderFxml_BorrowerIDComboBox.getItems().add(Borrowers_ids.get(i));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleAddOrderClicks(ActionEvent event) throws IOException, SQLException {
        if (event.getSource() == AddOrderFxml_ButtonAdd) {
            a.setHeaderText("");
            a.setContentText("");
            if (AddOrderFxml_RDDatePicker.getValue() == null || AddOrderFxml_BDDatePicker.getValue() == null || AddOrderFxml_BookIDComboBox.getValue() == null || AddOrderFxml_BorrowerIDComboBox.getValue() == null) {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setHeaderText("Don't leave any text field is Empty!");
                a.show();
            } else {
                try {
                    a.setAlertType(Alert.AlertType.CONFIRMATION);
                    a.setHeaderText("are you sure?");
                    Optional<ButtonType> result = a.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        java.sql.Date BD = java.sql.Date.valueOf(AddOrderFxml_BDDatePicker.getValue());
                        java.sql.Date RD = java.sql.Date.valueOf(AddOrderFxml_RDDatePicker.getValue());
                        Integer BrID = AddOrderFxml_BorrowerIDComboBox.getValue();
                        Integer BoID = AddOrderFxml_BookIDComboBox.getValue();
                        String sql = "insert into orders values (" + null + "," + BoID + "," + BrID + ",'" + BD + "','" + RD + "');";
                        int OrderAdded = OrderStatement3.executeUpdate(sql);
                        if (OrderAdded > 0) {
                            a.setAlertType(Alert.AlertType.INFORMATION);
                            a.setHeaderText("The Order has been Added Successfully!");
                            MainPageController.log_File("a new order has been added successfully, the order details:\n " + "Book ID: " + BoID + ", Borrower ID: " + BrID + ", Borrowing Date: " + BD + ", Return Date: " + RD + "\n");
                            a.showAndWait();
                            paneNum = 1;
                            Stage stage = (Stage) AddOrderFxml_ButtonCancel.getScene().getWindow();
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
                            a.setHeaderText("The Order was not added...");
                            a.show();
                        }
                    }
                } catch (Exception ex) {
                    //Logger.getLogger(AddOrderFxmlController.class.getName()).log(Level.SEVERE, null, ex);
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setHeaderText("make sure you filled the inputs correctly");
                    a.show();
                }
            }
        } else if (event.getSource() == AddOrderFxml_ButtonCancel) {
            MainPageController.log_File("adding an order has been cancelled \n");
            paneNum = 1;
            Stage stage = (Stage) AddOrderFxml_ButtonCancel.getScene().getWindow();
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
