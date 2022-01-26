package Model;
/**This class defines the First Level Divisions objects*/
public class FirstLevelDiv {

    /**Declare fields*/
    public int divID;
    public String divName;

    /**First Level Division constructor*/
    public FirstLevelDiv(int divID, String divName) {
        this.divID = divID;
        this.divName = divName;
    }
/**@return the division ID
 * */
    public int getDivID() {
        return divID;
    }

/**
 This method returns the String value of the division name for use in the first level division combo box.*/
    public String toString() {
        return (" " + divName + " ");
    }
}

