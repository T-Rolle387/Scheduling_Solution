package Controller;

import DBAccess.DBAppointment;
import DBAccess.DBCustomer;
import Model.Appointment;

import Model.Customer;
import Model.Lists;
import Utility.ButtonNav;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
/**This class defines interactions within the Main Menu.*/
public class MainMenuController implements Initializable {
    Stage stage;
    Parent scene;
    Customer customer;
    Appointment appt;


   /**Appointment TableView and Columns*/
    public TableView apptTableView;

    public TableColumn apptIDCol;
    public TableColumn apptTitleCol;
    public TableColumn apptDescriptionCol;
    public TableColumn apptLocationCol;
    public TableColumn contactCol;
    public TableColumn startTimeCol;
    public TableColumn endTimeCol;
    public TableColumn apptCustomerIDCol;
    public TableColumn apptTypeCol;

    /**Customer Search Bar*/
    public TextField searchBarTxt;

    /**Customer TableView and Columns*/
    public TableView<Customer> customerTableVIew;

    public TableColumn customerIDCol;
    public TableColumn customerNameCol;
    public TableColumn customerAddressCol;
    public TableColumn postalCodeCol;
    public TableColumn phoneNumCol;
    public TableColumn firstLevelDivCol;
    public TableColumn countryCol;



/**Method the returns customer objects based on search criteria*/
    @FXML
    void onActionCustomerSearch(ActionEvent event) {
        String customerSearch = searchBarTxt.getText();

        ObservableList<Customer> customers = Lists.lookupCustomer(customerSearch);

        if (customers.size() == 0) {
            try {
                int customerId = Integer.parseInt(customerSearch);
                Customer c = Lists.searchCustomerByID(customerId);
                if (c != null)
                    customers.add(c);
            } catch (NumberFormatException e) {
                //ignore it
            }
        }

        customerTableVIew.setItems(customers);
        if (customers.size() == 0) {
            System.out.println("Error");
            System.out.println("Customer not found");
            searchBarTxt.setText("");
        }
    }


    /**Event handler for exit button - When pressed ends application*/
    @FXML
    void onActionExit(ActionEvent event) {


        System.exit(0);

    }
    /**Event handler for report button - When pressed brings user to Report Menu*/
    @FXML
    void onActionReports(ActionEvent event) throws IOException {
        String selectedLocation = "/View/Reports.fxml";
        ButtonNav.navigateTo(stage, event, scene, selectedLocation);

    }

    /**Event handler to navigate to New Appointment Menu*/
    @FXML
    void onActionNewAppt(ActionEvent event) throws IOException {
        String selectedLocation = "/View/NewAppointment.fxml";
        ButtonNav.navigateTo(stage, event, scene, selectedLocation);
    }

    /**Event handler to navigate to Modify Appointment Menu - This method sends selected appointment over to Modify Appointment for use*/
    @FXML

    void onActionModifyAppt(ActionEvent event) throws IOException {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyAppointment.fxml"));
            loader.load();
            ModifyAppointment MAController = loader.getController();
            MAController.sentAppt((Appointment) apptTableView.getSelectionModel().getSelectedItem());


            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException | SQLException e)
        {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Appointment not selected.");
            alert.setContentText("Please select an appointment to continue.");
            alert.showAndWait();
        }

    }

    /**This method takes the user to the Add Customer screen*/
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        String selectedLocation = "/View/NewCustomer.fxml";
        ButtonNav.navigateTo(stage, event, scene, selectedLocation);

    }
    /**This method takes the user to the Modify Customer screen and sends associated appointment selection over.*/
    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException {
        //String selectedLocation = "/View/ModifyCustomer.fxml";
        //ButtonNav.navigateTo(stage, event, scene, selectedLocation);
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyCustomer.fxml"));
            loader.load();
            ModifyCustomer MController = loader.getController();
            MController.sentCustomer((Customer) customerTableVIew.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Customer not selected.");
            alert.setContentText("Please select a customer to continue.");
            alert.showAndWait();
        }
    }
    /**This method deletes an appointment*/
    @FXML
    void deleteAppt(ActionEvent event) throws SQLException {
        if(apptTableView.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select an appointment to delete.");
            alert.showAndWait();
        }
        appt = (Appointment) apptTableView.getSelectionModel().getSelectedItem();
        if((apptTableView.getSelectionModel().isEmpty()) == false){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The selected appointment will be deleted. Do you want to continue?");
            int aptID = ((Appointment) apptTableView.getSelectionModel().getSelectedItem()).getApptID();
            String aptType = ((Appointment) apptTableView.getSelectionModel().getSelectedItem()).getApptType();

            Optional<ButtonType> result = alert.showAndWait();


            if (result.isPresent() && result.get() == ButtonType.OK) {
                DBAppointment.deleteAppt(appt.getApptID());
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Delete Confirmation");
                alert2.setContentText("Deletion of appointment with ID of " + aptID + " of type " + aptType + " successful.");
                alert2.showAndWait();

                apptTableView.setItems(DBAppointment.getAllAppts());
            }

        }

    }
    /**This method takes the user to Calender View screen.*/
    @FXML
    void onActionCalendarVIew(ActionEvent event) throws IOException {
        String selectedLocation = "/View/CalendarView.fxml";
        ButtonNav.navigateTo(stage, event, scene, selectedLocation);
    }
    /**This method deletes selected customer*/
    @FXML
    void deleteCustomer(ActionEvent event) throws SQLException {
        if(customerTableVIew.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a customer to delete.");
            alert.showAndWait();
        }
        customer = (Customer) customerTableVIew.getSelectionModel().getSelectedItem();
        if (DBCustomer.getAllAssociatedAppts(customer.getCustomerID()).isEmpty() == false) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This customer has associated appointments. If you delete this customer all associated appointments will be deleted.");
            int customerID = customerTableVIew.getSelectionModel().getSelectedItem().getCustomerID();
            for(Appointment a : DBCustomer.getAllAssociatedAppts(customerID)){
                DBAppointment.deleteAppt(a.getApptID());
            }
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                DBCustomer.deleteCustomer(customerID);
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Error Dialog");
                alert2.setContentText("Customer deletion successful. All appointments associated with the customer have been deleted as well");
                alert2.showAndWait();
                customerTableVIew.setItems(DBCustomer.getAllCustomers());
                apptTableView.setItems(DBAppointment.getAllAppts());
            }
        }
        if(((customerTableVIew.getSelectionModel().isEmpty()) == false) && (DBCustomer.getAllAssociatedAppts(customer.getCustomerID()).isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The selected customer will be deleted. Do you want to continue?");

            Optional<ButtonType> result = alert.showAndWait();


            if (result.isPresent() && result.get() == ButtonType.OK) {
                DBCustomer.deleteCustomer(customer.getCustomerID());
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Error Dialog");
                alert2.setContentText("Customer deletion successful.");
                alert2.showAndWait();
                customerTableVIew.setItems(DBCustomer.getAllCustomers());
            }

        }

    }
    /**Properties loaded into tables, combo boxes on initialize*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerTableVIew.setItems(DBCustomer.getAllCustomers());
        apptTableView.setItems(DBAppointment.getAllAppts());

        


        //Populate Customer TableView
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        firstLevelDivCol.setCellValueFactory(new PropertyValueFactory<>("firstLevelDiv"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("customerCountry"));

        //Populate Appointment TableView
        apptIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptID"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("apptLocation"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("assocContactName"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("apptStart"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("apptEnd"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        apptCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));







    }
}
