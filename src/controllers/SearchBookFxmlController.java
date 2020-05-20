package controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static controllers.MainPageController.paneNum;
import entities.book;
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
public class SearchBookFxmlController implements Initializable {

    @FXML
    private TextField SearchBookFxml_textField;
    @FXML
    private Button SearchBookFxml_ButtonSearch;
    @FXML
    private Button SearchBookFxml_ButtonCancel;
    Alert a = new Alert(Alert.AlertType.NONE);
    Statement BookStatement1;
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
            this.BookStatement1 = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleSearchBookClicks(ActionEvent event) throws IOException, SQLException {
        if (event.getSource() == SearchBookFxml_ButtonSearch) {
            a.setHeaderText("");
            a.setContentText("");
            if (SearchBookFxml_textField.getText().equals("")) {
                a.setAlertType(AlertType.WARNING);
                a.setHeaderText("The text field is Empty!");
                a.show();
            } else {
                try {
                    String sql = "select * from books where id = " + SearchBookFxml_textField.getText();
                    ResultSet BookRs = BookStatement1.executeQuery(sql);
                    if (BookRs.next()) {
                        int id = BookRs.getInt("id");
                        String title = BookRs.getString("title");
                        String author = BookRs.getString("author");
                        Double price = BookRs.getDouble("price");
                        String publisher = BookRs.getString("publisher");
                        String genre = BookRs.getString("genre");
                        Date publication_Date = BookRs.getDate("publication_date");
                        String language = BookRs.getString("language");
                        book bo = new book(id, title, author, price, publisher, genre, publication_Date, language);
                        a.setAlertType(AlertType.INFORMATION);
                        a.setHeaderText("The Book has been found                                                                         ");
                        MainPageController.log_File("the user searched for this book:\n " + "title: " + title + ", author: " + author + ", price: " + price + ", publisher: " + publisher + ", genre: " + genre + ", publication Date: " + publication_Date + ", language: " + language + "\n");
                        a.setContentText(bo.toString());
                        a.showAndWait();
                        paneNum = 3;
                        Stage stage = (Stage) SearchBookFxml_ButtonCancel.getScene().getWindow();
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
                        a.setHeaderText("The Book does not exist!");
                        a.show();
                    }
                } catch (Exception ex) {
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setHeaderText("insert ONLY integer values for the ID text field!");
                    a.show();
                }
            }
        } else if (event.getSource() == SearchBookFxml_ButtonCancel) {
            MainPageController.log_File("searching for a book has been cancelled \n");
            paneNum = 3;
            Stage stage = (Stage) SearchBookFxml_ButtonCancel.getScene().getWindow();
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
