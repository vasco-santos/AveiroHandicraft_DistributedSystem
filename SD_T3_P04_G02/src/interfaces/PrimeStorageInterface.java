package interfaces;

import comInf.Tuple;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *   This data type describes the interaction with the Prime Storage, as a remote object, which is a service
 * in the Aveiro Handicraft Problem that implements the type 2 of the client-server model.
 * @author vsantos
 */
public interface PrimeStorageInterface extends Remote
{
    // Entrepreneur access Prime Storage
    /**
    *  Visit suppliers operation.
    *  The entrepreneur goes shopping for prime materials.
     * @param tuple with Entrepreneur new State and Vector Time Stamp.
     * @return Tuple with Entrepreneur state and Vector Time Stamp.
     * @throws java.rmi.RemoteException Unable to call remote object
    */
    public Tuple visitSupplies(Tuple tuple) throws RemoteException;
    
    /**
     * Ask Server for Shutdown.
     * The client says that his work is done. Server verify if it can shutdown
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void shutDown() throws RemoteException;
}
