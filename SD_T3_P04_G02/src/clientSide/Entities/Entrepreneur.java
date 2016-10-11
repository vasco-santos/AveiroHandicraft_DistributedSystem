package clientSide.Entities;

import interfaces.WorkshopInterface;
import interfaces.ShopInterface;
import interfaces.RepositoryInterface;
import interfaces.PrimeStorageInterface;
import comInf.States.EntrepreneurState;
import genclass.GenericIO;
import static java.lang.Thread.sleep;
import java.rmi.RemoteException;

/**
 * This Data Type implements the Entrepreneur Thread.
 * @author vsantos
 */
public class Entrepreneur extends Thread 
{
    /**
     * Data type to invoke the remote methods, manage states and manage vector clock.
     *   @serialField craftsmanToRemote
     */
    private final EntrepreneurToRemote entrepreneurToRemote;
    
    /**
     * Actual State of the Entrepreneur.
     * @serialField entS
     */
    private comInf.States.EntrepreneurState entS;
        
    /**
     * Instantiation of Entrepreneur thread.
     * @param repo Repository Interface.
     * @param shop Shop Interface.
     * @param workshop Workshop Interface.
     * @param ps Prime Storage Interface.
     */
    public Entrepreneur(RepositoryInterface repo, ShopInterface shop, WorkshopInterface workshop, PrimeStorageInterface ps)
    {
        super ("Entrepreneur");
        this.entS = comInf.States.EntrepreneurState.OPENING_THE_SHOP;
        this.entrepreneurToRemote = new EntrepreneurToRemote(shop, workshop, ps, repo);
    }

    /**
     * Entrepreneur Thread life cycle.
     */
    @Override
    public void run()
    {
        try
        {
            boolean out;                            // Entrepreneur is outside the shop
            char alternative = ' ';                 // Next task
            int custID;                             // Customer ID
            do {
                out = false;                        // Entrepreneur in the Shop
                entrepreneurToRemote.prepareToWork();
                while (!out) {                      
                    alternative = entrepreneurToRemote.appraiseSit(); // Entrepreneur waits for requests
                    switch (alternative) {
                        case 'C':                       // Customer Request
                            custID = entrepreneurToRemote.addressACustomer();
                            ServiceCustomer();
                            entrepreneurToRemote.sayGoodByeToCustomer(custID);
                            break;
                        case 'M':                       // Prime Materials Request
                        case 'T':                       // Transfer Products Request
                            entrepreneurToRemote.closeTheDoor();
                            out = !entrepreneurToRemote.customersInTheShop(); // Is it possible to leave the shop?
                            break;
                        case 'E':                       // Exit Shop
                            out = !entrepreneurToRemote.customersInTheShop(); // Is it possible to leave the shop?
                            break;
                    }
                }
                // Leave The Shop
                entrepreneurToRemote.prepareToLeave();
                entrepreneurToRemote.entExitShop(alternative);
                if (alternative == 'T') {               // Products Transfer
                    entrepreneurToRemote.goToworkShop(); 
                } else if (alternative == 'M') {        // Prime Materials Transfer
                    entrepreneurToRemote.visitSupplies();
                }
                entrepreneurToRemote.returnToShop();                    // Go back to Shop
            } while (!entrepreneurToRemote.endOperEntrep());          // Verify if it is over
        }catch(RemoteException e)
        {
            GenericIO.writelnString ("Exception in the invocation of a remote object" +
                                 getName () + ": " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }
    }
    
    /**
     * Service customer operation.
     * The entrepreneur services a customer for a random generated time period.
     */
    public void ServiceCustomer() 
    {    
        try
        { 
            sleep ((long) (1 + 10 * Math.random ()));
        }
        catch (InterruptedException e) {}
    }
    /**
     * Set new state.
     * @param s New entrepreneur state
     */    
    public void setState(EntrepreneurState s)
    {
        entS = s;
    }
    
    /**
     * Get Entrepreneur current state.
     * @return entS Entrepreneur State
     */
    public EntrepreneurState getEntrepreneurState()
    {
        return entS;
    }
}