package Controller;

import DBAccess.DBAppointment;
import DBAccess.DBCustomer;
import DBAccess.DBUser;
import Interfaces.ReturnContact;
import Interfaces.ReturnCustomer;
import Model.*;
import Utility.ButtonNav;
import Utility.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;
import java.util.TimeZone;
/**This class defines interactions with in the Modify Appointment interface*/
public class ModifyAppointment implements Initializable {
    Stage stage;
    Parent scene;

/**Declare error labels*/
    public Label errorLbl;
    public Label errorLbl2;
    public Label errorLbl3;
/**Declare all text fields*/
    public TextField apptIDTxt;
    public TextField apptTitleTxt;
    public TextField apptDescriptionTxt;
    public TextField apptLocationTxt;
    public TextField apptTypeTxt;
/**Declare combo box and date picker values*/
    public ComboBox<User> userIDCombo;
    public ComboBox<Contact> contactNameCombo;
    public ComboBox<Customer> customerComboBox;
    public ComboBox<LocalTime> startTimeCombo;
    public ComboBox<LocalTime> endTImeCombo;
    public DatePicker datePicker;




/**This method receives appointment data from the selected appointment in the main menu and sets all corresponding fields appropriately.*/
    public void sentAppt(Appointment apptToRecieve) throws SQLException {
        LocalDateTime aStart = apptToRecieve.getApptStart();
        LocalTime sentStartTime = aStart.toLocalTime();
        LocalDateTime aEnd = apptToRecieve.getApptEnd();
        LocalTime sentEndTime = aEnd.toLocalTime();
        int customerID = apptToRecieve.getCustomerID();
        String contactName = apptToRecieve.getAssocContactName();


        /** Lambda returning Customer object used to populate Customer ComboBox given customerID and SQL statement as parameters passed */
        String customerReturn =  "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, Division, Country, first_level_divisions.COUNTRY_ID\n" +
                "                FROM customers, first_level_divisions, countries\n" +
                "\t\t\t\tWHERE customers.Division_ID = first_level_divisions.Division_ID\n" +
                "                AND first_level_divisions.COUNTRY_ID = countries.Country_ID\n" +
                "                AND Customer_ID = ?;";
        ReturnCustomer returnFromSentAppt = (cID, sql) -> {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(customerReturn);
            ps.setInt(1, cID);
            ResultSet rs = ps.executeQuery();
            rs.next();
            cID = customerID;
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phoneNumber = rs.getString("Phone");
            String firstLevelDiv = rs.getString("Division");
            int divID = rs.getInt("Division_ID");
            int countryID = rs.getInt("first_level_divisions.COUNTRY_ID");
            String countryName = rs.getString("Country");

            Customer derivedCustomer = new Customer(customerID, customerName, customerAddress, postalCode, phoneNumber, firstLevelDiv, divID, countryID, countryName);
            return derivedCustomer;

        };
        Customer  c = returnFromSentAppt.returnCustomer(customerID, customerReturn);

        /**Lambda returning an ObservableList of contact associated appointments with the contactID and SQL statement parameters passed.
         Used in reports to generate list of contact based appointments.
         */

        String contactReturn = "SELECT Contact_Name, Contact_ID FROM contacts WHERE Contact_Name = ?;";
        ReturnContact returnfromAppt = (cName, sqls) ->{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(contactReturn);
            ps.setString(1, contactName);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int contactID = rs.getInt("Contact_ID");
            Contact contact = new Contact(contactID, contactName);
            return contact;
        };
        Contact contact = returnfromAppt.returnContact(contactName,contactReturn);
        User selectedUser = DBUser.getUserInfo(apptToRecieve.getAssocUserID());

        apptIDTxt.setText(String.valueOf(apptToRecieve.getApptID()));
        apptTitleTxt.setText(String.valueOf(apptToRecieve.getApptTitle()));
        apptDescriptionTxt.setText(String.valueOf(apptToRecieve.getApptDescription()));
        apptLocationTxt.setText(String.valueOf(apptToRecieve.getApptLocation()));
        apptTypeTxt.setText(String.valueOf(apptToRecieve.getApptType()));
        userIDCombo.setValue(selectedUser);
        customerComboBox.setValue(c);
        contactNameCombo.setValue(contact);
        startTimeCombo.setValue(sentStartTime);
        endTImeCombo.setValue(sentEndTime);
        datePicker.setValue(aStart.toLocalDate());
    }
    /**This method accepts userID selection*/
    @FXML
    void onActionSelectUser(ActionEvent event) {

    }
    boolean invalidTime = false;
    /**This method resets boolean value for logical error checks*/
    @FXML
    void onActionStartCombo(ActionEvent event) {
        invalidTime = false;
    }
    /**This method resets boolean value for logical error checks*/
    @FXML
    void onActionEndCombo(ActionEvent event) {
        invalidTime = false;
    }
    /**This method accepts user entered information, performs logical and time related checks and stores updated appointment information in the database*/
    @FXML
    void onActionModifyAppointment(ActionEvent event) throws IOException {
        int apptID = Integer.parseInt(apptIDTxt.getText());
        String apptTitle = apptTitleTxt.getText();
        String apptDescription = apptDescriptionTxt.getText();
        String apptLocation = apptLocationTxt.getText();
        String apptType = apptTypeTxt.getText();
        int customerID = customerComboBox.getSelectionModel().getSelectedItem().getCustomerID();
        int contactID = contactNameCombo.getSelectionModel().getSelectedItem().getContactID();

        //Obtain values from appointment start and end date pickers and combo boxes
        LocalDate date = datePicker.getValue();
        LocalTime aptStart = startTimeCombo.getValue();
        LocalTime aptEnd = endTImeCombo.getValue();
        //Step 1
        // Obtain the LocalDateTime for appointment start and end
        LocalDateTime apptStart = LocalDateTime.of(date, aptStart);
        LocalDateTime apptEnd = LocalDateTime.of(date, aptEnd);
        //LocalTime for business open and closed
        LocalTime bOpen = LocalTime.of(8, 00);
        LocalTime bClosed = LocalTime.of(22, 00);
        //User's zoneId
        ZoneId usersLocalZoneId = ZoneId.of(TimeZone.getDefault().getID());//User's zone Id
        // obtain the ZonedDateTime version of LocalDateTime
        ZonedDateTime localZDTApptStart = ZonedDateTime.of(apptStart, ZoneId.systemDefault());
        ZonedDateTime localZDTApptEnd = ZonedDateTime.of(apptEnd, ZoneId.systemDefault());
        // LocalDateTime object(s) used to compare to start time and end time to business hours
        LocalDateTime businessOpen = LocalDateTime.of(date, bOpen);
        LocalDateTime businessClosed = LocalDateTime.of(date, bClosed);
        //EST time zone ZoneId
        ZoneId nyZoneId = ZoneId.of("America/New_York");
        //Step 2
        //Zoned DateTime objects for normal business hours in EST
        ZonedDateTime nyZDTOpen = ZonedDateTime.of(businessOpen, nyZoneId);
        ZonedDateTime nyZDTClosed = ZonedDateTime.of(businessClosed, nyZoneId);
        //Step 3
        //Comparing EST to user's local time zone
        ZonedDateTime nyOpenToUsersZDT = nyZDTOpen.withZoneSameInstant(usersLocalZoneId);
        ZonedDateTime nyClosedToUsersZDT = nyZDTClosed.withZoneSameInstant(usersLocalZoneId);
        //Step 4
        //comparing EST to user's local time zone outputting just the date or just the time (or both)
        LocalDateTime nyOpen = nyOpenToUsersZDT.toLocalDateTime();
        LocalDateTime nyClosed = nyClosedToUsersZDT.toLocalDateTime();


        //Check time to make sure it is within normal business hours
        if ((apptStart.isBefore(nyOpen)) || (apptStart.isAfter(nyClosed)) || (apptEnd.isAfter(nyClosed))) {
            errorLbl.setText("Error");
            errorLbl2.setText("Normal business hours are from 8:00am - 10:00pm EST daily.");
            errorLbl3.setText("Please select an appointment time within normal business hours.");
            invalidTime = true;
        }
        if(aptStart.isAfter(aptEnd)){
            errorLbl.setText("Error");
            errorLbl2.setText("Appointment end time cannot be before appointment start time. Please adjust appointment times.");
            invalidTime = true;
        }
        boolean hasAssocAppts = true;
        boolean apptOverlap = false;

        if (DBCustomer.getAllAssociatedAppts(customerID).isEmpty()) {
            hasAssocAppts = false;
        }
        if (hasAssocAppts) {
            for (Appointment a : DBCustomer.getAllAssociatedAppts(customerID)) {

                LocalDateTime start = a.getApptStart();
                LocalDateTime end = a.getApptEnd();

                if (a.getApptID() == apptID) {
                    break;
                }

                if ((apptStart.isAfter(start) || apptStart.isEqual(start)) && apptStart.isBefore(end)) {
                    apptOverlap = true;
                    break;

                }
                if (apptEnd.isAfter(start) && (apptEnd.isBefore(end) || apptEnd.isEqual(end))) {
                    apptOverlap = true;
                    break;

                }
                if ((apptStart.isBefore(start) || apptStart.isEqual(start)) && (apptEnd.isAfter(end) || apptEnd.isEqual(end))) {
                    apptOverlap = true;
                    break;
                }
            }
        }
        if (apptOverlap) {
            errorLbl.setText("Error");
            errorLbl2.setText("Another appointment is scheduled during this time. Please select another time");
            invalidTime = true;
        }
        if (invalidTime == false) {
            //add appointment to database after logical checks
            DBAppointment.updateAppt(apptID, apptTitle, apptDescription, apptLocation, apptType, apptStart, apptEnd, customerID, contactID);
            String selectedLocation = "/View/MainMenu.fxml";
            ButtonNav.navigateTo(stage, event, scene, selectedLocation);

        }
    }


    /**Event handler to navigate to Main Menu*/
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        String selectedLocation = "/View/MainMenu.fxml";
        ButtonNav.navigateTo(stage, event, scene, selectedLocation);
    }
/**This is the initialize method. Combo box selections are set here.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userIDCombo.setItems(DBUser.getAllUsers());
        LocalTime start = LocalTime.of(7,0);
        LocalTime end = LocalTime.of(23,00);
        while(start.isBefore(end.plusSeconds(1))){
            startTimeCombo.getItems().add(start);
            endTImeCombo.getItems().add(start);
            start = start.plusMinutes(15);
        }
        startTimeCombo.getSelectionModel().select(LocalTime.of(8,0));
        contactNameCombo.setItems(Lists.getAllContacts());
        customerComboBox.setItems(DBCustomer.getAllCustomers());





    }
}
