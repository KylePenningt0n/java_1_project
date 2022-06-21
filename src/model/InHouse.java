package model;

/**
 * This class is an extension of a Part.
 * RUNTIME ERROR had trouble setting which part was which in the add part form
 * FUTURE ENHANCEMENT could add operators to machines
 */
public class InHouse extends Part {
    private int machineID;

    /**
     * this sets up the inhouse object
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max,int machineId){
        super(id,name,price,stock,min,max);
        this.machineID = machineId;
        }

        public int getMachineId(){
        return machineID;
        }
        public void setMachineId(int machineID){this.machineID = machineID;
        }
}
