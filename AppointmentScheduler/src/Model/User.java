package Model;

import Controller.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**This class is designed to create/define User objects. The user data is obtained from the database;therefore, no setters are used.*/
public class User {


    /**Declare fields*/
    private int userID;
    private String userName;
    private String userPassword;


    /**This is the User constructor*/
    public User(int userID, String userName, String userPassword) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }

/**
  @return the userID
 * */
    public int getUserID() {
        return userID;
    }
/**
 @return the userName
 * */
    public String getUserName() {
        return userName;
    }
/**
 @return the userPassword
 * */
    public String getUserPassword() {
        return userPassword;
    }
 /**
   This method returns the string value of the user ID for use in the combo box.
  * */
    @Override
    public String toString() {
        return (" " + userID + " ");
    }
}


