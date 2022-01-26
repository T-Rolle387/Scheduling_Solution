package Controller;

import DBAccess.DBAppointment;
import DBAccess.DBCustomer;
import DBAccess.DBUser;
import Model.*;
import Utility.ButtonNav;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
/**This class defines interactions within the New Appointment user interface.*/
public class NewAppointment implements Initializable{
    Stage stage;
    Parent scene;

    /**Declare combo boxes and date picker*/
    public DatePicker datePicker;
    public ComboBox<LocalTime> startTimeCombo;
    public ComboBox<LocalTime> endTimeCombo;
    /**Declare text fields*/
    public TextField apptID;
    public TextField apptTItleTXT;
    public TextField descriptionTxt;
    public TextField locationTxt;
    public TextField typeTxt;
    public TextField userIDTxt;

    /**Declare error labels*/
    public Label errorLbl;
    public Label errorLbl2;
    public Label errorLbl3;
    /**Declare combo boxes*/
    public ComboBox<User> userIDCombo;
    public ComboBox<Contact> contactComboBox;
    public ComboBox<Customer> customerComboBox;
    boolean invalidTime = false;


/**This is the action handler for the "Clear" button*/
    @FXML
    void onActionClear(ActionEvent event) {
        apptID.setText("");
        apptTItleTXT.setText("");
        descriptionTxt.setText("");
        locationTxt.setText("");
        typeTxt.setText("");
        userIDTxt.setText("");
        userIDCombo.getSelectionModel().clearSelection();
        contactComboBox.getSelectionModel().clearSelection();
        customerComboBox.getSelectionModel().clearSelection();
        startTimeCombo.getSelectionModel().select(LocalTime.of(8,0));
    }

    /**This resets the boolean value for time error checks once a new option is selected*/
    @FXML
    void onActionResetE(ActionEvent event) {

        invalidTime = false;


    }
    /**This resets the boolean value for time error checks once a new option is selected*/
    @FXML
    void onActionResetS(ActionEvent event) {
     invalidTime = false;
    }


    /**This action handler provides logical error checks, time conversion, and time error checks and if all is well, saves the appointment to the database*/
    @FXML
    void saveAppt(ActionEvent event) throws SQLException, IOException {

        String apptTitle = apptTItleTXT.getText();
        String apptDescription = descriptionTxt.getText();
        String apptLocation = locationTxt.getText();
        String apptType = typeTxt.getText();
        int customerID = customerComboBox.getSelectionModel().getSelectedItem().getCustomerID();
        int contactID = contactComboBox.getSelectionModel().getSelectedItem().getContactID();

        if(customerComboBox.getValue() == null || contactComboBox == null){
            errorLbl.setText("Error:");
            errorLbl2.setText("Please make sure both a customer and contact are selected.");
            invalidTime = true;

        }

        if (datePicker.getValue() == null || startTimeCombo.getValue() == null || endTimeCombo == null) {
            errorLbl.setText("Error:");
            errorLbl2.setText("Either date or time is not filled in. Please check date/time options and make appropriate selections.");
            errorLbl3.setText("Please use the calendar date picker to select a date AND use the time dropdown to set a time before saving.");
            invalidTime = true;

        }
        //Obtain values from appointment start and end date pickers and combo boxes
        LocalDate date = datePicker.getValue();
        LocalTime aptStart = startTimeCombo.getValue();
        LocalTime aptEnd = endTimeCombo.getValue();
        LocalTime bOpen = LocalTime.of(8, 00);
        LocalTime bClosed = LocalTime.of(22, 00);

        //Step1 for appt
        // Obtain the LocalDateTime for appointment start and end
        LocalDateTime apptStart = LocalDateTime.of(date, aptStart);
        LocalDateTime apptEnd = LocalDateTime.of(date, aptEnd);
        //User's zoneId
        ZoneId usersLocalZoneId = ZoneId.of(TimeZone.getDefault().getID());//User's zone Id
        //Step1 for business
        // LocalDateTime object(s) used to compare to start time and end time to business hours
        LocalDateTime businessOpen = LocalDateTime.of(date, bOpen);
        LocalDateTime businessClosed = LocalDateTime.of(date, bClosed);
        //EST time zone ZoneId
        ZoneId nyZoneId = ZoneId.of("America/New_York");
        //Step 2 for business
        //Zoned DateTime objects for normal business hours in EST
        ZonedDateTime nyZDTOpen = ZonedDateTime.of(businessOpen, nyZoneId);
        ZonedDateTime nyZDTClosed = ZonedDateTime.of(businessClosed, nyZoneId);
        //Step 3 business time converted to local for comparison
        //Comparing EST to user's local time zone
        ZonedDateTime nyOpenToUsersZDT = nyZDTOpen.withZoneSameInstant(usersLocalZoneId);
        ZonedDateTime nyClosedToUsersZDT = nyZDTClosed.withZoneSameInstant(usersLocalZoneId);
        //Step 4 Converted back to ldt for comparison
        //comparing EST to user's local time zone outputting just the date or just the time (or both)
        LocalDateTime nyOpen = nyOpenToUsersZDT.toLocalDateTime();
        LocalDateTime nyClosed = nyClosedToUsersZDT.toLocalDateTime();

        //Check time to make sure it is within normal business hours
        if ((apptStart.isBefore(nyOpen)) || apptStart.isAfter(nyClosed) || (apptEnd.isAfter(nyClosed))) {
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

                if (((apptStart.isAfter(start)) || (apptStart.isEqual(start))) && (apptStart.isBefore(end))) {
                    apptOverlap = true;
                    break;
                }
                if ((apptEnd.isAfter(start)) && ((apptEnd.isBefore(end)) || (apptEnd.isEqual(end)))) {
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
            DBAppointment.addAppointment(apptTitle, apptDescription, apptLocation, apptType, apptStart, apptEnd, customerID, contactID);
            String selectedLocation = "/View/MainMenu.fxml";
            ButtonNav.navigateTo(stage, event, scene, selectedLocation);
        }
    }

/**This action handler returns the user to the main menu*/
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        String selectedLocation = "/View/MainMenu.fxml";
        ButtonNav.navigateTo(stage, event, scene, selectedLocation);
    }


/**This is the initialized method. Object info is set into combo boxes here.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LocalTime start = LocalTime.of(7,0);
        LocalTime end = LocalTime.of(23,00);
        while(start.isBefore(end.plusSeconds(1))){
            startTimeCombo.getItems().add(start);
            endTimeCombo.getItems().add(start);
            start = start.plusMinutes(15);
        }
        startTimeCombo.getSelectionModel().select(LocalTime.of(8,0));
        contactComboBox.setItems(Lists.getAllContacts());
        customerComboBox.setItems(DBCustomer.getAllCustomers());
        userIDCombo.setItems(DBUser.getAllUsers());



    }
}
