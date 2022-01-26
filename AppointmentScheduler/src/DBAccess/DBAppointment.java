package DBAccess;

import Model.Appointment;
import Utility.DBConnection;
import com.google.protobuf.GeneratedMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**This class defines database/appointment interactions*/
public class DBAppointment {
    /**Declares an ObservableList of all appointments*/
    private static ObservableList<Appointment> allAppts = FXCollections.observableArrayList();

    /**This method adds appointments to allAppts list*/
    public static void addAppt(Appointment appointment) {
        allAppts.add(appointment);
    }
/**This method adds appointments to the database.*/
    public static void addAppointment(String apptTitle, String apptDescription, String apptLocation,
                                      String apptType, LocalDateTime apptStart, LocalDateTime apptEnd, int customerID, int contactID){

        try {
            Timestamp aStart = Timestamp.valueOf(apptStart);
            Timestamp aEnd = Timestamp.valueOf(apptEnd);


            String insertStatement = "INSERT into appointments(Title, Description, Location, Type, Start, End, Customer_ID, Contact_ID)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            //Create prepared statement object
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(insertStatement);

            //key-value mapping
            ps.setString(1, apptTitle);
            ps.setString(2, apptDescription);
            ps.setString(3, apptLocation);
            ps.setString(4, apptType);
            ps.setTimestamp(5, aStart);
            ps.setTimestamp(6, aEnd);
            ps.setInt(7, customerID);
            ps.setInt(8, contactID);
            ps.execute();

        } catch(SQLException e) {
            e.printStackTrace();

        }
    }
/**This method returns an Observable List of all appointments.*/
    public static ObservableList<Appointment> getAllAppts() {
        ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
        String selectStatement = "SELECT contacts.Contact_Name, Appointment_ID, Title, Description, Location, Type, Start, End, \n" +
                "appointments.Customer_ID, User_ID, customers.Customer_Name\n" +
                "FROM appointments, customers, contacts\n" +
                "WHERE appointments.Customer_ID = customers.Customer_ID\n" +
                "AND appointments.Contact_ID = contacts.Contact_ID;";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(selectStatement);

            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                int apptID = rs.getInt("Appointment_ID");
                String apptTitle = rs.getString("Title");
                String apptDescription = rs.getString("Description");
                String apptLocation = rs.getString("Location");
                String apptType = rs.getString("Type");
                String assocContactName = rs.getString("Contact_Name");
                Timestamp aptStart = rs.getTimestamp("Start");
                Timestamp aptEnd = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                int assocUserID = rs.getInt("User_ID");

                LocalDateTime apptStart = aptStart.toLocalDateTime();
                LocalDateTime apptEnd = aptEnd.toLocalDateTime();

                Appointment appointment = new Appointment(apptID, apptTitle, apptDescription, apptLocation, apptType, assocContactName, apptStart, apptEnd, customerID, customerName, assocUserID);

                allAppts.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allAppts;    }
    /**This method is used to update appointments*/
    public static void updateAppt(int apptID,String apptTitle, String apptDescription, String apptLocation,
    String apptType, LocalDateTime apptStart, LocalDateTime apptEnd, int customerID, int contactID) {
        String updateStatement2 = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?;";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(updateStatement2);

            //key-value mapping
            ps.setString(1, apptTitle);
            ps.setString(2, apptDescription);
            ps.setString(3, apptLocation);
            ps.setString(4, apptType);
            ps.setTimestamp(5, Timestamp.valueOf(apptStart));
            ps.setTimestamp(6, Timestamp.valueOf(apptEnd));
            ps.setInt(7, customerID);
            ps.setInt(8, contactID);
            ps.setInt(9, apptID);

            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


/**This method is used to delete appointments*/
    public static void deleteAppt(int apptID) throws SQLException {
        String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";
        //Create prepared statement object
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(deleteStatement);
        //key-value mapping
        ps.setInt(1, apptID);

        ps.execute();

        //Check rows affected
        if(ps.getUpdateCount() > 0)
            System.out.println(ps.getUpdateCount() + " row(s) affected.");

    }



}
