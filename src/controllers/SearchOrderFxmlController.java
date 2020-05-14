package controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static controllers.MainPageController.paneNum;
import entities.orders;
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
import javafx.scene.control.Alert.AlertType;
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
public class SearchOrderFxmlController implements Initializable {

    @FXML
    private TextField SearchOrderFxml_textField;
    @FXML
    private Button SearchOrderFxml_ButtonSearch;
    @FXML
    private Button SearchOrderFxml_ButtonCancel;
    Alert a = new Alert(Alert.AlertType.NONE);
    Statement OrderStatement1;
    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library_management?serverTimeZone=UTC", "root", "");
            this.OrderStatement1 = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleSearchOrderClicks(ActionEvent event) throws IOException, SQLException {
        if (event.getSource() == SearchOrderFxml_ButtonSearch) {
            a.setHeaderText("");
            a.setContentText("");
            if (SearchOrderFxml_textField.getText().equals("")) {
                a.setAlertType(AlertType.WARNING);
                a.setHeaderText("The text field is Empty!");
                a.show();
            } else {
                try {
                    String sql = "select * from orders where order_id = " + SearchOrderFxml_textField.getText();
                    ResultSet OrderRs = OrderStatement1.executeQuery(sql);
                    if (OrderRs.next()) {
                        int Order_id = OrderRs.getInt("order_id");
                        int Book_id = OrderRs.getInt("book_id");
                        int Borrower_id = OrderRs.getInt("borrower_id");
                        Date Borrowing_date = OrderRs.getDate("borrowing_date");
                        Date Return_date = OrderRs.getDate("return_date");
                        orders or = new orders(Order_id, Book_id, Borrower_id, Borrowing_date, Return_date);
                        a.setAlertType(AlertType.INFORMATION);
                        a.setHeaderText("The Order has been found                                                                         ");
                        a.setContentText(or.toString());
                        a.showAndWait();
                        paneNum = 1;
                        Stage stage = (Stage) SearchOrderFxml_ButtonCancel.getScene().getWindow();
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
                        a.setHeaderText("The Order does not exist!");
                        a.show();
                    }
                } catch (SQLException ex) {
                    //Logger.getLogger(SearchOrderFxmlController.class.getName()).log(Level.SEVERE, null, ex);
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setHeaderText("insert ONLY integer values for the ID text field!");
                    a.show();
                }
            }
        } else if (event.getSource() == SearchOrderFxml_ButtonCancel) {
            paneNum = 1;
            Stage stage = (Stage) SearchOrderFxml_ButtonCancel.getScene().getWindow();
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
