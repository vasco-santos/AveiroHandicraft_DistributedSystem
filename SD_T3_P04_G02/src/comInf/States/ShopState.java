package comInf.States;

import java.io.Serializable;

/**
 * Constants that characterise the internal state of the Shop.
 * @author andrealves
 */
public enum ShopState implements Serializable{

    OPEN("OPEN"),               // Shop is Open
    STILLOPEN("STOP"),          // Shop is Open, but the door is closed
    CLOSED("CLOS");             // Shop is Closed
    
    /**
     * State name.
     * @serialField name
     */
    private final String name;
    
    /**
    *  Serialization key.
    *    @serialField serialVersionUID
    */
    private static final long serialVersionUID = 1004L;

    /**
     * Instantiate Shop State.
     * @param name 
     */
    private ShopState(String name) {
        this.name = name;
    }

    /**
     * Get Shop actual State.
     * @return short name of the requested state
     */
    public String getState() {
        return name;
    }
}
