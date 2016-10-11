package serverSide.PrimeStorage;

import comInf.States.EntrepreneurState;
import comInf.States.InitialState;
import comInf.Tuple;
import comInf.VectorialClock;
import interfaces.RepositoryInterface;
import interfaces.WorkshopInterface;
import java.rmi.RemoteException;

/**
 *  This data type implements the PrimeStorage Monitor - all the communications take place here
 *  concurrent solution based on Monitors as the synchronization element between the store Entrepreneur.
 * @author vsantos
 */
public class PrimeStorage{
 
    /**
     * Number of prime materials available.
     * @serialField stock
     */
    private int stock;
    
    /**
     * Repository to log changes.
     * @serialField logger
     */
    private final RepositoryInterface repo;
    
    /**
     * Repository to log changes.
     * @serialField logger
     */
    private final WorkshopInterface workshop;
    
    /**
     * Instantiation of Prime Storage Monitor.
     * @param repoInter Repository Interface.
     * @param workshopInter Workshop Interface.
     */
    public PrimeStorage(RepositoryInterface repoInter,WorkshopInterface workshopInter)
    {
        repo = repoInter;
        workshop = workshopInter;
        this.stock = InitialState.getTotalNumberOfPrimeMaterials();
    }
    
    /**
    * Visit suppliers operation.
    *  The entrepreneur goes for prime materials.
    * @param vec Vectorial Clock.
    * @return new Vectorial Clock.
    * @throws java.rmi.RemoteException Unable to call remote object
    */
    public synchronized VectorialClock visitSupplies(VectorialClock vec) throws RemoteException 
    {
        // Verify if the Prime Storage has availability
        if (this.stock >= InitialState.getNumberOfSuppliedPrimes())
        {
            // Update Prime Storage Stock
            this.stock -= InitialState.getNumberOfSuppliedPrimes();
            repo.changeEntrepreneurStateAndPrimeMaterialsS(EntrepreneurState.BUYING_PRIME_MATERIAL,vec);
            VectorialClock res = workshop.replenishStock(new Tuple(vec, EntrepreneurState.BUYING_PRIME_MATERIAL)).getClock();
            return res.getCopy();
        }
        else
        {
            repo.changeEntrepreneurState(EntrepreneurState.BUYING_PRIME_MATERIAL,vec);
        }
        return vec.getCopy();
    }
}