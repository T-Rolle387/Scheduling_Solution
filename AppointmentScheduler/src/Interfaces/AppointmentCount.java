package Interfaces;

import java.sql.SQLException;
/**Lambda generating a total appointment count per customer, accepting a SQL statement and CustomerID
 as parameters passed and returning an integer as the total appointment count. */
public interface AppointmentCount {

    int selectCount(String sql, int n) throws SQLException;
}
