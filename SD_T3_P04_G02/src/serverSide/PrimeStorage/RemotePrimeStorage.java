package serverSide.PrimeStorage;

import genclass.GenericIO;
import comInf.States.EntrepreneurState;
import comInf.States.InitialState;
import comInf.Tuple;
import comInf.VectorialClock;
import interfaces.PrimeStorageInterface;
import java.rmi.RemoteException;

/**
 * Remote Prime Storage Data Type, with Entity States State Machine.
 * Receives client request, validate Entity State, update Vectorial Clock,
 * handle the request and responds.
 * @author vsantos
 */
public class RemotePrimeStorage implements PrimeStorageInterface
{
    /**
     * Vectorial clock of Prime Storage.
     * @serialField vetorclock
     */
    private final VectorialClock vectorClock;
    
    /**
     * Prime Storage Monitor.
     *   @serialField primeStorage
     */
    private final PrimeStorage primeStorage;
    
    /**
     * Instantiate Remote Prime Storage data type.
     * @param primeStorage Prime Storage.
     */
    public RemotePrimeStorage (PrimeStorage primeStorage)
    {
        this.primeStorage = primeStorage;
        vectorClock = new VectorialClock(InitialState.getNumberOfEntities(),8);
    }
    
    /**
     * Visit Supplies invocation by the client.
     * @param tuple with Entrepreneur State and Vector Clock.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple visitSupplies(Tuple tuple) throws RemoteException
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
        VectorialClock res = primeStorage.visitSupplies(vectorClock.update(tmp));
        vectorClock.update(res);
        return new Tuple(vectorClock.getCopy(), EntrepreneurState.BUYING_PRIME_MATERIAL);
    }

    /**
     * Ask Server for Shutdown.
     * The client says that his work is done. Server verifies if it can shutdown
     */
    @Override
    public void shutDown() throws RemoteException
    {
        PrimeStorageServer.endRun();
    }
    
}