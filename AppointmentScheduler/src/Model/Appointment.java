package Model;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.sql.Timestamp;
import java.time.LocalDateTime;
/**This class is used to define Appointment objects*/
public class Appointment {

    /**Declare fields*/
    private int apptID;
    private String apptTitle;
    private String apptDescription;
    private String apptLocation;
    private String apptType;
    private String assocContactName;
    LocalDateTime apptStart;
    LocalDateTime apptEnd;
    private int customerID;
    private String customerName;
    private int assocUserID;
    Customer customer;


    /**This is the Appointment constructor*/
    public Appointment(int apptID, String apptTitle, String apptDescription, String apptLocation, String apptType, String assocContactName, LocalDateTime apptStart, LocalDateTime apptEnd, int customerID, String customerName, int assocUserID){
        this.apptID = apptID;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.assocContactName = assocContactName;
        this.apptStart = apptStart;
        this.apptEnd = apptEnd;
        this.customerID = customerID;
        this.customerName = customerName;
        this.assocUserID = assocUserID;
    }

    /**
     * @param apptID  to set*/
    public void setApptID(int apptID) { this.apptID = apptID; }
/**@return  the apptID */
    public int getApptID(){ return apptID; }
/**@param apptTitle to set*/
    public void setApptTitle(String apptTitle) { this.apptTitle = apptTitle; }
/**@return  the appointment title*/
    public String getApptTitle() { return  apptTitle; }
/**@param apptDescription to set*/
    public void setApptDescription(String apptDescription) { this.apptDescription = apptDescription; }
/**@return returns the appointment description*/
    public String getApptDescription() { return apptDescription; }
/**@param  apptLocation to set*/
    public void setApptLocation(String apptLocation) {
        this.apptLocation = apptLocation;
    }
/**@return  the appointment location*/
    public String getApptLocation() {
        return apptLocation;
    }
/**@param  apptType*/
    public void setApptType(String apptType) {
        this.apptType = apptType;
    }
/**@return the appointment type*/
    public String getApptType() {
        return apptType;
    }
/**@param assocContactName to set*/
    public void setAssocContactName(String assocContactName) {
        this.assocContactName = assocContactName;
    }
/**@return  the contact name associated with the appointment*/
    public String getAssocContactName() {
        return assocContactName;
    }
/**@param apptStart to set*/
    public void setApptStart(LocalDateTime apptStart) {
        this.apptStart = apptStart;
    }
/**@return  the appointment start date*/
    public LocalDateTime getApptStart() {
        return apptStart;
    }
/**@param  apptEnd to set*/
    public void setApptEnd(LocalDateTime apptEnd) {
        this.apptEnd = apptEnd;
    }
/**@return the appointment end date/time to return*/
    public LocalDateTime getApptEnd() {
        return apptEnd;
    }
/**@param assocCustomerID to set*/
    public void setCustomerID(int assocCustomerID){this.customerID = customerID;}
/**@return the customer ID to return*/
    public int getCustomerID() {
        return customerID;
    }
/**@param customerName to set*/
    public void setCustomerName(String customerName){ this.customerName = customerName;}
/**@return  the customer name to return*/
    public String getCustomerName() {
        return customerName;
    }
    /**@return th userId associated with the appointment*/
    public int getAssocUserID() {
        return assocUserID;
    }
}
