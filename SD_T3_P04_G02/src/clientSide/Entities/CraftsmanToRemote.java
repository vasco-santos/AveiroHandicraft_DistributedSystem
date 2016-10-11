package clientSide.Entities;

import comInf.States.CraftsmanState;
import comInf.States.InitialState;
import comInf.Tuple;
import comInf.VectorialClock;
import interfaces.RepositoryInterface;
import interfaces.ShopInterface;
import interfaces.WorkshopInterface;
import java.rmi.RemoteException;

/**
 * Data Type that defines the communication of the Craftsman Thread to the Remote Methods.
 * Craftsman methods invoke methods from the Remote Object Interface (RMI).
 * For each method, the Entity state is updated and the Vectorial Clock is incremented, sent and updated.
 * @author vsantos
 */
public class CraftsmanToRemote
{
    /**
     * Vectorial clock of Craftsman.
     * @serialField vetorclock
     */
    private final VectorialClock vectorClock;
    
    /**
     * Workshop Monitor.
     * @serialField workshop
     */
    private final WorkshopInterface workshopInter;
    
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
     * Instantiation of Craftsman to Remote Data Type.
     * @param workshop Workshop Interface.
     * @param shop Shop Interface.
     * @param repo Repository Interface.
     * @param id Craftsman identification.
     */
    public CraftsmanToRemote (WorkshopInterface workshop, ShopInterface shop, RepositoryInterface repo, int id)
    {
        this.workshopInter = workshop;
        this.shopInter = shop;
        this.repoInter = repo;
        vectorClock = new VectorialClock(InitialState.getNumberOfEntities(),InitialState.getLocalIndexBaseCraftsman() + id);
    }
    
    /**
     * Method Check For Materials invoked to the Workshop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @param craftsmanId Craftsman identification.
     * @return if there is prime materials or not.
     * @throws RemoteException Unable to call remote object
     */
    public boolean checkForMaterials(int craftsmanId) throws RemoteException
    {
        vectorClock.increment();
        Tuple t = workshopInter.checkForMaterials(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Craftsman)Thread.currentThread()).getCraftsmanState()), craftsmanId);
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Craftsman)Thread.currentThread()).setState((CraftsmanState)t.getState());
        return (boolean)t.getReturn();
    }
    
    /**
     * Method Prime Materials Needed invoked to the Shop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @param craftsmanId Craftsman identification.
     * @throws RemoteException Unable to call remote object
     */
    public void primeMaterialsNeeded(int craftsmanId) throws RemoteException
    {
        vectorClock.increment();
        Tuple t = shopInter.primeMaterialsNeeded(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Craftsman)Thread.currentThread()).getCraftsmanState()), craftsmanId);
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Craftsman)Thread.currentThread()).setState((CraftsmanState)t.getState());
    }
    
    /**
     * Method Back to Work invoked to the Workshop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @param craftsmanId Craftsman identification.
     * @throws RemoteException Unable to call remote object
     */
    public void backToWork(int craftsmanId) throws RemoteException
    {
        vectorClock.increment();
        Tuple t = workshopInter.backToWork(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Craftsman)Thread.currentThread()).getCraftsmanState()), craftsmanId);
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Craftsman)Thread.currentThread()).setState((CraftsmanState)t.getState());
    }
    
    /**
     * Method Collect Materials invoked to the Workshop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @param craftsmanId Craftsman identification.
     * @return true, if prime materials were sucessfully collected, 
     *      false, otherwise.
     * @throws RemoteException Unable to call remote object
     */
    public boolean collectMaterials(int craftsmanId) throws RemoteException
    {
        vectorClock.increment();
        // Valid State

        Tuple t = workshopInter.collectMaterials(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Craftsman)Thread.currentThread()).getCraftsmanState()), craftsmanId);
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Craftsman)Thread.currentThread()).setState((CraftsmanState)t.getState());
        return (boolean)t.getReturn();
    }
    
    /**
     * Method Prepare to Produce invoked to the Workshop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @param craftsmanId Craftsman identification.
     * @throws RemoteException Unable to call remote object
     */
    public void prepareToProduce(int craftsmanId) throws RemoteException
    {
        vectorClock.increment();
        Tuple t = workshopInter.prepareToProduce(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Craftsman)Thread.currentThread()).getCraftsmanState()), craftsmanId);
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Craftsman)Thread.currentThread()).setState((CraftsmanState)t.getState());
    }
    
    /**
     * Method Got to Store invoked to the Workshop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @param craftsmanId Craftsman identification.
     * @return number of products presently stored in the storeroom.
     * @throws RemoteException Unable to call remote object
     */
    public int goToStore(int craftsmanId) throws RemoteException
    {
        vectorClock.increment();
        Tuple t = workshopInter.goToStore(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Craftsman)Thread.currentThread()).getCraftsmanState()), craftsmanId);
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Craftsman)Thread.currentThread()).setState((CraftsmanState)t.getState());
        return (int)t.getReturn();
    }
    
    /**
     * Method Batch is ready for Transfer invoked to the Shop Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @param craftsmanId Craftsman identification.
     * @throws RemoteException Unable to call remote object
     */
    public void batchReadyForTransfer(int craftsmanId) throws RemoteException
    {
        vectorClock.increment();
        Tuple t = shopInter.batchReadyForTransfer(new Tuple(vectorClock.getCopy(),((clientSide.Entities.Craftsman)Thread.currentThread()).getCraftsmanState()), craftsmanId);
        // Update Vector Clock
        vectorClock.update(t.getClock());
        // Update State
        ((clientSide.Entities.Craftsman)Thread.currentThread()).setState((CraftsmanState)t.getState());
    }
    
    /**
     * Method End Operation Craftsman invoked to the Repository Remote Object.
     * The Entity state is updated and the Vectorial Clock is incremented, sent and updated.
     * @param craftsmanId Craftsman identification.
     * @return if the Craftsman is ready to terminate.
     * @throws RemoteException Unable to call remote object
     */
    public boolean endOperCraftsman (int craftsmanId) throws RemoteException
    {
        vectorClock.increment();
        Tuple t = repoInter.endOperCraftsman(craftsmanId,vectorClock.getCopy());
        // Update Vector Clock
        vectorClock.update(t.getClock());
        return (boolean)t.getReturn();
    }
}
