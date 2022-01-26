package Model;

import DBAccess.DBCustomer;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.Month;
/**The list class used for defining lists used throughout the program.*/
public class Lists {

    /**List of all contacts*/
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    //private static ObservableList<User> allUsers = FXCollections.observableArrayList();
    /**List of all countries*/
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();

    /**List of appointment times*/
    private static ObservableList<LocalTime> apptTimes = FXCollections.observableArrayList();


    /**Method to add contact to list allContacts*/
    public static void addContact(Contact contact) {
        allContacts.add(contact);
    }


/** Method to add country to list allCountries*/
    public static void addCountry(Country country) {
        allCountries.add(country);
    }

    /**Method to populate list allContacts*/
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        String selectStatement2 = "SELECT * FROM contacts";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(selectStatement2);

            ResultSet rs = ps.executeQuery();

            //Forward scroll ResultSet
            while (rs.next()) //is true will iterate until false(all results obtained)
            {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                Contact contact = new Contact(contactID, contactName);
                allContacts.add(contact);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allContacts;

    }


    /**Method to look up customer by customer name in allCustomers list*/
    public static ObservableList<Customer> lookupCustomer(String partialPartName) {
        ObservableList<Customer> customerName = FXCollections.observableArrayList();

        ObservableList<Customer> allCustomers = DBCustomer.getAllCustomers();

        for (Customer c : allCustomers) {
            if (c.getCustomerName().contains(partialPartName)) {
                customerName.add(c);
            }
        }

        return customerName;

    }
/** Method to search customer by customer ID in allCustomers list*/
    public static Customer searchCustomerByID(int customerID) {
        for (Customer c : DBCustomer.getAllCustomers()) {
            if (c.getCustomerID() == customerID)
                return c;
        }
        return null;
    }

    /**Appointment notification method to cut down redundancy in cod where Information Alerts are used*/
    public static void apptNotification(String notificationType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Notification");
        alert.setContentText(notificationType);
        alert.showAndWait();
    }

    /**This method creates an Observable list of Month objects to populate the Month combo box in reports*/
    public static ObservableList<Month> getMonthValues() {
        ObservableList<Month> monthValues = FXCollections.observableArrayList();
        for (Month m : Month.values()) {
            monthValues.add(m);
        }
        return monthValues;
    }

}













