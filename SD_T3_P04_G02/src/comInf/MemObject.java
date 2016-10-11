package comInf;

/**
 * Generic Static Memory.
 * @author andrealves
 */
public abstract class MemObject
{
  /**
   *  Memory size.
   */
   protected int nMax = 0;

  /**
   *  Array to store the Objects.
   */
   protected Object [] mem = null;

  /**
   *  Static Memory Constructor.
   *    @param nElem memory size
   */
   protected MemObject (int nElem)
   {
     mem = new Object [nElem];
     nMax = nElem;
   }

  /**
   *  Store Value (virtual method).
   *    @param val value to store
   */
   protected abstract void write (Object val);

  /**
   *  Get Value (virtual method).
   *    @return Object value stored
   */
   protected abstract Object read ();
}

