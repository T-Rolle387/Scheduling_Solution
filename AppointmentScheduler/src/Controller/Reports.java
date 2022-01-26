package Controller;

import DBAccess.DBAppointment;
import DBAccess.DBCustomer;
import Interfaces.AppointmentCount;

import Interfaces.ReturnAppts;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.Lists;
import Utility.ButtonNav;
import Utility.DBConnection;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
/**This class serves as the controller class for GUI interactions in the Reports screen*/
public class Reports implements Initializable {

    Stage stage;
    Parent scene;
    /**Declares combo boxes, columns, and table view for contact associated appointments*/
    public ComboBox<Contact> contactCombo;
    public TableView contactScheduleTableView;
    public TableColumn apptIDCol;
    public TableColumn apptTitleCol;
    public TableColumn apptTypeCol;
    public TableColumn apptDescriptionCol;
    public TableColumn apptStartCol;
    public TableColumn apptEndCol;
    public TableColumn customerIDCol;



    public Reports() throws FileNotFoundException {
    }
/**This is the handler for contact combobox selection. Upon selection table information will be generated and returned via table view display.*/
    @FXML
    void onActionSelectContact(ActionEvent event) throws SQLException {
        /**Declares contact associated Observable List*/
        ObservableList<Appointment> cAssocAppts = FXCollections.observableArrayList();
        /**Sets specified values into columns*/
        int contactID = contactCombo.getSelectionModel().getSelectedItem().getContactID();
        apptIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("customerID"));

        /**Lambda returning an ObservableList of contact associated appointments with the contactID and SQL statement as parameters passed.
         Used in reports to generate list of contact based appointments.
         */
        try {
            String sql = "SELECT * FROM appointments WHERE Contact_ID = ?;";
            //Value returning lambda expression
            ReturnAppts apptByContact = (c, s) -> {
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ps.setInt(1, c);
                ResultSet rs = ps.executeQuery();
                rs.next();
                int apptID = rs.getInt("Appointment_ID");
                String apptTitle = rs.getString("Title");
                String apptDescription = rs.getString("Description");
                String apptLocation = rs.getString("Location");
                String apptType = rs.getString("Type");
                String assocContactName = contactCombo.getSelectionModel().getSelectedItem().getContactName();
                Timestamp aptStart = rs.getTimestamp("Start");
                Timestamp aptEnd = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                String customerName = null;

                int assocUserID = rs.getInt("User_ID");

                LocalDateTime apptStart = aptStart.toLocalDateTime();
                LocalDateTime apptEnd = aptEnd.toLocalDateTime();
                Appointment cAssocAppt = new Appointment(apptID, apptTitle, apptDescription, apptLocation, apptType, assocContactName, apptStart, apptEnd, customerID, customerName, assocUserID);
                cAssocAppts.add(cAssocAppt);

                return cAssocAppts;
            };


        ObservableList<Appointment> contactAssocAppts = apptByContact.contAssocAppts(contactID, sql);
        contactScheduleTableView.setItems(contactAssocAppts);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    ////////////////////////////Total Number of Customer Appointments by Type and Month//////////////////////////////////////////////////////////////////////////////////////////
    /**Label that will display total number of appointments per month*/
    public Label totalApptsLbl;
    /***Returns user to main menu*/
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        String selectedLocation = "/View/MainMenu.fxml";
        ButtonNav.navigateTo(stage, event, scene, selectedLocation);
    }

    /**Declare text area, combo box, StringBuilder*/
    public TextArea reportView;
    public ComboBox<Month> monthCombo;
    StringBuilder aptTypeView = new StringBuilder();
    /**Declares Observable List of appointments by month selected*/
    public static ObservableList<Appointment> apptsByMonth = FXCollections.observableArrayList();

/**This is the event handler for the month selection combo box.*/
    @FXML
    void onActionMonthCombo(ActionEvent event) throws SQLException {
        /**clears appointments by list month and "resets" Stringbuilder aptTypeView, clears reportView*/
        apptsByMonth.clear();
        aptTypeView.delete(0, aptTypeView.length());
        reportView.clear();

        /**Returns the month value selected*/
        Month monthSelected = monthCombo.getValue();
        int monthChosen = monthSelected.getValue();
        /**Appends the header*/
        StringBuilder aptTypeView = new StringBuilder();
        aptTypeView.append("# of each Type                Type  \n___________________________________________________________________________________________________________\n");
        /**Populates an Observable List of appointments by month*/
        for (Appointment a : DBAppointment.getAllAppts()) {
            if (a.getApptStart().getMonth() == monthSelected) {
                apptsByMonth.add(a);
            }

        }

        int totalNum = apptsByMonth.size();
        totalApptsLbl.setText(String.valueOf(totalNum));

            /**Appends Type and total values to display*/
            try {
                String sql = "SELECT Type, count(*) AS Amount FROM appointments WHERE month(Start) = ? group by Type;";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ps.setInt(1, monthChosen);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String amount = rs.getString("Amount");
                    String type = rs.getString("Type");
                    aptTypeView.append(amount + "                                       " + type + "                    " + "\n");

                    String s = aptTypeView.toString();
                    reportView.setText(s);
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        if (totalNum == 0) {
            reportView.setText("There are no associated appointments this month.");
        }
        }
    /**This method returns all appointment types and their total count*/
    public String apptsTypeMonth() {
        String sql = "SELECT Type, COUNT(*) as Amount FROM appointments GROUP BY Type ;";
        try{

            aptTypeView.append("# of each Type                Type  \n___________________________________________________________________________________________________________\n");

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                aptTypeView.append(rs.getString("Amount") + "                                       "   + rs.getString("Type") + "                    " + "\n");

            }
            return aptTypeView.toString();

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return aptTypeView.toString();

    }
    /**This method returns the total number of appointments in the database*/
    public int totalNumApptsAll(){
        int totalAll = 0;
        String sql = "SELECT COUNT(*) as Amount FROM appointments;";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                totalAll = rs.getInt("Amount");
            }
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return totalAll;
    }
    /**This is the handler for the "View All" button that returns a view of all appointments*/
    @FXML
    void onActionVIewAll(ActionEvent event) {
        monthCombo.getSelectionModel().clearSelection();
        apptsByMonth.clear();
        aptTypeView.delete(0, aptTypeView.length());
        reportView.clear();
        reportView.setText(apptsTypeMonth());
        totalApptsLbl.setText(String.valueOf(totalNumApptsAll()));
    }


    /////////////////////////////Customer Schedule Report///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**Declares customer combo box, appointment count label, and table view with associated columns for customer schedule display*/
    public ComboBox<Customer> customerCombo;
    public Label apptCountLbl;
    public TableView customerScheduleTableView;
    public TableColumn customerNameCol;
    public TableColumn customerApptCol;
    public TableColumn DescriptionCol;
    public TableColumn StartCol;
    public TableColumn EndCol;

    /**This is the method handler hat displays customer associated appointments based on user's combo box selection*/
    @FXML
    void onActionPopulateCustomerTableView(ActionEvent event) throws SQLException {

        int customerID = customerCombo.getSelectionModel().getSelectedItem().getCustomerID();
        customerScheduleTableView.setItems(DBCustomer.getAllAssociatedAppts(customerID));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerApptCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        StartCol.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
        EndCol.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));


        /**Lambda generating a total appointment count per customer, accepting a SQL statement and CustomerID
         as parameters passed and returning an integer as the total appointment count.*/
        String countStatement =  "SELECT COUNT(Appointment_ID) FROM appointments WHERE Customer_ID = ?;";

        AppointmentCount reportCounts = (s, n) -> {

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(s);
            ps.setInt(1, n);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int c = rs.getInt("COUNT(Appointment_ID)");
            return c;
        };
        int count = reportCounts.selectCount(countStatement, customerID);

        apptCountLbl.setText(String.valueOf(count));





    }


/**This is the initialize method. Initial table view values and combo boxes selections are populated here.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       contactCombo.setItems(Lists.getAllContacts());


       ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        monthCombo.setItems(Lists.getMonthValues());
        reportView.setText(apptsTypeMonth());
        totalApptsLbl.setText(String.valueOf(totalNumApptsAll()));

   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Combo box for customer schedule report
        customerCombo.setItems(DBCustomer.getAllCustomers());
    }

}
