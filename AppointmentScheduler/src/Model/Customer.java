package Model;
/**This class defines the customer object*/
public class Customer {

    /**Declare fields*/
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String postalCode;
    private String phoneNumber;
    private String firstLevelDiv;
    private int divID;
    private int countryID;
    private String countryName;


    /**This is the Customer constructor.*/
    public Customer(int customerID, String customerName, String customerAddress, String postalCode, String phoneNumber, String firstLevelDiv, int divID, int countryID, String countryName){
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.firstLevelDiv = firstLevelDiv;
        this.divID = divID;
        this.countryID = countryID;
        this.countryName = countryName ;
    }

    /**
      @param customerID the customer ID to set
     */
    public void setCustomerID(int customerID) { this.customerID = customerID; }

    /**
      @return the customerID
     */
    public int getCustomerID() { return customerID; }

    /**
       @return the customerName
     */
    public String getCustomerName() { return  customerName; }

    /**
       @return the customer address
     */
    public String getCustomerAddress() { return customerAddress; }

    /**
       @return the postalCode
     */
    public String getPostalCode() { return  postalCode; }

    /**
       @return the phone number
     */
    public String getPhoneNumber() { return  phoneNumber;}

    /**
     @return the phone number
     */
    public int getDivID(){return divID;}

    /**
     @return the first level division
     */
    public String getFirstLevelDiv(){ return firstLevelDiv;}

    /**
     @return the customer's country
     */
    public String getCustomerCountry() {
        return countryName;
    }

    /**
     This method returns the String value of the customer name and ID for use in the first level division combo box.*/
    @Override
    public String toString() {
        return (" " + customerID + " " + customerName + " ");
    }





}
