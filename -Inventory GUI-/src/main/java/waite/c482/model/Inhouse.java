package waite.c482.model;

/**
 * @author Dustin Waite
 *
 * models Inhouse part
 */

public class Inhouse extends Part {

    /**
     * machine ID for the part
     */
    private int machineId;

    /**
     * constructor and super constructor for Inhouse object.
     *
     * @param id ID for part
     * @param name name for part
     * @param price price for part
     * @param stock stock for part
     * @param min min value for stock of part
     * @param max max value for stock of part
     * @param machineId machine ID for part
     */
    public Inhouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * setter for machine ID.
     *
     * @param machineId sets machine ID
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * getter for machine ID.
     *
     * @return machineId
     */
    public int getMachineId() {
        return machineId;
    }
}
