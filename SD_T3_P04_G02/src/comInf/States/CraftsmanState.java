package comInf.States;

import java.io.Serializable;

/**
 * Constants that characterise the internal state of the Craftsman.
 * @author alves
 */
public enum CraftsmanState implements isAnEntitie, Serializable
{
    
    FETCHING_PRIME_MATERIALS("FPM"),    // the craftsman is fetching the prime materials he needs to produce a new piece
    PRODUCING_NEW_PIECE("PNP"),         // the craftsman is producing a new piece
    STORING_FOR_TRANSFER("SFT"),        // the craftsman is placing the produced piece in the storeroom
    CONTACTING_THE_ENTREPENEUR("CTE");  // the craftsman is contacting the entrepreneur either to collect the produced pieces or to deliver prime materials
    
    /**
     * State name.
     * @serialField name
     */
    private final String name;
    
    /**
    *  Serialization key.
    *    @serialField serialVersionUID
    */
    private static final long serialVersionUID = 1001L;

    /**
     * Instantiate Craftsman State.
     * @param name 
     */
    private CraftsmanState(String name) {
        this.name = name;
    }

    /**
     * Get Craftsman actual State.
     * @return short name of the requested state
     */
    @Override
    public String getState() {
        return name;
    }
}
