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
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AboOod_shbika99
 */
public class AddBookFxmlController implements Initializable {

    @FXML
    private TextField AddBookFxml_TitleTextField;
    @FXML
    private TextField AddBookFxml_AuthorTextField;
    @FXML
    private TextField AddBookFxml_PriceTextField;
    @FXML
    private TextField AddBookFxml_PublisherTextField;
    @FXML
    private TextField AddBookFxml_GenreTextField;
    @FXML
    private DatePicker AddBookFxml_PDTextField;
    //String date = AddBookFxml_PDTextField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    @FXML
    private TextField AddBookFxml_LanguageTextField;
    @FXML
    private Button AddBookFxml_ButtonAdd;
    @FXML
    private Button AddBookFxml_ButtonCancel;
    Alert a = new Alert(Alert.AlertType.NONE);
    Statement BookStatement3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library_management?serverTimeZone=UTC", "root", "");
            this.BookStatement3 = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleAddBookClicks(ActionEvent event) throws IOException, SQLException {
        if (event.getSource() == AddBookFxml_ButtonAdd) {
            a.setHeaderText("");
            a.setContentText("");
            if (AddBookFxml_TitleTextField.getText().equals("") || AddBookFxml_AuthorTextField.getText().equals("") || AddBookFxml_PriceTextField.getText().equals("") || AddBookFxml_PublisherTextField.getText().equals("") || AddBookFxml_GenreTextField.getText().equals("") || AddBookFxml_LanguageTextField.getText().equals("") || AddBookFxml_PDTextField.getValue() == null) {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setHeaderText("Don't leave any text field is Empty!");
                a.show();
            } else {
                try {
                    String title = AddBookFxml_TitleTextField.getText();
                    String author = AddBookFxml_AuthorTextField.getText();
                    Double price = Double.valueOf(AddBookFxml_PriceTextField.getText());
                    String publisher = AddBookFxml_PublisherTextField.getText();
                    String genre = AddBookFxml_GenreTextField.getText();
                    java.sql.Date publication_Date = java.sql.Date.valueOf(AddBookFxml_PDTextField.getValue());
                    String language = AddBookFxml_LanguageTextField.getText();
                    String sql = "insert into books values (" + null + ",'" + title + "','" + author + "'," + price + ",'" + publisher + "','" + genre + "','" + publication_Date + "','" + language + "');";
                    int BookAdded = BookStatement3.executeUpdate(sql);
                    if (BookAdded > 0) {
                        a.setAlertType(Alert.AlertType.INFORMATION);
                        a.setHeaderText("The Book has been Added Successfully!");
                        a.showAndWait();
                        paneNum = 3;
                        Stage stage = (Stage) AddBookFxml_ButtonCancel.getScene().getWindow();
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
                    //Logger.getLogger(AddBookFxmlController.class.getName()).log(Level.SEVERE, null, ex);
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setHeaderText("something went wrong......");
                    a.show();
                }
            }
        } else if (event.getSource() == AddBookFxml_ButtonCancel) {
            paneNum = 3;
            Stage stage = (Stage) AddBookFxml_ButtonCancel.getScene().getWindow();
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
