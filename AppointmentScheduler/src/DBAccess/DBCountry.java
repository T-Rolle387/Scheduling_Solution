package DBAccess;

import Model.Country;
import Utility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**This class defines database/country interactions*/
public class DBCountry {
    /**This method returns a list of all countries*/
    public static ObservableList<Country> getAllCountries () {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        String selectStatement2 = "SELECT * FROM countries";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(selectStatement2);

            ResultSet rs = ps.executeQuery();

            //Forward scroll ResultSet
            while (rs.next()) //is true will iterate until false(all results obtained)
            {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");


                Country country = new Country(countryID, countryName);
                allCountries.add(country );
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allCountries;

    }
}
