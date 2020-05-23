/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.time.LocalDate;
import entities.book;
import entities.borrowers;
import entities.orders;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author AboOod_shbika99
 */
public class MainPageController implements Initializable {

    @FXML
    private FontAwesomeIcon ExitButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button booksButton;
    @FXML
    private Button borrowersButton;
    @FXML
    private Button ordersButton;
    @FXML
    private TableView<book> booksTableView;
    @FXML
    private Button SearchBookButton;
    @FXML
    private Button DeleteBookButton;
    @FXML
    private Button UpdateBookButton;
    @FXML
    private Button AddBookButton;
    @FXML
    private Pane BooksPane;
    @FXML
    private Pane BorrowersPane;
    @FXML
    private FontAwesomeIcon ExitButton1;
    @FXML
    private Pane OrdersPane;
    @FXML
    private FontAwesomeIcon ExitButton2;
    @FXML
    private TableColumn<book, Integer> booksTcId;
    @FXML
    private TableColumn<book, String> booksTcTitle;
    @FXML
    private TableColumn<book, String> booksTcAuthor;
    @FXML
    private TableColumn<book, Double> booksTcPrice;
    @FXML
    private TableColumn<book, String> booksTcPublisher;
    @FXML
    private TableColumn<book, String> booksTcGenre;
    @FXML
    private TableColumn<book, Date> booksTcPublicationDate;
    @FXML
    private TableColumn<book, String> booksTcLanguage;
    @FXML
    private TableView<borrowers> borrowerTableView;
    @FXML
    private TableColumn<borrowers, Integer> borrowerTcId;
    @FXML
    private TableColumn<borrowers, String> borrowersTcFirstName;
    @FXML
    private TableColumn<borrowers, String> borrowersTcLastName;
    @FXML
    private TableColumn<borrowers, Integer> borrowersTcPhone;
    @FXML
    private TableColumn<borrowers, Integer> borrowersTcAge;
    @FXML
    private TableColumn<borrowers, String> borrowersTcEmail;
    @FXML
    private TableColumn<borrowers, String> borrowersTcAddress;
    @FXML
    private TableColumn<borrowers, String> borrowersTcGender;
    @FXML
    private Button SearchBorrowerButton;
    @FXML
    private Button DeleteBorrowerButton;
    @FXML
    private Button UpdateBorrowerButton;
    @FXML
    private Button AddBorrowerButton;
    @FXML
    private TableView<orders> ordersTableView;
    @FXML
    private TableColumn<orders, Integer> ordersTcOrderId;
    @FXML
    private TableColumn<orders, Integer> ordersTcBookId;
    @FXML
    private TableColumn<orders, Integer> ordersTcBorrowerId;
    @FXML
    private TableColumn<orders, Date> ordersTcBorrowingDate;
    @FXML
    private TableColumn<orders, Date> ordersTcReturnDate;
    @FXML
    private Button SearchOrderButton;
    @FXML
    private Button DeleteOrderButton;
    @FXML
    private Button UpdateOrderButton;
    @FXML
    private Button AddOrderButton;
    Statement statement;
    List<book> listBooks = new LinkedList<>();
    List<borrowers> listBorrowers = new LinkedList<>();
    List<orders> listOrders = new LinkedList<>();
    public static int paneNum = 0;
    @FXML
    private Button ClearOrderButton;
    @FXML
    private Button ClearBorrowerButton;
    @FXML
    private Button ClearBookButton;
    Alert ab = new Alert(Alert.AlertType.NONE);

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
        booksTcId.setCellValueFactory(new PropertyValueFactory("id"));
        booksTcTitle.setCellValueFactory(new PropertyValueFactory("title"));
        booksTcAuthor.setCellValueFactory(new PropertyValueFactory("author"));
        booksTcPrice.setCellValueFactory(new PropertyValueFactory("price"));
        booksTcPublisher.setCellValueFactory(new PropertyValueFactory("publisher"));
        booksTcGenre.setCellValueFactory(new PropertyValueFactory("genre"));
        booksTcPublicationDate.setCellValueFactory(new PropertyValueFactory("publication_date"));
        booksTcLanguage.setCellValueFactory(new PropertyValueFactory("language"));

        borrowerTcId.setCellValueFactory(new PropertyValueFactory("id"));
        borrowersTcFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
        borrowersTcLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        borrowersTcPhone.setCellValueFactory(new PropertyValueFactory("phone"));
        borrowersTcAge.setCellValueFactory(new PropertyValueFactory("age"));
        borrowersTcEmail.setCellValueFactory(new PropertyValueFactory("email"));
        borrowersTcAddress.setCellValueFactory(new PropertyValueFactory("address"));
        borrowersTcGender.setCellValueFactory(new PropertyValueFactory("gender"));

        ordersTcOrderId.setCellValueFactory(new PropertyValueFactory("order_id"));
        ordersTcBookId.setCellValueFactory(new PropertyValueFactory("book_id"));
        ordersTcBorrowerId.setCellValueFactory(new PropertyValueFactory("borrower_id"));
        ordersTcBorrowingDate.setCellValueFactory(new PropertyValueFactory("borrowing_date"));
        ordersTcReturnDate.setCellValueFactory(new PropertyValueFactory("return_date"));

        try {
            showBooks();
            showBorrowers();
            showOrders();
        } catch (SQLException ex) {
            Logger.getLogger(controllers.MainPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        switch (paneNum) {
            case 1:
                OrdersPane.toFront();
                break;
            case 2:
                BorrowersPane.toFront();
                break;
            case 3:
                BooksPane.toFront();
                break;
            default:
                break;
        }
    }

    @FXML
    private void handleClicks(ActionEvent event) throws IOException {
        ab.setHeaderText("");
        ab.setContentText("");
        if (event.getSource() == closeButton) {
            log_File("the user closed the program \n");
            System.exit(0);
        } else if (event.getSource() == booksButton) {
            BooksPane.toFront();
        } else if (event.getSource() == borrowersButton) {
            BorrowersPane.toFront();
        } else if (event.getSource() == ordersButton) {
            OrdersPane.toFront();
        } else if (event.getSource() == SearchBookButton) {
            Stage stage = (Stage) SearchBookButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/SearchBookFxml.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } else if (event.getSource() == DeleteBookButton) {
            book boo = booksTableView.getSelectionModel().getSelectedItem();
            if (boo != null) {
                try {
                    ab.setAlertType(Alert.AlertType.CONFIRMATION);
                    ab.setHeaderText("are you sure?");
                    Optional<ButtonType> result = ab.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        String sql = "delete from books where id=" + boo.getId();
                        this.statement.executeUpdate(sql);
                        ab.setAlertType(Alert.AlertType.INFORMATION);
                        ab.setHeaderText("The Book has been Deleted Successfully!");
                        ab.showAndWait();
                        booksTableView.getItems().clear();
                        showBooks();
                    }
                } catch (SQLException ex) {
                    ab.setAlertType(Alert.AlertType.ERROR);
                    ab.setHeaderText("Book can't be deleted due to it having a primary key-foreign key relationship with some orders. ");
                    ab.show();
                }
            } else {
                Stage stage = (Stage) DeleteBookButton.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/DeleteBookFxml.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
            }
        } else if (event.getSource() == UpdateBookButton) {
            Stage stage = (Stage) UpdateBookButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/UpdateBookFxml.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } else if (event.getSource() == AddBookButton) {
            Stage stage = (Stage) AddBookButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/AddBookFxml.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } else if (event.getSource() == SearchBorrowerButton) {
            Stage stage = (Stage) SearchBorrowerButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/SearchBorrowerFxml.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } else if (event.getSource() == DeleteBorrowerButton) {
            borrowers bro = borrowerTableView.getSelectionModel().getSelectedItem();
            if (bro != null) {
                try {
                    ab.setAlertType(Alert.AlertType.CONFIRMATION);
                    ab.setHeaderText("are you sure?");
                    Optional<ButtonType> result = ab.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        String sql = "delete from borrowers where id=" + bro.getId();
                        this.statement.executeUpdate(sql);
                        ab.setAlertType(Alert.AlertType.INFORMATION);
                        ab.setHeaderText("The Borrowers has been Deleted Successfully!");
                        ab.showAndWait();
                        borrowerTableView.getItems().clear();
                        showBorrowers();
                    }
                } catch (SQLException ex) {
                    ab.setAlertType(Alert.AlertType.ERROR);
                    ab.setHeaderText("Borrower can't be deleted due to it having a primary key-foreign key relationship with some orders. ");
                    ab.show();
                }
            } else {
                Stage stage = (Stage) DeleteBorrowerButton.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/DeleteBorrowerFxml.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
            }
        } else if (event.getSource() == UpdateBorrowerButton) {
            Stage stage = (Stage) UpdateBorrowerButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/UpdateBorrowerFxml.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } else if (event.getSource() == AddBorrowerButton) {
            Stage stage = (Stage) AddBorrowerButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/AddBorrowerFxml.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } else if (event.getSource() == AddOrderButton) {
            Stage stage = (Stage) AddOrderButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/AddOrderFxml.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } else if (event.getSource() == SearchOrderButton) {
            Stage stage = (Stage) SearchOrderButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/SearchOrderFxml.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } else if (event.getSource() == UpdateOrderButton) {
            Stage stage = (Stage) UpdateOrderButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/UpdateOrderFxml.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } else if (event.getSource() == DeleteOrderButton) {
            orders or = ordersTableView.getSelectionModel().getSelectedItem();
            if (or != null) {
                try {
                    ab.setAlertType(Alert.AlertType.CONFIRMATION);
                    ab.setHeaderText("are you sure?");
                    Optional<ButtonType> result = ab.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        String sql = "delete from orders where order_id=" + or.getOrder_id();
                        ab.setAlertType(Alert.AlertType.INFORMATION);
                        ab.setHeaderText("The Order has been Deleted Successfully!");
                        ab.showAndWait();
                        this.statement.executeUpdate(sql);
                        ordersTableView.getItems().clear();
                        showOrders();
                    }
                } catch (SQLException ex) {
                    ab.setAlertType(Alert.AlertType.ERROR);
                    ab.setHeaderText("something went wrong.......");
                    ab.show();
                }
            } else {
                Stage stage = (Stage) DeleteOrderButton.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/DeleteOrderFxml.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
            }
        } else if (event.getSource() == ClearBookButton) {
            String sql = "delete from books";
            try {
                ab.setAlertType(Alert.AlertType.CONFIRMATION);
                ab.setHeaderText("are you sure?");
                Optional<ButtonType> result = ab.showAndWait();
                if (result.get() == ButtonType.OK) {
                    int rows = this.statement.executeUpdate(sql);
                    if (rows > 0) {
                        ab.setAlertType(Alert.AlertType.INFORMATION);
                        ab.setHeaderText("The Books have been cleared");
                        log_File("Books have been cleared \n");
                        ab.show();
                    } else {
                        ab.setAlertType(Alert.AlertType.INFORMATION);
                        ab.setHeaderText("The Books are Empty!");
                        ab.show();
                    }
                    showBooks();
                }
            } catch (Exception e) {
                ab.setAlertType(Alert.AlertType.ERROR);
                ab.setHeaderText("books can't be cleared due to some books having primary key-foreign key relationship with some orders. ");
                ab.show();
            }
        } else if (event.getSource() == ClearBorrowerButton) {
            String sql = "delete from borrowers";
            try {
                ab.setAlertType(Alert.AlertType.CONFIRMATION);
                ab.setHeaderText("are you sure?");
                Optional<ButtonType> result = ab.showAndWait();
                if (result.get() == ButtonType.OK) {
                    int rows = this.statement.executeUpdate(sql);
                    if (rows > 0) {
                        ab.setAlertType(Alert.AlertType.INFORMATION);
                        ab.setHeaderText("The Borrowers have been cleared");
                        log_File("Borrowers have been cleared \n");
                        ab.show();
                    } else {
                        ab.setAlertType(Alert.AlertType.INFORMATION);
                        ab.setHeaderText("The Borrowers are Empty!");
                        ab.show();
                    }
                    showBorrowers();
                }
            } catch (Exception e) {
                ab.setAlertType(Alert.AlertType.ERROR);
                ab.setHeaderText("Borrowers can't be cleared due to some borrowers having primary key-foreign key relationship with some orders. ");
                ab.show();
            }
        } else if (event.getSource() == ClearOrderButton) {
            String sql = "delete from orders";
            try {
                ab.setAlertType(Alert.AlertType.CONFIRMATION);
                ab.setHeaderText("are you sure?");
                Optional<ButtonType> result = ab.showAndWait();
                if (result.get() == ButtonType.OK) {
                    int rows = this.statement.executeUpdate(sql);
                    if (rows > 0) {
                        ab.setAlertType(Alert.AlertType.INFORMATION);
                        ab.setHeaderText("The Orders have been cleared");
                        log_File("Orders have been cleared \n");
                        ab.show();
                    } else {
                        ab.setAlertType(Alert.AlertType.INFORMATION);
                        ab.setHeaderText("The Orders are Empty!");
                        ab.show();
                    }
                    showOrders();
                }
            } catch (Exception e) {
                ab.setAlertType(Alert.AlertType.ERROR);
                ab.setHeaderText("something went wrong.......");
                ab.show();
            }
        }
    }

    @FXML
    private void ExitButtonHandler(MouseEvent event) {
        if (event.getSource() == ExitButton || event.getSource() == ExitButton1 || event.getSource() == ExitButton2) {
            log_File("the user closed the program \n");
            System.exit(0);
        }
    }

    private void showBooks() throws SQLException {
        ResultSet rs = this.statement.executeQuery("Select * From books");
        booksTableView.getItems().clear();
        while (rs.next()) {
            book b = new book();
            b.setId(rs.getInt("id"));
            b.setTitle(rs.getString("title"));
            b.setAuthor(rs.getString("author"));
            b.setPrice(rs.getDouble("price"));
            b.setPublisher(rs.getString("publisher"));
            b.setGenre(rs.getString("genre"));
            b.setPublication_date(rs.getDate("publication_date"));
            b.setLanguage(rs.getString("language"));
            listBooks.add(b);
            booksTableView.getItems().add(b);
        }
        rs.close();
    }

    private void showBorrowers() throws SQLException {
        ResultSet rs = this.statement.executeQuery("Select * From borrowers");
        borrowerTableView.getItems().clear();
        while (rs.next()) {
            borrowers br = new borrowers();
            br.setId(rs.getInt("id"));
            br.setFirstName(rs.getString("firstName"));
            br.setLastName(rs.getString("lastName"));
            br.setPhone(rs.getInt("phone"));
            br.setAge(rs.getInt("age"));
            br.setEmail(rs.getString("email"));
            br.setAddress(rs.getString("address"));
            br.setGender(rs.getString("gender"));
            listBorrowers.add(br);
            borrowerTableView.getItems().add(br);
        }
        rs.close();
    }

    private void showOrders() throws SQLException {
        ResultSet rs = this.statement.executeQuery("Select * From orders");
        ordersTableView.getItems().clear();
        listOrders.clear();
        List<orders> OrderedOrderList = null;
        while (rs.next()) {
            orders or = new orders();
            or.setOrder_id(rs.getInt("order_id"));
            or.setBook_id(rs.getInt("book_id"));
            or.setBorrower_id(rs.getInt("borrower_id"));
            or.setBorrowing_date(rs.getDate("borrowing_date"));
            or.setReturn_date(rs.getDate("return_date"));
            listOrders.add(or);
            //ordersTableView.getItems().add(or);
        }
        OrderedOrderList = listOrders.stream().sorted(Comparator.comparing(orders::getBook_id)).collect(Collectors.toList());
        ordersTableView.getItems().addAll(OrderedOrderList);
        rs.close();
    }

    public static void log_File(String query) {
        File file = new File("logFile.txt");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file, true);
            PrintWriter pw = new PrintWriter(fos);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            java.util.Date date = new java.util.Date(System.currentTimeMillis() + 10800000);
            pw.println(formatter.format(date) + " --> " + query);
            pw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
