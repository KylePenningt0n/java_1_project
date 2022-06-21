package model;

/**
 * This is the Outsourced class from a Part
 * RUNTIME ERRORS accidentally switched outsourced and inhouse parts in the code. switched them after seing how they were used in memory off debug screen
 * FUTURE ENHANCEMENTS add an address to the company name or add other suppliers with the same part
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * This sets up the outsourced object
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max,String companyName){
        super(id,name,price,stock,min,max);
        this.companyName = companyName;
    }

    /**
     * gets the company name for the object outsourced
     * @return
     */
    public String getCompanyName(){
        return companyName;
    }

    /**
     * sets the company name
     * @param companyName
     */
    public void setCompanyName(String companyName){ this.companyName = companyName;
    }
}
