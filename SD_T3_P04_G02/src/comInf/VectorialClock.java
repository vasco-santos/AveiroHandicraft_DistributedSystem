package comInf;

import java.io.Serializable;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Vectorial Clock Data Type to synchronize the Entities and Monitors with threadsafe operations.
 * @author André
 */
public class VectorialClock implements Serializable,Cloneable{

    /**
     * TimeStamp.
     *   @serialField ts Integer timestantes
     */
    private int[] ts;

    /**
     * Local Index of the Entity.
     *   @serialField localIndex Integer local index
     */
    private final int localIndex;
    
    /**
     * Instantiation of the Vectorial Clock Data Type.
     * @param size size of the vector clock.
     * @param localIndex local index of the entity.
     */
    public VectorialClock(int size, int localIndex)
    {    
        this.ts = new int[size];
        this.localIndex = localIndex;
        Arrays.fill(ts, 0);
    }

    // Operations to the Vector Clock

    /**
     * Increment the Time Stamp.
     */
    public synchronized void increment()
    {
        ts[localIndex] += 1;
    }

    /**
     * Update the Vectorial Clock Time Stamp.
     * @param tsamp time stamp.
     */
    public synchronized VectorialClock update(VectorialClock tsamp)
    {
        int []tmp = tsamp.getIntegerArray();
        for(int i = 0; i < 7;i++){
            if (tmp[i] > ts[i]){
                ts[i] = tmp[i];
            }
        }
        return getCopy();
    }

    /**
     * Get Copy of the current Vectorial Clock.
     * @return current vectorial clock.
     */
    public synchronized VectorialClock getCopy()
    {
        VectorialClock copia = null;
                //= new VectorTimeStamp(ts.length,localIndex);
        try {
           copia = (VectorialClock)this.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(VectorialClock.class.getName()).log(Level.SEVERE, null, ex);
        }
        return copia;
    }

    /**
     * Get Time Stamp Size.
     * @return size
     */
    public synchronized int getSize()
    {
        return ts.length;
    }

    /**
     * Get Time Stamp Array.
     * @return time stamp array.
     */
    public synchronized int[] getIntegerArray()
    {
        int copia []= null;
        copia = (int [])ts.clone();
        return copia;
    }
}
