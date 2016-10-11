package serverSide.Workshop;

import comInf.States.CraftsmanState;
import comInf.States.EntrepreneurState;
import comInf.States.InitialState;
import comInf.Tuple;
import comInf.VectorialClock;
import genclass.GenericIO;
import interfaces.WorkshopInterface;
import java.rmi.RemoteException;

/**
 * Remote Workshop Data Type, with Entity States State Machine.
 * Receives client request, validate Entity State, update Vectorial Clock,
 * handle the request and responds.
 * @author vsantos
 */
public class RemoteWorkshop implements WorkshopInterface
{
    /**
     * Vectorial clock of Shop.
     * @serialField vetorclock
     */
    private final VectorialClock vectorClock;
    
    /**
     * Workshop Monitor
     *   @serialField workshop.
     */
    private final Workshop workshop;
    
    /**
     * Instantiation of Remote Workshop Data Type.
     * @param workshop Workshop Monitor.
     */
    public RemoteWorkshop (Workshop workshop)
    {
        this.workshop = workshop;
        this.vectorClock = new VectorialClock(InitialState.getNumberOfEntities(),8);
    }

    /**
     * Collect Materials invocation by the client.
     * @param tuple with Craftsman State and Vector Clock.
     * @param craftID craftsman identification.
     * @return Tuple with next state, operation result and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple collectMaterials(Tuple tuple, int craftID) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != CraftsmanState.FETCHING_PRIME_MATERIALS)
        {
            GenericIO.writeString("Invalid State!\nExpected: FETCHING_PRIME_MATERIALS\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        boolean collectedMaterials = workshop.collectMaterials(craftID, vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), collectedMaterials, CraftsmanState.FETCHING_PRIME_MATERIALS);
    }

    /**
     * Prepare to Produce invocation by the client.
     * @param tuple with Craftsman State and Vector Clock.
     * @param craftId craftsman identification.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple prepareToProduce(Tuple tuple, int craftId) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != CraftsmanState.FETCHING_PRIME_MATERIALS)
        {
            GenericIO.writeString("Invalid State!\nExpected: FETCHING_PRIME_MATERIALS\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        workshop.prepareToProduce(craftId, vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), CraftsmanState.PRODUCING_NEW_PIECE);
    }

    /**
     * Go to Store invocation by the client.
     * @param tuple with Craftsman State and Vector Clock.
     * @param craftID craftsman identification.
     * @return Tuple with next state, operation result and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple goToStore(Tuple tuple, int craftID) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != CraftsmanState.PRODUCING_NEW_PIECE)
        {
            GenericIO.writeString("Invalid State!\nExpected: PRODUCING_NEW_PIECE\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        int numberOfProductsStored = workshop.goToStore(craftID, vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), numberOfProductsStored, CraftsmanState.STORING_FOR_TRANSFER);
    }

    /**
     * Back to work invocation by the client.
     * @param tuple with Craftsman State and Vector Clock.
     * @param craftID craftsman identification.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException Unable to locate remote object
     */
    @Override
    public Tuple backToWork(Tuple tuple, int craftID) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != CraftsmanState.CONTACTING_THE_ENTREPENEUR && tuple.getState() != CraftsmanState.STORING_FOR_TRANSFER)
        {
            GenericIO.writeString("Invalid State!\nExpected: CONTACTING_THE_ENTREPENEUR OR STORING_FOR_TRANSFER\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        workshop.backToWork(craftID, vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), CraftsmanState.FETCHING_PRIME_MATERIALS);
    }

    /**
     * Check for Materials invocation by the client.
     * @param tuple with Craftsman State and Vector Clock.
     * @param craftID craftsman identification.
     * @return Tuple with next state, operation result and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple checkForMaterials(Tuple tuple, int craftID) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != CraftsmanState.FETCHING_PRIME_MATERIALS)
        {
            GenericIO.writeString("Invalid State!\nExpected: FETCHING_PRIME_MATERIALS\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        boolean enoughMaterials = workshop.checkForMaterials(craftID, vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), enoughMaterials, CraftsmanState.FETCHING_PRIME_MATERIALS);
    }

    /**
     * Go to Workshop invocation by the client.
     * @param tuple with Entrepreneur State and Vector Clock.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple goToworkShop(Tuple tuple) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != EntrepreneurState.EXITING_THE_SHOP)
        {
            GenericIO.writeString("Invalid State!\nExpected: EXITING_THE_SHOP\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        VectorialClock tmp = tuple.getClock();
        tmp.increment();
        VectorialClock res = workshop.goToworkShop(vectorClock.update(tmp));
        vectorClock.update(res);
        return new Tuple (vectorClock.getCopy(), EntrepreneurState.COLLECTING_BATCH_OF_PRODUCTS);
    }

    /**
     * Replenish Stock invocation by the client.
     * @param tuple with Entrepreneur State and Vector Clock.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException Unable to locate remote object
     */
    @Override
    public Tuple replenishStock(Tuple tuple) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != EntrepreneurState.BUYING_PRIME_MATERIAL)
        {
            GenericIO.writeString("Invalid State!\nExpected: BUYING_PRIME_MATERIAL\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        workshop.replenishStock(vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), EntrepreneurState.DELIVERING_PRIME_MATERIALS);
    }

    /**
     * Ask Server for Shutdown.
     * The client says that his work is done. Server verifies if it can shutdown
     */
    @Override
    public void shutDown() throws RemoteException
    {
        WorkshopServer.endRun();
    }
}
