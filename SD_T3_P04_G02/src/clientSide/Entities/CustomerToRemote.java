package clientSide.Entities;

import comInf.States.CustomerState;
import comInf.States.InitialState;
import comInf.Tuple;
import comInf.VectorialClock;
import interfaces.RepositoryInterface;
import interfaces.ShopInterface;
import java.rmi.RemoteException;

/**
 * Data Type that defines the communication of the Customer Thread to the Remote Methods.
 * Customer methods invoke methods from the Remote Object Interface (RMI).
 * For each method, the Entity state is updated and the Vectorial Clock is incremented, sent and updated.
 * @author vsantos
 */
public class CustomerToRemote
{
    /**
     * Vectorial clock of Customer.
     * @serialField vetorclock
     */
    private final VectorialClock vectorClock;
    
    /**
     * Repository Monitor.
     * @serialField rep
     */
    private final RepositoryInterface repoInter;

    /**
     * Shop Monitor.
     * @serialField shop
     */
    private final ShopInterface shopInter;
    
    /**
     * Instantiation of Customer to Remote Data Type.
     * @param shop Shop Interface.
     * @param repo Repository Interface.
     * @param id Customer identification.
     */
    public CustomerToRemote (ShopInterface shop, RepositoryInterface repo, int id)
    {
        this.shopInter = shop;
        this.repoInter = repo;
        vectorClock = new VectorialClock(InitialState.getNumberOfEntities(),InitialState.getLocalIndexBaseCustomer() + id);
    }
    
    /**
     * Method Check For Materials invoked to the Shop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @param customerId Customer identification.
     * @throws RemoteException Unable to call remote object
     */
    public void goShopping(int customerId) throws RemoteException
    {
        vectorClock.increment();
        Tuple t = shopInter.goShopping(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Customer)Thread.currentThread()).getCustomerState()), customerId);
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Customer)Thread.currentThread()).setState((CustomerState)t.getState());
    }
    
    /**
     * Method Is Door Open invoked to the Shop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @return true, if the shop door is open
     *         false, otherwise
     * @throws RemoteException Unable to call remote object
     */
    public boolean isDoorOpen() throws RemoteException
    {
        vectorClock.increment();
        Tuple t = shopInter.isDoorOpen(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Customer)Thread.currentThread()).getCustomerState()));
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Customer)Thread.currentThread()).setState((CustomerState)t.getState());
        return (boolean)t.getReturn();
    }
    
    /**
     * Method Try Again Later invoked to the Shop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @param customerId Customer identification.
     * @throws RemoteException Unable to call remote object
     */
    public void tryAgainLater(int customerId) throws RemoteException
    {
        vectorClock.increment();
        Tuple t = shopInter.tryAgainLater(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Customer)Thread.currentThread()).getCustomerState()), customerId);
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Customer)Thread.currentThread()).setState((CustomerState)t.getState());
    }
    
    /**
     * Method Enter Shop invoked to the Shop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @param customerId Customer identification.
     * @throws RemoteException Unable to call remote object
     */
    public void enterShop(int customerId) throws RemoteException
    {
        vectorClock.increment();
        Tuple t = shopInter.enterShop(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Customer)Thread.currentThread()).getCustomerState()), customerId);
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Customer)Thread.currentThread()).setState((CustomerState)t.getState());
    }
    
    /**
     * Method Perusing Around invoked to the Shop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @param customerId Customer identification.
     * @return Number of products to buy.
     * @throws RemoteException Unable to call remote object
     */
    public int perusingAround(int customerId) throws RemoteException
    {
        vectorClock.increment();
        Tuple t = shopInter.perusingAround(new Tuple (vectorClock.getCopy(),((clientSide.Entities.Customer)Thread.currentThread()).getCustomerState()), customerId);
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Customer)Thread.currentThread()).setState((CustomerState)t.getState());
        return (int)t.getReturn();
    }
    
    /**
     * Method I Want This invoked to the Shop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @param customerId Customer identification.
     * @param numProdSelec Number of products to buy.
     * @throws RemoteException Unable to call remote object
     */
    public void iWantThis(int customerId, int numProdSelec) throws RemoteException
    {
        vectorClock.increment();
        Tuple t = shopInter.iWantThis(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Customer)Thread.currentThread()).getCustomerState()), customerId, numProdSelec);
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Customer)Thread.currentThread()).setState((CustomerState)t.getState());
    }
    
    /**
     * Method Exit Shop invoked to the Shop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @param customerId Customer identification.
     * @throws RemoteException Unable to call remote object
     */
    public void exitShop(int customerId) throws RemoteException
    {
        vectorClock.increment();
        Tuple t = shopInter.exitShop(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Customer)Thread.currentThread()).getCustomerState()), customerId);
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Customer)Thread.currentThread()).setState((CustomerState)t.getState());
    }
    
    /**
     * Method End Operation Craftsman invoked to the Repository Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @param customerId Customer identification.
     * @return if the Customer is ready to terminate.
     * @throws RemoteException Unable to call remote object
     */
    public boolean endOperCustomer (int customerId) throws RemoteException
    {
        vectorClock.increment();
        Tuple t = repoInter.endOperCustomer(customerId,vectorClock.getCopy());
        // Update Vector Clock
        vectorClock.update(t.getClock());
        return (boolean)t.getReturn();
    }
}
