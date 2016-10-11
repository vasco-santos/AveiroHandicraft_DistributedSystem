package serverSide.Repository;

import comInf.States.CustomerState;

/**
 * Customer current information.
 * @author andrealves
 */
public class Customer {

    /**
     * Customer current internal state.
     * @serialField stat
     */
    private CustomerState stat;
    
    /**
     * amount of products bought by the customer.
     * @serialField boughtPieces
     */
    private int boughtPieces;
    
    /**
     * Customer Instantiation.
     */
    public Customer()
    {
        this.stat = CustomerState.CARRYING_DAILY_CHORES;
        this.boughtPieces = 0;
    }

    /**
     * Get Customer current internal State.
     * @return stat Customer State
     */
    public CustomerState getCustomerState() {
        return this.stat;
    }
    
    /**
     * Get Customer current bought products.
     * @return boughtPieces the number of bought products for a client
     */
    public int getBoughtProducts() {
        return this.boughtPieces;
    }

    /**
     * Update Customer state.
     * @param stat new status of costumer
     */
    public void setCustomerState(CustomerState stat) {
        this.stat = stat;
    }

    /**
     * Update Customer bought products.
     * @param boughtPieces new number of bought pieces
     */
    public void setBoughtProducts(int boughtPieces) {
        this.boughtPieces = boughtPieces;
    }

}
