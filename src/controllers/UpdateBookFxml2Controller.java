/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import static controllers.MainPageController.paneNum;
import static controllers.UpdateBookFxmlController.globalUpdateBookId;
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
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AboOod_shbika99
 */
public class UpdateBookFxml2Controller implements Initializable {

    @FXML
    private TextField UpdateBookFxml_TitleTextField;
    @FXML
    private TextField UpdateBookFxml_AuthorTextField;
    @FXML
    private TextField UpdateBookFxml_PriceTextField;
    @FXML
    private TextField UpdateBookFxml_PublisherTextField;
    @FXML
    private TextField UpdateBookFxml_GenreTextField;
    @FXML
    private DatePicker UpdateBookFxml_PDTextField;
    @FXML
    private TextField UpdateBookFxml_LanguageTextField;
    @FXML
    private Button UpdateBookFxml_ButtonUpdate2;
    @FXML
    private Button UpdateBookFxml_ButtonCancel2;
    Alert a = new Alert(Alert.AlertType.NONE);
    Statement BookStatement5;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library_management?serverTimeZone=UTC", "root", "");
            this.BookStatement5 = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateBookClicks2(ActionEvent event) throws IOException, SQLException {
        if (event.getSource() == UpdateBookFxml_ButtonUpdate2) {
            a.setHeaderText("");
            a.setContentText("");
            if (UpdateBookFxml_TitleTextField.getText().equals("") || UpdateBookFxml_AuthorTextField.getText().equals("") || UpdateBookFxml_PriceTextField.getText().equals("") || UpdateBookFxml_PublisherTextField.getText().equals("") || UpdateBookFxml_GenreTextField.getText().equals("") || UpdateBookFxml_LanguageTextField.getText().equals("") || UpdateBookFxml_PDTextField.getValue() == null) {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setHeaderText("Don't leave any text field is Empty!");
                a.show();
            } else {
                try {
                    String title = UpdateBookFxml_TitleTextField.getText();
                    String author = UpdateBookFxml_AuthorTextField.getText();
                    Double price = Double.valueOf(UpdateBookFxml_PriceTextField.getText());
                    String publisher = UpdateBookFxml_PublisherTextField.getText();
                    String genre = UpdateBookFxml_GenreTextField.getText();
                    java.sql.Date publication_Date = java.sql.Date.valueOf(UpdateBookFxml_PDTextField.getValue());
                    String language = UpdateBookFxml_LanguageTextField.getText();
                    String sql = "update books set title = '" + title + "',author = '" + author + "',price = " + price + ",publisher = '" + publisher + "',genre = '" + genre + "',publication_date = '" + publication_Date + "',language = '" + language + "' where id = " + globalUpdateBookId;
                    int BookUpdated = BookStatement5.executeUpdate(sql);
                    if (BookUpdated > 0) {
                        a.setAlertType(Alert.AlertType.INFORMATION);
                        a.setHeaderText("The Book has been Updated Successfully!");
                        a.showAndWait();
                        paneNum = 3;
                        Stage stage = (Stage) UpdateBookFxml_ButtonCancel2.getScene().getWindow();
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
                    Logger.getLogger(AddBookFxmlController.class.getName()).log(Level.SEVERE, null, ex);
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setHeaderText("something went wrong......");
                    a.show();
                }
            }
        } else if (event.getSource() == UpdateBookFxml_ButtonCancel2) {
            paneNum = 3;
            Stage stage = (Stage) UpdateBookFxml_ButtonCancel2.getScene().getWindow();
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
