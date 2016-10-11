package comInf;

/**
 *  Queue Object extended from a generic static memory.
 * @author andrealves
 */
public class Queue  extends MemObject{
  
   /**
   *  Point of Insertion.
   *    @serialField insertPoint
   */
   private int insertPoint;

  /**
   *  Point of removal.
   *    @serialField removalPoint
   */
   private int removalPoint;

  /**
   *  Queue empty.
   *    @serialField empty
   */
   private boolean empty;

  /**
   *  Queue fifo Constructor.
   *    @param nElem size of the queue
   */
   public Queue (int nElem)
   {
        super (nElem);
        this.insertPoint = 0;
        this.removalPoint = 0;
        this.empty = true;
   }

  /**
   *  Add a new value to the Queue.
   *    @param val value to store
   */
   @Override
   public void write (Object val)
   {
     if ((insertPoint != removalPoint) || empty)
        { 
            mem[insertPoint] = val;
            insertPoint += 1;
            insertPoint %= nMax;
            empty = false;
        }
   }

  /**
   *  Next value to remove.
   *    @return Object value stored
   */
   @Override
   public Object read ()
   {
     Object val = null;

     if ((removalPoint != insertPoint) || !empty)
        { val = mem[removalPoint];
          removalPoint += 1;
          removalPoint %= nMax;
          empty = (insertPoint == removalPoint);
        }
     return (val);
   }

  /**
   *  Verify if Queue is Empty.
   *    @return true, if Queue has no values stored
   *            false, otherwise
   */
   public boolean empty ()
   {
     return (this.empty);
   }

  /**
   *  Verify if Queue is Full.
   *    @return true, if Queue is full
   *            false, otherwise
   */
   public boolean full ()
   {
     return (!this.empty && (removalPoint == insertPoint));
   }
}