package DBAccess;

import Model.Appointment;
import Model.Customer;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
/**This class defines database/customer interactions.*/
public class DBCustomer {
    /**Declare ObservableList allCustomers and associatedAppts*/
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private ObservableList<Appointment> associatedAppts = FXCollections.observableArrayList();

    /**Method to add customer to allCustomers list*/
    public static void addCustomer(Customer customer) {
        allCustomers.add(customer);
    }

    /**This method adds a customer into the database*/
    public static void addCustomer(String customerName, String customerAddress, String postalCode, String phoneNumber, int divID) throws SQLException {
        String insertStatement = "INSERT into customers(Customer_Name, Address, Postal_Code, Phone, Division_ID)" +
                "VALUES (?, ?, ?, ?, ?)";
        //Create prepared statement object
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(insertStatement);
        //key-value mapping
        ps.setString(1, customerName);
        ps.setString(2, customerAddress);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setInt(5, divID);

        ps.execute();

        //Check rows affected
        if(ps.getUpdateCount() > 0)
            System.out.println(ps.getUpdateCount() + " row(s) affected.");

    }

    /**Method to access customers from database and return an observable array list of all customers*/
    public static ObservableList<Customer> getAllCustomers()  {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String selectStatement1 = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, Division, Country, first_level_divisions.COUNTRY_ID\n" +
                "FROM customers, first_level_divisions, countries\n" +
                "WHERE customers.Division_ID = first_level_divisions.Division_ID\n" +
                "AND first_level_divisions.COUNTRY_ID = countries.Country_ID;";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(selectStatement1);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                String firstLevelDiv = rs.getString("Division");
                int divID = rs.getInt("Division_ID");
                String countryName = rs.getString("Country");
                int countryID = rs.getInt("COUNTRY_ID");


                Customer customer = new Customer(customerID, customerName, customerAddress, postalCode, phoneNumber, firstLevelDiv, divID, countryID, countryName );
                allCustomers.add(customer);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allCustomers;

    }
    /**This method updates a customer in the database*/
    public static void updateCustomer(int customerID, String customerName, String customerAddress, String postalCode, String phoneNumber, int divID) throws SQLException {
        String insertStatement = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        //Create prepared statement object
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(insertStatement);
        //key-value mapping
        ps.setString(1, customerName);
        ps.setString(2, customerAddress);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setInt(5, divID);
        ps.setInt(6,customerID);
        ps.execute();

        //Check rows affected
        if(ps.getUpdateCount() > 0)
            System.out.println(ps.getUpdateCount() + " row(s) affected.");

    }
    /**This method deletes a customer*/
    public static void deleteCustomer(int customerID) throws SQLException {
        String deleteStatement = "DELETE FROM customers WHERE Customer_ID = ?";
        //Create prepared statement object
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(deleteStatement);
        //key-value mapping
        ps.setInt(1, customerID);

        ps.execute();

        //Check rows affected
        if(ps.getUpdateCount() > 0)
            System.out.println(ps.getUpdateCount() + " row(s) affected.");

    }
    /**This method returns an Observable list of all appointments associated with a specific customer.*/
    public static ObservableList<Appointment> getAllAssociatedAppts(int customerID){
        ObservableList<Appointment> associatedAppts = FXCollections.observableArrayList();
        String selectStatement1 = "SELECT appointments.Appointment_ID, Title, Description, Location, Type, Start, End, User_ID, customers.Customer_Name FROM appointments, customers\n" +
                "WHERE appointments.Customer_ID = customers.Customer_ID\n" +
                "AND appointments.Customer_ID = ?;";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(selectStatement1);
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();

            //Forward scroll ResultSet
            while (rs.next()) //is true will iterate until false(all results obtained)
            {
                int apptID = rs.getInt("Appointment_ID");
                String apptTitle = rs.getString("Title");
                String apptDescription = rs.getString("Description");
                Timestamp aptStart = rs.getTimestamp("Start");
                Timestamp aptEnd = rs.getTimestamp("End");
                String apptType = rs.getString("Type");
                String apptLocation = rs.getString("Location");
                String customerName = rs.getString("Customer_Name");
                int assocUserID = rs.getInt("User_ID");

                LocalDateTime apptStart = aptStart.toLocalDateTime();
                LocalDateTime apptEnd = aptEnd.toLocalDateTime();


                String assocContactName = null;
                Appointment appointment = new Appointment( apptID, apptTitle, apptDescription, apptLocation, apptType, null, apptStart, apptEnd, customerID, customerName, assocUserID);
                associatedAppts.add(appointment);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(associatedAppts); //Take this out later
        return associatedAppts;
    }

    public void addAssociatedAppts(Appointment appointment){

        associatedAppts.add(appointment);
    }



}
