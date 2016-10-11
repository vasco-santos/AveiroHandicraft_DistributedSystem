package clientSide.Entities;

import interfaces.ShopInterface;
import interfaces.RepositoryInterface;
import comInf.States.CustomerState;
import genclass.GenericIO;
import static java.lang.Thread.sleep;
import java.rmi.RemoteException;

/**
 * This Data Type implements the Customer Thread.
 * @author vsantos
 */
public class Customer extends Thread
{
    /**
     * Data type to invoke the remote methods, manage states and manage vector clock.
     *   @serialField craftsmanToRemote
     */
    private final CustomerToRemote customerToRemote;
    
    /**
     * Actual State of the Customer.
     */
    private comInf.States.CustomerState custS;
    
    /**
     * Customer Thread Identification.
     * @serialField customerId
     */
    private final int customerId;
    
   
    /**
     * Instantiation of Customer thread.
     * @param id Customer Identification
     * @param repo repository interface
     * @param shop shop interface
     */
    public Customer(int id, RepositoryInterface repo, ShopInterface shop)
    {
        super ("Customer_" + id);
        this.customerId = id;
        this.custS = CustomerState.CARRYING_DAILY_CHORES;
        this.customerToRemote = new CustomerToRemote(shop, repo, id);
    }
    
    /**
     * Customer Thread Life Cycle.
     */
    @Override
    public void run()
    {
        try
        {
            int numProdSelec = 0;            // Number of products to buy
            do {
                LivingNormalLife();
                customerToRemote.goShopping(this.customerId);
                if (!customerToRemote.isDoorOpen())        // Customer is going to enter
                {
                    customerToRemote.tryAgainLater(this.customerId);
                    continue;
                }
                customerToRemote.enterShop(this.customerId);
                numProdSelec = customerToRemote.perusingAround(customerId); // Decide how many Products wants to buy
                if (numProdSelec != 0)       // Customer wants to buy products
                {
                    customerToRemote.iWantThis(this.customerId, numProdSelec);
                }
                customerToRemote.exitShop(customerId);
            }while (!customerToRemote.endOperCustomer(customerId)); // Verify if it is over
        }catch(RemoteException e)
        {
            GenericIO.writelnString ("Exception in the invocation of a remote object" +
                                 getName () + ": " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }
    }
        
    /**
    * Living normal life operation.
    * The customer lives his normal life for a random generated time period.
    */
    public void LivingNormalLife() 
    {
        try
        { 
            sleep ((long) (1 + 10 * Math.random ()));
        }
        catch (InterruptedException e) {}
    }
     /**
     * Set new state.
     * @param s New customer state
     */   
    public void setState(CustomerState s)
    {
        custS = s;
    }
    
    /**
     * Get Customer current state.
     * @return custS Customer State
     */
    public CustomerState getCustomerState()
    {
        return custS;
    }
}
