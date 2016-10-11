package interfaces;

import comInf.Tuple;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *   This data type describes the interaction with the Shop, as a remote object, which is a service
 * in the Aveiro Handicraft Problem that implements the type 2 of the client-server model.
 * @author vsantos
 */
public interface ShopInterface extends Remote
{
    // Craftsman access Shop
    /**
     * Craftsman needs prime materials.
     * @param tuple with Craftsman new State and Vector Time Stamp.
     * @param craftID craftsman identification
     * @return Tuple with Vector Time Stamp and Craftsman State.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple primeMaterialsNeeded(Tuple tuple, int craftID) throws RemoteException;
    
    /**
     * Craftsman got products to transfer.
     * @param tuple with Craftsman new State and Vector Time Stamp.
     * @param craftID craftsman identification
     * @return Tuple with Vector Time Stamp and Craftsman State.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple batchReadyForTransfer(Tuple tuple, int craftID) throws RemoteException;
    
    // Customer access Shop
    /**
     * Customer goes Shopping.
     * @param tuple with Customer new State and Vector Time Stamp.
     * @param custID customer identification
     * @return Tuple with Vector Time Stamp and Customer State.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple goShopping(Tuple tuple, int custID) throws RemoteException;
    
    /**
     * Customer verifies if the shop is open.
     * @param tuple with Customer new State and Vector Time Stamp.
     * @return Tuple with Vector Time Stamp, Customer State and a boolean
     *         true, if the shop is open
     *         false, otherwise.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple isDoorOpen(Tuple tuple) throws RemoteException;
    
    /**
     * Customer decides to verify if the shop is open later.
     * @param tuple with Customer new State and Vector Time Stamp.
     * @param custID customer identification
     * @return Tuple with Vector Time Stamp and Customer State.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple tryAgainLater(Tuple tuple, int custID) throws RemoteException;
    
    /**
     * Customer enters in the shop.
     * @param tuple with Customer new State and Vector Time Stamp.
     * @param custID customer identification
     * @return Tuple with Vector Time Stamp and Customer State.
     * @throws java.rmi.RemoteException Unable to call remote object
     */ 
    public Tuple enterShop(Tuple tuple, int custID) throws RemoteException;
    
    /**
     * Customer choose products to purchase.
     * @param tuple with Customer new State and Vector Time Stamp.
     * @param custID customer identification
     * @return Tuple with Vector Time Stamp, Customer State and number of products to buy
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple perusingAround(Tuple tuple, int custID) throws RemoteException;
    
    /**
     * Customer buys the products he want.
     * @param tuple with Customer new State and Vector Time Stamp.
     * @param custID customer identification
     * @param numProducts number of products to buy
     * @return Tuple with Vector Time Stamp and Customer State.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple iWantThis(Tuple tuple, int custID, int numProducts) throws RemoteException;
    
    /**
     * Customer exits the shop.
     * @param tuple with Customer new State and Vector Time Stamp.
     * @param custID customer identification
     * @return Tuple with Vector Time Stamp and Customer State.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple exitShop(Tuple tuple, int custID) throws RemoteException;
    
    // Entrepreneur access Shop
    /**
     * Entrepreneur prepares to work, opening the shop.
     * @param tuple with Entrepreneur new State and Vector Time Stamp.
     * @return Tuple with Vector Time Stamp and Entrepreneur State.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple prepareToWork(Tuple tuple) throws RemoteException;
    
    /**
     * Entrepreneur waits for requests.
     * @param tuple with Entrepreneur new State and Vector Time Stamp.
     * @return Tuple with Vector Time Stamp, Entrepreneur State and what to do next.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple appraiseSit(Tuple tuple) throws RemoteException;
    
    /**
     * Entrepreneur attends a customer.
     * @param tuple with Entrepreneur new State and Vector Time Stamp.
     * @return Tuple with Vector Time Stamp, Entrepreneur State and customer identification
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple addressACustomer(Tuple tuple) throws RemoteException;
    
    /**
     * Entrepreneur finishes attending the customer.
     * @param tuple with Entrepreneur new State and Vector Time Stamp.
     * @param custID customer identification
     * @return Tuple with Vector Time Stamp and Entrepreneur State.
     * @throws java.rmi.RemoteException Unable to call remote object
     */ 
    public Tuple sayGoodByeToCustomer(Tuple tuple, int custID) throws RemoteException;
    
    /**
     * Entrepreneur closes the door of the shop.
     * @param tuple with Entrepreneur new State and Vector Time Stamp.
     * @return Tuple with Vector Time Stamp and Entrepreneur State.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple closeTheDoor(Tuple tuple) throws RemoteException;
    
    /**
     * Entrepreneur verifies if there is any customer in the shop.
     * @param tuple with Entrepreneur new State and Vector Time Stamp.
     * @return Tuple with Vector Time Stamp, Entrepreneur State and a boolean
     *         true, if the shop is empty
     *         false, otherwise
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple customersInTheShop(Tuple tuple) throws RemoteException;
    
    /**
     * Entrepreneur prepares to leave the shop.
     * @param tuple with Entrepreneur new State and Vector Time Stamp.
     * @return Tuple with Vector Time Stamp and Entrepreneur State.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple prepareToLeave(Tuple tuple) throws RemoteException;
    
    /**
     * Entrepreneur returns the shop.
     * @param tuple with Entrepreneur new State and Vector Time Stamp.
     * @return Tuple with Vector Time Stamp and Entrepreneur State.
     * @throws java.rmi.RemoteException Unable to call remote object
     */ 
    public Tuple returnToShop(Tuple tuple) throws RemoteException;
    
    /**
     * Entrepreneur leaves the shop.
     * @param tuple with Entrepreneur new State and Vector Time Stamp.
     * @param alternative char for the switch case
     * @return Tuple with Vector Time Stamp and Entrepreneur State.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple entExitShop(Tuple tuple, char alternative) throws RemoteException;
    
    /**
     * Entrepreneur arranges the new products in the shop.
     * @param tuple with Entrepreneur new State and Vector Time Stamp.
     * @param numberOfProducts new products
     * @return Tuple with Vector Time Stamp and Entrepreneur State.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Tuple arrangeProducts(Tuple tuple, int numberOfProducts) throws RemoteException;
    
    /**
     * Ask Server for Shutdown.
     * The client says that his work is done. Server verify if it can shutdown
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void shutDown() throws RemoteException;
}
