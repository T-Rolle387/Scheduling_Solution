package Interfaces;

import Model.Appointment;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**Lambda returning an ObservableList of contact associated appointments with the contactID and SQL statement parameters passed.
    Used in reports to generate list of contact based appointments.
 */
public interface ReturnAppts {

    ObservableList<Appointment> contAssocAppts(int c, String s) throws SQLException;

}
