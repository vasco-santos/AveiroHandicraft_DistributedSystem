package interfaces;

import comInf.Tuple;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *   This data type describes the interaction with the Workshop, as a remote object, which is a service
 * in the Aveiro Handicraft Problem that implements the type 2 of the client-server model.
 * @author vsantos
 */
public interface WorkshopInterface  extends Remote
{
    // Craftsman access Shop
    /**
     * Craftsman pick prime material.
     * @param tuple with Craftsman new State and Vector Time Stamp.
     * @param craftID indicates the craftsman identifier on array
     * @return Tuple with Vector Time Stamp, Craftsman State and a boolean, true if materials collected
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple collectMaterials(Tuple tuple, int craftID) throws RemoteException;

    /**
     * Prepare to produce operation. 
     * The craftsman prepares materials for the production of a new product.
     * @param tuple with Craftsman new State and Vector Time Stamp.
     * @param craftId identification of the craftsman
     * @return Tuple with Vector Time Stamp and Craftsman State.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple prepareToProduce(Tuple tuple, int craftId) throws RemoteException;
            
    /**
     * Craftsman saves the artifact.
     * @param tuple with Craftsman new State and Vector Time Stamp.
     * @param craftID indicates the craftsman identifier on array.
     * @return Tuple with Vector Time Stamp, Craftsman State and the current option.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple goToStore(Tuple tuple, int craftID) throws RemoteException;

    /**
     * Craftsman get to work.
     * @param tuple with Craftsman new State and Vector Time Stamp.
     * @param craftID indicates the craftsman identifier on array
     * @return Tuple with Vector Time Stamp and Craftsman State.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple backToWork(Tuple tuple, int craftID) throws RemoteException;

    /**
     * Craftsman checks if there are prime materials enough.
     * @param tuple with Craftsman new State and Vector Time Stamp.
     * @param craftID indicates the craftsman identifier on array
     * @return Tuple with Vector Time Stamp, Craftsman State and a boolean,
     *         true if there are enough prime materials,
     *         false, otherwise.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple checkForMaterials(Tuple tuple, int craftID) throws RemoteException;
    
    // Entrepreneur access Workshop
    /**
     * Entrepreneur goes to workshop.
     * @param tuple with Entrepreneur new State and Vector Time Stamp.
     * @return Tuple with Vector Time Stamp and Entrepreneur State.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple goToworkShop(Tuple tuple) throws RemoteException;

    /**
     * Entrepreneur bring prime materials.
     * @param tuple with Entrepreneur new State and Vector Time Stamp.
     * @return Tuple with Vector Time Stamp and Entrepreneur State.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple replenishStock(Tuple tuple) throws RemoteException;
    
    /**
     * Ask Server for Shutdown.
     * The client says that his work is done. Server verify if it can shutdown
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void shutDown() throws RemoteException;
}
