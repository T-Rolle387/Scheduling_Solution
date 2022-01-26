package Controller;

import DBAccess.DBCountry;
import DBAccess.DBCustomer;
import Model.Country;
import Model.Customer;
import Model.FirstLevelDiv;
import Model.Lists;
import Utility.ButtonNav;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
/**This is the controller class for the Add Customer interface.*/
public class NewCustomer implements Initializable {
    Stage stage;
    Parent scene;
/**Declare all fields.*/
    public TextField customerNameTxt;
    public TextField customerAddressTxt;
    public TextField customerPostalCodeTxt;
    public TextField customerPhoneNumberTxt;
/**Declare combo boxes*/
    public ComboBox<FirstLevelDiv> stateComboBox;
    public ComboBox<Country> countryComboBox;


    /**Event handler for button to navigate to Main Menu*/
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        String selectedLocation = "/View/MainMenu.fxml";
        ButtonNav.navigateTo(stage, event, scene, selectedLocation);
    }
    /**Sets First Level Division objects in combo box upon country selection*/
    @FXML
    void onActionCountrySelected(ActionEvent event) {
        stateComboBox.setItems(getAllAssocDiv());
    }

/**Handler for the "Clear" button*/
    @FXML
    void clearAllFields(ActionEvent event) throws IOException {
        customerNameTxt.setText("");
        customerAddressTxt.setText("");
        customerPostalCodeTxt.setText("");
        customerPhoneNumberTxt.setText("");
        countryComboBox.getSelectionModel().clearSelection();
        stateComboBox.getSelectionModel().clearSelection();

    }

/**This is the handler for the save button. It performs logical checks and saves the new customer information into the database.*/
    @FXML
    void saveCustomerInfo(ActionEvent event) throws SQLException, IOException {
        boolean isNull = false;

        String customerName = customerNameTxt.getText();
        String customerAddress = customerAddressTxt.getText();
        String postalCode = customerPostalCodeTxt.getText();
        String phoneNumber = customerPhoneNumberTxt.getText();
        int divID = stateComboBox.getSelectionModel().getSelectedItem().getDivID();

        if (customerName.isEmpty() || customerAddress.isEmpty() || postalCode.isEmpty() || phoneNumber.isEmpty()){
            isNull = true;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("All fields must be filled in.");
            alert.setContentText("Please enter a value for each field.");
            alert.showAndWait();

        }



        if (isNull == false) {
            DBCustomer.addCustomer(customerName, customerAddress, postalCode, phoneNumber, divID);

            String selectedLocation = "/View/MainMenu.fxml";
            ButtonNav.navigateTo(stage, event, scene, selectedLocation);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryComboBox.setItems(DBCountry.getAllCountries());

        stateComboBox.setVisibleRowCount(5);
    }
/**This method obtains all associated first level division information based on country selection.*/
    public ObservableList<FirstLevelDiv> getAllAssocDiv() {
        Country selectedCountry = countryComboBox.getValue();
        System.out.println(selectedCountry);
        /**This declares an Observable List of all first level divisions based on country selection.*/
        ObservableList<FirstLevelDiv> allAssocDiv = FXCollections.observableArrayList();

        String assocDivisions = "SELECT first_level_divisions.Division, first_level_divisions.Division_ID \n" +
                "FROM first_level_divisions, countries\n" +
                "WHERE first_level_divisions.COUNTRY_ID = countries.Country_ID\n" +
                "AND countries.Country = ?";
        stateComboBox.getItems().clear();
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
