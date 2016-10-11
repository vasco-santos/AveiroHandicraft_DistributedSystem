package comInf.States;

import java.io.Serializable;

/**
 * Constants that characterise the internal state of the Customer.
 * @author andrealves
 */

public enum CustomerState implements isAnEntitie, Serializable
{
    CARRYING_DAILY_CHORES("CDC"),       // the customer is living his normal life
    CHECKING_SHOP_DOOR_OPEN("CSDO"),    // the customer is checking whether he can enter the shop
    APRAISING_OFFER_IN_DISPLAY("AOID"), // the customer is inspecting the products being offered
    BUYING_SOME_GOODS("BSG");           // the customer is buying some products
    
    /**
     * State name.
     * @serialField name
     */
    private final String name;
    
    /**
    *  Serialization key.
    *    @serialField serialVersionUID
    */
    private static final long serialVersionUID = 1002L;
    
    /**
     * Instantiate Customer State.
     * @param name 
     */
    private CustomerState(String name) {
        this.name = name;
    }

    /**
     * Get Customer actual State.
     * @return short name of the requested state
     */
    @Override
    public String getState() {
        return name;
    }
}
