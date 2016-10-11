package comInf.States;

import java.io.Serializable;

/**
 * Constants that characterise the internal state of the Entrepreneur.
 * @author andrealves
 */
public enum EntrepreneurState implements isAnEntitie, Serializable
{
    OPENING_THE_SHOP("OTS"),            // the entrepreneur is opening the shop
    WAITING_FOR_NEXT_TASK("WFNT"),      // the entrepreneur is waiting for service requests
    ATTENDING_A_CUSTOMER("AC"),         // the entrepreneur is attending a customer
    CLOSING_THE_SHOP("CTS"),            // the entrepreneur is closing the shop
    EXITING_THE_SHOP("ETS"),            // the entrepreneur exits the shop
    COLLECTING_BATCH_OF_PRODUCTS("CBOP"),// the entrepreneur is collecting finished products from the work shop
    BUYING_PRIME_MATERIAL("BPM"),       // the entrepreneur goes shopping for prime materials
    DELIVERING_PRIME_MATERIALS("DPM");  // the entrepreneur delivers prime materials to the work shop
    
    private final String name; 

    /**
    *  Serialization key.
    *    @serialField serialVersionUID
    */
    private static final long serialVersionUID = 1003L;
    
    /**
     * Instantiate Entrepreneur State.
     * @param name 
     */
    private EntrepreneurState(String name) {
        this.name = name;
    }

    /**
     * Get Entrepreneur actual State.
     * @return short name of the requested state
     */
    @Override
    public String getState() {
        return name;
    }
}
