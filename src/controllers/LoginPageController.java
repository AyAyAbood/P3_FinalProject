/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import entities.user;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AboOod_shbika99
 */
public class LoginPageController implements Initializable {

    @FXML
    private Button LoginButton;
    Statement statement;
    ArrayList<user> users = new ArrayList<>();
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    Alert a = new Alert(Alert.AlertType.NONE);
    private FontAwesomeIcon ExitButton;
    @FXML
    private Button exit;
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
            this.statement = connection.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            importUsers();
        } catch (SQLException ex) {
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleClicks(ActionEvent event) throws IOException {
        if (event.getSource() == LoginButton) {
            if (!usernameTextField.getText().equals("") && !passwordTextField.getText().equals("")) {
                String usernameT = usernameTextField.getText();
                String passwordT = passwordTextField.getText();
                boolean found = false;
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getUsername().equals(usernameT) && users.get(i).getPassword().equals(md5(passwordT))) {
                        a.setAlertType(Alert.AlertType.INFORMATION);
                        a.setHeaderText("Login Successful!");
                        MainPageController.log_File("the user has logged in \n");
                        a.showAndWait();
                        Stage stage = (Stage) LoginButton.getScene().getWindow();
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
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setHeaderText("the username or Password are incorrect!");
                    a.show();
                }
            } else {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setHeaderText("Don't leave any field empty!");
                a.show();
            }
        } else if (event.getSource() == exit) {
            MainPageController.log_File("the user exited the program \n");
            System.exit(0);
        }
    }

    private void importUsers() throws SQLException {
        ResultSet rs = this.statement.executeQuery("Select * From users");
        users.clear();
        while (rs.next()) {
            user u = new user();
            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            users.add(u);
        }
        rs.close();
    }

    private void ExitButtonHandler(MouseEvent event) {
        if (event.getSource() == ExitButton) {
            MainPageController.log_File("the user exited the program \n");
            System.exit(0);
        }
    }
    
    private String md5(String pass){
         MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(StandardCharsets.UTF_8.encode(pass));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert md5 != null;
        return String.format("%032x", new BigInteger(1,md5.digest()));
    }

}
