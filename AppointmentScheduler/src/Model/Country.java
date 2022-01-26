package Model;
/**This class defines Country objects.*/
public class Country {

    /**Declares fields*/
    private int countryID;
    private String countryName;

    /**This is the constructor for the Country class.*/
    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * @return the countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     This method returns the string value of the country name for use in the combo box.
     * */
    @Override
    public String toString() {
        return (" " + countryName + " ");

    }



}

