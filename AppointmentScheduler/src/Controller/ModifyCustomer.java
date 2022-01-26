package Controller;

import DBAccess.DBAppointment;
import DBAccess.DBCountry;
import DBAccess.DBCustomer;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
/**This class defines interactions in the Modify Customer interface*/
public class ModifyCustomer implements Initializable {

    Stage stage;
    Parent scene;

    /**Declares table view and table column values*/
    public TableView assocAppt;

    public TableColumn<Appointment, Integer> apptID;
    public TableColumn<Appointment, String> apptTitle;
    public TableColumn<Appointment, String> apptDescription;
    public TableColumn<Appointment, String> apptStart;
    public TableColumn<Appointment, String> apptEnd;

    public TextField customerIDTxt;
    public TextField customerNameTxt;
    public TextField customerAddressTxt;
    public TextField customerPostalCode;
    public TextField phoneNumberTxt;
    /**Declares combo box values*/
    public ComboBox<FirstLevelDiv> firstLevelDivCombo;
    public ComboBox<Country> countryCombo;



    /**This method receives the customer info selected from the main menu.*/
    public void sentCustomer(Customer customerToReceive) {
        FirstLevelDiv fld = new FirstLevelDiv(customerToReceive.getDivID(),customerToReceive.getFirstLevelDiv());
        Country c = new Country(customerToReceive.getCustomerID(),customerToReceive.getCustomerCountry());
        //Converts text to acceptable data types
        int customerID = customerToReceive.getCustomerID();
        customerIDTxt.setText(String.valueOf(customerToReceive.getCustomerID()));
        customerNameTxt.setText(String.valueOf(customerToReceive.getCustomerName()));
        customerAddressTxt.setText(String.valueOf(customerToReceive.getCustomerAddress()));
        customerPostalCode.setText(String.valueOf(customerToReceive.getPostalCode()));
        phoneNumberTxt.setText(String.valueOf(customerToReceive.getPhoneNumber()));

        //Associated appointments table columns
        assocAppt.setItems(DBCustomer.getAllAssociatedAppts(customerID));
        apptID.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("apptTitle"));
        apptDescription.setCellValueFactory(new PropertyValueFactory<>("apptDescription"));
        apptStart.setCellValueFactory(new PropertyValueFactory<>("apptStart"));
        apptEnd.setCellValueFactory(new PropertyValueFactory<>("apptEnd"));

        //Combo box selection
        countryCombo.setValue(c);
        firstLevelDivCombo.setValue(fld);
    }

/**This method allow the user to delete customer associated appointments*/
    @FXML
    void deleteAssocAppt(ActionEvent event)  {
        Appointment selectedAppt = (Appointment) assocAppt.getSelectionModel().getSelectedItem();
        int aptToDelete = selectedAppt.getApptID();
        String aTDType = selectedAppt.getApptType();
        int customerID = Integer.parseInt(customerIDTxt.getText());
        if ((assocAppt.getSelectionModel().isEmpty()) == false) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The selected appointment will be deleted. Do you want to continue?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    DBAppointment.deleteAppt(aptToDelete);
                    Alert alert2 = new Alert(Alert.AlertType.WARNING);
                    alert2.setTitle("Error Dialog");
                    alert2.setContentText("Appointment (ID : " + aptToDelete + "Type: " + aTDType  + " ) deletion  successful.");
                    alert2.showAndWait();
                    assocAppt.setItems(DBCustomer.getAllAssociatedAppts(customerID));

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            }

    }

/**This method sets first level division values based on the country selected.*/
    @FXML
    void onCountrySelected(ActionEvent event) {

        firstLevelDivCombo.setItems(getAllAssocDiv());

    }

/**This method accepts user input provided, does logical error checks and update customer in database*/
    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException, SQLException {

        int customerID = Integer.parseInt(customerIDTxt.getText());
        String customerName = customerNameTxt.getText();
        String customerAddress = customerAddressTxt.getText();
        String postalCode = customerPostalCode.getText();
        String phoneNumber = phoneNumberTxt.getText();
        int divID = firstLevelDivCombo.getSelectionModel().getSelectedItem().getDivID();
        boolean isNull = false;

        if (customerName.isEmpty() || customerAddress.isEmpty() || postalCode.isEmpty() || phoneNumber.isEmpty()){
            isNull = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("All fields must be filled in.");
            alert.setContentText("Please enter a value for each field.");
            alert.showAndWait();

        }

        if (isNull == false) {
            DBCustomer.updateCustomer(customerID, customerName, customerAddress, postalCode, phoneNumber, divID);

            String selectedLocation = "/View/MainMenu.fxml";
            ButtonNav.navigateTo(stage, event, scene, selectedLocation);
        }

    }
/**This method returns the user to the main menu.*/
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        String selectedLocation = "/View/MainMenu.fxml";
        ButtonNav.navigateTo(stage, event, scene, selectedLocation);
    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){
        countryCombo.setItems(DBCountry.getAllCountries());


    }
    /**This method returns a list of Country associated first level division for use in the first level division combo box.*/
    public ObservableList<FirstLevelDiv> getAllAssocDiv() {
        firstLevelDivCombo.setPromptText("Please select a State/Province.");
        Country selectedCountry = countryCombo.getValue();
        firstLevelDivCombo.getItems().clear();
        ObservableList<FirstLevelDiv> allAssocDiv = FXCollections.observableArrayList();

        String assocDivisions = "SELECT first_level_divisions.Division, first_level_divisions.Division_ID \n" +
                "FROM first_level_divisions, countries\n" +
                "WHERE first_level_divisions.COUNTRY_ID = countries.Country_ID\n" +
                "AND countries.Country = ?";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(assocDivisions);
            //key-value pair mapping
            ps.setString(1, selectedCountry.getCountryName());

            ResultSet rs = ps.executeQuery();
                while (rs.next()){

                    String divName = rs.getString("Division");
                    int divID = rs.getInt("Division_ID");
                    FirstLevelDiv firstleveldiv = new FirstLevelDiv(divID, divName);
                    allAssocDiv.add(firstleveldiv );


                }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allAssocDiv;

    }
}

