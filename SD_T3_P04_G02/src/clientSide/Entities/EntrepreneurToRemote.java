package clientSide.Entities;

import comInf.States.EntrepreneurState;
import comInf.States.InitialState;
import comInf.Tuple;
import comInf.VectorialClock;
import interfaces.PrimeStorageInterface;
import interfaces.RepositoryInterface;
import interfaces.ShopInterface;
import interfaces.WorkshopInterface;
import java.rmi.RemoteException;

/**
 * Data Type that defines the communication of the Entrepreneur Thread to the Remote Methods.
 * Entrepreneur methods invoke methods from the Remote Object Interface (RMI).
 * For each method, the Entity state is updated and the Vectorial Clock is incremented, sent and updated.
 * @author vsantos
 */
public class EntrepreneurToRemote
{
     /**
     * Vectorial clock of Entrepreneur.
     * @serialField vetorclock
     */
    private final VectorialClock vectorClock;
    
    /**
     * Shop Monitor.
     * @serialField shop
     */
    private final ShopInterface shopInter;
    
    /**
     * Workshop Monitor.
     * @serialField workshop
     */
    private final WorkshopInterface workshopInter;
    
    /**
     * Prime Storage Monitor.
     * @serialField primeStorage
     */
    private final PrimeStorageInterface primeStorageInter;
    
    /**
     * Repository Monitor.
     * @serialField rep
     */
    private final RepositoryInterface repoInter;
    
    /**
     * Instantiation of Entrepreneur to Remote Data Type.
     * @param shop shop interface
     * @param workshop workshop interface
     * @param primeS primestorage iterface
     * @param repo repository interface
     */
    public EntrepreneurToRemote (ShopInterface shop, WorkshopInterface workshop, PrimeStorageInterface primeS, RepositoryInterface repo)
    {
        this.shopInter = shop;
        this.workshopInter = workshop;
        this.primeStorageInter = primeS;
        this.repoInter = repo;
        this.vectorClock = new VectorialClock(InitialState.getNumberOfEntities(),InitialState.getLocalIndexEntrepreneur());
    }
    
    /**
     * Method Prepare to Work invoked to the Shop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @throws RemoteException Unable to call remote object
     */
    public void prepareToWork() throws RemoteException
    {
        vectorClock.increment();
        Tuple t = shopInter.prepareToWork(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Entrepreneur)Thread.currentThread()).getEntrepreneurState()));
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Entrepreneur)Thread.currentThread()).setState((EntrepreneurState)t.getState());
    }
    
    /**
     * Method Prepare to Work invoked to the Shop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @return Entrepreneur next duty.
     * @throws RemoteException Unable to call remote object
     */
    public char appraiseSit() throws RemoteException
    {
        vectorClock.increment();
        Tuple t = shopInter.appraiseSit(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Entrepreneur)Thread.currentThread()).getEntrepreneurState()));
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Entrepreneur)Thread.currentThread()).setState((EntrepreneurState)t.getState());
        return (char)t.getReturn();
    }
    
    /**
     * Method Address a Customer invoked to the Shop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @return Customer identification.
     * @throws RemoteException Unable to call remote object
     */
    public int addressACustomer() throws RemoteException
    {
        vectorClock.increment();
        Tuple t = shopInter.addressACustomer(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Entrepreneur)Thread.currentThread()).getEntrepreneurState()));
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Entrepreneur)Thread.currentThread()).setState((EntrepreneurState)t.getState());
        return (int)t.getReturn();
    }
    
    /**
     * Method Say good bye to Customer invoked to the Shop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @param custID Customer identification.
     * @throws RemoteException Unable to call remote object
     */
    public void sayGoodByeToCustomer(int custID) throws RemoteException
    {
        vectorClock.increment();
        Tuple t = shopInter.sayGoodByeToCustomer(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Entrepreneur)Thread.currentThread()).getEntrepreneurState()), custID);
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Entrepreneur)Thread.currentThread()).setState((EntrepreneurState)t.getState());
    }
    
    /**
     * Method Close the door invoked to the Shop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @throws RemoteException Unable to call remote object
     */
    public void closeTheDoor() throws RemoteException
    {
        vectorClock.increment();
        Tuple t = shopInter.closeTheDoor(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Entrepreneur)Thread.currentThread()).getEntrepreneurState()));
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Entrepreneur)Thread.currentThread()).setState((EntrepreneurState)t.getState());
    }
    
    /**
     * Method Customers in the Shop invoked to the Shop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @return if there is any customer in the shop.
     * @throws RemoteException Unable to call remote object
     */
    public boolean customersInTheShop() throws RemoteException
    {
        vectorClock.increment();
        Tuple t = shopInter.customersInTheShop(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Entrepreneur)Thread.currentThread()).getEntrepreneurState()));
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Entrepreneur)Thread.currentThread()).setState((EntrepreneurState)t.getState());
        return (boolean)t.getReturn();
    }
    
    /**
     * Method Prepare to leave invoked to the Shop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @throws RemoteException Unable to call remote object
     */
    public void prepareToLeave() throws RemoteException
    {
        vectorClock.increment();
        Tuple t = shopInter.prepareToLeave(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Entrepreneur)Thread.currentThread()).getEntrepreneurState()));
        // Update Vector Clock 
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Entrepreneur)Thread.currentThread()).setState((EntrepreneurState)t.getState());
    }
    
    /**
     * Method Entrepreneur exits Shop invoked to the Shop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @param alternative reason for exiting shop.
     * @throws RemoteException Unable to call remote object
     */
    public void entExitShop(char alternative) throws RemoteException
    {
        vectorClock.increment();
        Tuple t = shopInter.entExitShop(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Entrepreneur)Thread.currentThread()).getEntrepreneurState()), alternative);
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Entrepreneur)Thread.currentThread()).setState((EntrepreneurState)t.getState());
    }
    
    /**
     * Method Go to Workshop invoked to the Workshop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @throws RemoteException Unable to call remote object
     */
    public void goToworkShop() throws RemoteException
    {
        vectorClock.increment();
        Tuple t = workshopInter.goToworkShop(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Entrepreneur)Thread.currentThread()).getEntrepreneurState())); 
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Entrepreneur)Thread.currentThread()).setState((EntrepreneurState)t.getState());
    }
    
    /**
     * Method Visit Supplies invoked to the Prime Storage Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @throws RemoteException Unable to call remote object
     */
    public void visitSupplies() throws RemoteException
    {
        vectorClock.increment();
        Tuple t = primeStorageInter.visitSupplies(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Entrepreneur)Thread.currentThread()).getEntrepreneurState()));
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Entrepreneur)Thread.currentThread()).setState((EntrepreneurState)t.getState());
    }
    
    /**
     * Method Return to Shop invoked to the Shop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @throws RemoteException Unable to call remote object
     */
    public void returnToShop() throws RemoteException
    {
        vectorClock.increment();
        Tuple t = shopInter.returnToShop(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Entrepreneur)Thread.currentThread()).getEntrepreneurState()));
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Entrepreneur)Thread.currentThread()).setState((EntrepreneurState)t.getState());
        
    }
    
    /**
     * Method End Operation Entrepreneur invoked to the Repository Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @return if the Entrepreneur is ready to terminate.
     * @throws RemoteException Unable to call remote object
     */
    public boolean endOperEntrep() throws RemoteException
    {
        vectorClock.increment();
        Tuple t =repoInter.endOperEntrep(vectorClock.getCopy());
        // Update Vector Clock
        vectorClock.update(t.getClock());
        return (boolean)t.getReturn();
    }
    
}
