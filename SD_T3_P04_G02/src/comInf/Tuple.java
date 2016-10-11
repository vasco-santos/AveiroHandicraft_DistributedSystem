package comInf;

import java.io.Serializable;

/*
 * Tuple Data Type to allow the return of more than one value.
 * Data type used to return Entities states and Vectorial Clocks.
 * @author André
 */
public class Tuple implements Serializable
{
    /**
     * Vectorial Clock.
     *   @serialField clock VectorialClock clock
     */
    private VectorialClock vc;
    
    /**
     * Object Data type 1.
     *   @serialField r Object r 
     */
    private Object r = null;
    
    /**
     * Object Data Type 2-
     *   @serialField r2 Object r2 
     */
    private Object r2 = null;
    
    /**
     * Instantiation of the Tuple Data Type (Type 1).
     * @param vc Vectorial Clock.
     */
    public Tuple(VectorialClock vc)
    {
        this.vc = vc;
    }

    /**
     * Instantiation of the Tuple Data Type (Type 2).
     * @param vc Vectorial Clock.
     * @param r Value of the function to return.
     */
    public Tuple(VectorialClock vc, Object r) {
        this.vc = vc;
        this.r = r;
    }
    
    /**
     * Instantiation of the Tuple Data Type (Type 3).
     * @param vc Vectorial Clock.
     * @param r Value of the function to return.
     * @param r2 Entity State.
     */
    public Tuple(VectorialClock vc, Object r, Object r2) {
        this.vc = vc;
        this.r = r;
        this.r2 = r2;
    }
    
    /**
     * Get Value of the function to return.
     * @return Value returned.
     */
    public synchronized Object getReturn()
    {
        return r;
    }
    
    /**
     * Get State of the Entity.
     * @return State.
     */
    public synchronized Object getState()
    {
        return r2 != null ? r2 : r;
    }
    
    /**
     * Get Vectorial Clock.
     * @return clock.
     */
    public synchronized VectorialClock getClock()
    {
        return vc;
    }
}
