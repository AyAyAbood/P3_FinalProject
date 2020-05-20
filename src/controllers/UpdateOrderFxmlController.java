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
public class UpdateOrderFxmlController implements Initializable {

    @FXML
    private Button UpdateOrderFxml_ButtonAdd;
    @FXML
    private Button UpdateOrderFxml_ButtonCancel;
    Alert a = new Alert(Alert.AlertType.NONE);
    Statement OrderStatement4;
    @FXML
    private DatePicker UpdateOrderFxml_RDDatePicker;
    @FXML
    private DatePicker UpdateOrderFxml_BDDatePicker;
    @FXML
    private ComboBox<Integer> UpdateOrderFxml_OrderIDComboBox = new ComboBox();
    @FXML
    private ComboBox<Integer> UpdateOrderFxml_BookIDComboBox = new ComboBox();
    @FXML
    private ComboBox<Integer> UpdateOrderFxml_BorrowerIDComboBox = new ComboBox();
    public int order_IDD = 0;
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
            this.OrderStatement4 = connection.createStatement();

            String sql1 = "select id from books";
            ResultSet rs1 = OrderStatement4.executeQuery(sql1);
            ArrayList<Integer> Books_ids = new ArrayList<>();
            while (rs1.next()) {
                Books_ids.add(rs1.getInt("id"));
            }
            rs1.close();
            for (int i = 0; i < Books_ids.size(); i++) {
                UpdateOrderFxml_BookIDComboBox.getItems().add(Books_ids.get(i));
            }

            String sql2 = "select id from borrowers";
            ResultSet rs2 = OrderStatement4.executeQuery(sql2);
            ArrayList<Integer> Borrowers_ids = new ArrayList<>();
            while (rs2.next()) {
                Borrowers_ids.add(rs2.getInt("id"));
            }
            rs2.close();
            for (int i = 0; i < Borrowers_ids.size(); i++) {
                UpdateOrderFxml_BorrowerIDComboBox.getItems().add(Borrowers_ids.get(i));
            }

            String sql3 = "select order_id from orders";
            ResultSet rs3 = OrderStatement4.executeQuery(sql3);
            ArrayList<Integer> Orders_ids = new ArrayList<>();
            while (rs3.next()) {
                Orders_ids.add(rs3.getInt("order_id"));
            }
            rs3.close();
            for (int i = 0; i < Orders_ids.size(); i++) {
                UpdateOrderFxml_OrderIDComboBox.getItems().add(Orders_ids.get(i));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateOrderClicks(ActionEvent event) throws IOException, SQLException {
        if (event.getSource() == UpdateOrderFxml_ButtonAdd) {
            a.setHeaderText("");
            a.setContentText("");
            if (UpdateOrderFxml_RDDatePicker.getValue() == null || UpdateOrderFxml_BDDatePicker.getValue() == null || UpdateOrderFxml_BookIDComboBox.getValue() == null || UpdateOrderFxml_BorrowerIDComboBox.getValue() == null || UpdateOrderFxml_OrderIDComboBox.getValue() == null) {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setHeaderText("Don't leave any text field is Empty!");
                a.show();
            } else {
                try {
                    a.setAlertType(Alert.AlertType.CONFIRMATION);
                    a.setHeaderText("are you sure?");
                    Optional<ButtonType> result = a.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        java.sql.Date BD = java.sql.Date.valueOf(UpdateOrderFxml_BDDatePicker.getValue());
                        java.sql.Date RD = java.sql.Date.valueOf(UpdateOrderFxml_RDDatePicker.getValue());
                        Integer BrID = UpdateOrderFxml_BorrowerIDComboBox.getValue();
                        Integer BoID = UpdateOrderFxml_BookIDComboBox.getValue();
                        Integer OrID = UpdateOrderFxml_OrderIDComboBox.getValue();
                        String sql = "update orders set book_id = " + BoID + ",borrower_id = " + BrID + ",borrowing_date = '" + BD + "',return_date = '" + RD + "' where order_id = " + OrID;
                        int OrderUpdated = OrderStatement4.executeUpdate(sql);
                        if (OrderUpdated > 0) {
                            a.setAlertType(Alert.AlertType.INFORMATION);
                            a.setHeaderText("The Order has been Updated Successfully!");
                            MainPageController.log_File("an order has been updated successfully, the order details:\n " + "Book ID: " + BoID + ", Borrower ID: " + BrID + ", Borrowing Date: " + BD + ", Return Date: " + RD + "\n");
                            a.showAndWait();
                            paneNum = 1;
                            Stage stage = (Stage) UpdateOrderFxml_ButtonCancel.getScene().getWindow();
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
        } else if (event.getSource() == UpdateOrderFxml_ButtonCancel) {
            MainPageController.log_File("updating an order has been cancelled \n");
            paneNum = 1;
            Stage stage = (Stage) UpdateOrderFxml_ButtonCancel.getScene().getWindow();
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
