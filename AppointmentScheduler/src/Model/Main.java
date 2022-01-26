package Model;
/**@author Tiffany Rolle */
import Controller.MainMenuController;
import DBAccess.DBAppointment;
import DBAccess.DBCustomer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Utility.DBConnection;

import java.sql.*;
/**This is the main class.*/
public class Main extends Application {
/**This method will take the user to the login display.*/
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/View/login.fxml"));
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }

/**This is the main method. This is the first method that gets called when the program is run. Here methods are called to establish database connection*/
    public static void main(String[] args) throws SQLException
    {

        DBConnection.startConnection();
        DBCustomer.getAllCustomers();
        DBAppointment.getAllAppts();



        launch(args);
        Connection conn = DBConnection.getConnection();


        DBConnection.closeConnection();
    }
}
