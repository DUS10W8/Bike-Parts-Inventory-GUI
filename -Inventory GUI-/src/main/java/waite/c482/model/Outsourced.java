package waite.c482.model;

/**
 * @author Dustin Waite
 *
 * model for the Outsourced part
 */

public class Outsourced extends Part {

    /**
     * the company name for Outsourced part.
     */
    private String companyName;

    /**
     * constructor and super constructor for new outsourced part.
     *
     * @param id id for outsourced part
     * @param name name for outsourced part
     * @param price price for outsourced part
     * @param stock stock for outsourced part
     * @param min min amount of stock of outsourced part
     * @param max max amount of stock of outsourced part
     * @param companyName company name for outsourced part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * company name getter
     *
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * company name setter
     *
     * @param companyName company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
