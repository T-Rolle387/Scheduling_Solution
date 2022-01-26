package Interfaces;

import Model.Contact;

import java.sql.SQLException;
/**
 Lambda used to return a Contact object given a contact name (String) and sql(Statement).*/

public interface ReturnContact {
    Contact returnContact(String cn, String s) throws SQLException;
}
