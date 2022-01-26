package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**This class is used to define Contact objects*/
public class Contact {
    /**Declares an Observable List of Appointment objects associated with a specific contact*/
    private ObservableList<Appointment> contactAssociatedAppts = FXCollections.observableArrayList();

    /**Declare fields*/
    private int contactID;
    private String contactName;


    /**This is the Contact constructor*/
    public Contact(int contactID, String contactName){
        this.contactID = contactID;
        this.contactName = contactName;

    }

/**
 * @return  the contact ID*/
    public int getContactID() {
        return contactID;
    }
/**
 * @return the contact name*/
    public String getContactName() {
        return contactName;
    }




    @Override
    public String toString() {
        return (" " + contactName + " ");
    }
}
