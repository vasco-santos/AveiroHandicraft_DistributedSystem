package clientSide.Entities;

import interfaces.WorkshopInterface;
import interfaces.ShopInterface;
import interfaces.RepositoryInterface;
import comInf.States.CraftsmanState;
import comInf.States.InitialState;
import genclass.GenericIO;
import static java.lang.Thread.sleep;
import java.rmi.RemoteException;

/**
 * This Data Type implements the Craftsman Thread.
 * @author vsantos
 */
public class Craftsman extends Thread
{  
    /**
     * Data type to invoke the remote methods, manage states and manage vector clock.
     *   @serialField craftsmanToRemote
     */
    private final CraftsmanToRemote craftsmanToRemote;
    
    /**
     * Actual State of the Craftsman.
     *   @serialField craftS
     */
    private comInf.States.CraftsmanState craftS;

    /**
     * Craftsman thread Identification.
     * @serialField craftsmanId
     */
    private final int craftsmanId;

    /**
     * Instantiation of Craftsman thread.
     * @param id Craftsman identification
     * @param repo repo interface
     * @param shop shop interface
     * @param workshop wokshop interface
     */
    public Craftsman(int id, RepositoryInterface repo, ShopInterface shop, WorkshopInterface workshop)
    {
        super ("Craftsman_" + id);
        craftsmanId = id;
        craftsmanToRemote = new CraftsmanToRemote(workshop, shop, repo, id);
        craftS = CraftsmanState.FETCHING_PRIME_MATERIALS;
    }

    /**
     * Craftsman thread life cycle.
     */
    @Override
    public void run()
    {
        try
        {
            int numProd = 0;                // Number of products in store room
            do {   // Verify if is necessary a request to the entrepreneur
                if (!craftsmanToRemote.checkForMaterials(this.craftsmanId)) 
                {
                    craftsmanToRemote.primeMaterialsNeeded(this.craftsmanId);
                    craftsmanToRemote.backToWork(this.craftsmanId);
                } // Collect Materials if possible
                else if (craftsmanToRemote.collectMaterials(this.craftsmanId))
                {
                    craftsmanToRemote.prepareToProduce(this.craftsmanId);
                    shapingItUp();
                    numProd = craftsmanToRemote.goToStore(this.craftsmanId);
                    // Verify if it is necessary to request the Entrepreneur
                    if (numProd >= InitialState.getNumberOfProductsToTransfer())
                    {
                        craftsmanToRemote.batchReadyForTransfer(this.craftsmanId);
                    }
                    craftsmanToRemote.backToWork(this.craftsmanId);
                }
            } while (!craftsmanToRemote.endOperCraftsman(this.craftsmanId));
        }catch(RemoteException e)
        {
            GenericIO.writelnString ("Exception in the invocation of a remote object" +
                                 getName () + ": " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }
    }

    /**
     * Shaping It Up operation The craftsman builds the product for a random
     * generated period of time.
     */
    public void shapingItUp()
    {
        try {
            sleep((long) (1 + 10 * Math.random()));
        } catch (InterruptedException e) {
        }
    }
    
    /**
     * Set new state.
     * @param s New craftsman state
     */
    public void setState(CraftsmanState s)
    {
        craftS = s;
    }
    
    /**
     * Get Craftsman current state.
     * @return craftS Craftsman State
     */
    public CraftsmanState getCraftsmanState()
    {
        return craftS;
    }
}
