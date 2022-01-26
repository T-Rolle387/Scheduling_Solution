package Interfaces;

import Model.Customer;

import java.sql.SQLException;
/** Lambda returning Customer object used to populate Customer ComboBox given customerID and SQL statement as parameters passed */
public interface ReturnCustomer {

    Customer returnCustomer(int cID, String sql) throws SQLException;
}
