package DBAccess;

import Model.User;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**This class is used to define database/user interactions.*/
public class DBUser {
    /**This method returns an observable list of all users.*/
    public static ObservableList<User> getAllUsers(){
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        String userSelect = "SELECT * FROM users;";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(userSelect);

            ResultSet rs = ps.executeQuery();
            //Forward scroll ResultSet
            while (rs.next()) //is true will iterate until false(all results obtained)
            {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");
                User user = new User(userID, userName,userPassword);
                allUsers.add(user);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allUsers;

    }
    /**This method return a User object given the userID as the argument*/
    public static User getUserInfo(int userID){
        User selectedUser = null;
        String userSelect = "SELECT * FROM users WHERE User_ID = ?;";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(userSelect);
            ps.setInt(1, userID);

            ResultSet rs = ps.executeQuery();

            //Forward scroll ResultSet
            while (rs.next()) //is true will iterate until false(all results obtained)
            {
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");

                User s = new User(userID, userName,userPassword);
                selectedUser = s;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return selectedUser;

    }





}
