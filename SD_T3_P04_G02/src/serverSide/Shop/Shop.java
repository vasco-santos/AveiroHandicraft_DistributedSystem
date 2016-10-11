package serverSide.Shop;

import comInf.States.CraftsmanState;
import comInf.States.CustomerState;
import comInf.States.EntrepreneurState;
import comInf.States.InitialState;
import comInf.States.ShopState;
import comInf.Queue;
import comInf.VectorialClock;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import interfaces.RepositoryInterface;
import java.rmi.RemoteException;

/**
 * This data type implements the Store. All the communications take place here
 * concurrent solution based on Monitors as the synchronization element between
 * the store Entrepreneur and the clients.
 *
 * @author vsantos
 */
public class Shop{
    
    /**
     * Vectorial clock of Shop.
     * @serialField vetorclock
     */
    private VectorialClock vectorClock;
    
    /**
     * Number of customers in the shop.
     * @serialField nCust
     */
    private int nCust;

    /**
     * Number of customers about to enter in the Shop.
     * @serialField aboutToEnter
     */
    private int aboutToEnter;

    /**
     * Number of products in the shop.
     * @serialField nProd
     */
    private int nProd;

    /**
     * Queue by the counter formed by the customers which want to buy goods.
     * @serialField queue
     */
    private final Queue queue;

    /**
     * Products Collecting request.
     * @serialField pcr
     */
    private boolean pcr;

    /**
     * Prime Materials Delivery request.
     * @serialField pmc
     */
    private boolean pmr;

    /**
     * Repository to log changes.
     * @serialField logger
     */
    private final RepositoryInterface repo;

    /**
     * Customer is being.
     * @serialField custBalc
     */
    private final boolean[] custBalc;

    /**
     * Shop Monitor Instantiation.
     * @param repoInter Repository Interface
     */
    public Shop(RepositoryInterface repoInter)
    {
        repo = repoInter;
        this.custBalc = new boolean[InitialState.getNumberOfClients()];
        Arrays.fill(custBalc, false);
        this.queue = new Queue(InitialState.getNumberOfClients());
        vectorClock = new VectorialClock(InitialState.getNumberOfEntities(),8);
        try {
            repo.changeEntrepreneurStateAndShopState(ShopState.CLOSED, EntrepreneurState.OPENING_THE_SHOP,this.vectorClock);
        } catch (RemoteException ex) {
            Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.nCust = 0;
        this.nProd = 0;
        this.pcr = false;
        this.pmr = false;
        
    }

    /**
    *  Prepare to work operation.
    *  The entrepreneur opens the shop.
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
    */
    public synchronized void prepareToWork(VectorialClock vec) throws RemoteException
    {
        this.repo.changeEntrepreneurStateAndShopState(ShopState.OPEN, EntrepreneurState.WAITING_FOR_NEXT_TASK,vec);
    }
    
    /**
     * Appraise situation operation.
     * The entrepreneur waits for requests. She is waken up by requests.
     * @param vec Vectorial Clock.
     * @return 'C', if a customer is needing attention
     *         'M', if she should go shopping for prime materials
     *         'T', if she should go to the workshop to collect a new batch of products
     *         'E', if the simulation is over 
     * @throws java.rmi.RemoteException  Unable to call remote object
     */
    public synchronized char appraiseSit(VectorialClock vec) throws RemoteException {
        do {
            if (!this.queue.empty()) {
                return 'C';
            } else if (this.pmr) {
                return 'M';
            } else if (this.pcr) {
                return 'T';
            } else if ((boolean)this.repo.endOperEntrep(vec).getReturn()) {
                return 'E';
            } else {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } while (true);
    }
    
    /**
     * Address a customer operation.
     * The entrepreneur goes to the counter to attend a customer.
     * @param vec Vectorial Clock.
     * @return the identification of the customer
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized int addressACustomer(VectorialClock vec) throws RemoteException {
        // change state
        this.repo.changeEntrepreneurState(EntrepreneurState.ATTENDING_A_CUSTOMER,vec);
        // Queue_Remove
        int custID = (int)queue.read();
        return custID;
    }

     /**
     * Say goodbye to customer operation.
     * The entrepreneur completes the transaction and the customer is waken up.
     * @param custID identification of the customer
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */   
    public synchronized void sayGoodByeToCustomer(int custID,VectorialClock vec) throws RemoteException {
        // Set boolean array in custID
        this.custBalc[custID] = true;
        // Wake Up Client
        notifyAll();
        repo.changeEntrepreneurState(EntrepreneurState.WAITING_FOR_NEXT_TASK,vec);
    }
    /**
     * Close the door operation.
     * The entrepreneur closes the door to prevent customers to come in.
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized void closeTheDoor(VectorialClock vec) throws RemoteException
    {
        if (this.repo.GetShopState() != comInf.States.ShopState.STILLOPEN)
        {
            repo.changeShopState(ShopState.STILLOPEN,vec);
        }
    }
    /**
     * Customers in the shop operation.
     * The entrepreneur checks if there are any customers in the shop, or about to enter.
     * @param vec Vectorial Clock.
     * @return true, if there are any customers in the shop or about to enter the shop
     *         false, otherwise
     */
    public synchronized boolean customerIntTheShop(VectorialClock vec)
    {
       return this.nCust > 0 || this.aboutToEnter > 0;
    }
    
    /**
     * Prepare to leave operation.
     * The entrepreneur closes the shop.
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized void prepareToLeave(VectorialClock vec) throws RemoteException
    {
        this.repo.changeEntrepreneurStateAndShopState(ShopState.CLOSED, EntrepreneurState.CLOSING_THE_SHOP,vec);
    }
    
    /**
     * Return to shop.
     * The entrepreneur goes back to the shop.
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized void returnToShop(VectorialClock vec) throws RemoteException
    {
        this.repo.changeEntrepreneurStateAndShopState(ShopState.OPEN, EntrepreneurState.OPENING_THE_SHOP,vec);
    }

    /**
     * Entrepreneur exits shop.
     * @param alternative requested option
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized void entExitShop(char alternative,VectorialClock vec) throws RemoteException
    {
        // Verify why she was requested
        if (alternative == 'M')
        {
            this.pmr = false;
            this.repo.changeEntrepreneurStateAndPMR(EntrepreneurState.EXITING_THE_SHOP, this.pmr,vec);
        }
        else if (alternative == 'T')
        {
            this.pcr = false;
            this.repo.changeEntrepreneurStateAndPCR(EntrepreneurState.EXITING_THE_SHOP, this.pcr,vec);
        }
    }

    /**
     * Increase number of products in shop.
     * @param numberOfProducts number of products in the shop
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized void arrangeProducts(int numberOfProducts,VectorialClock vec) throws RemoteException
    {
        this.nProd += numberOfProducts;
        repo.changeNPI_S(this.nProd,vec);
    }
    
    /**
     * Prime materials needed operation.
     * The craftsman phones the entrepreneur to let her know the workshop 
     * requires more prime material.
     * @param craftID identification of the craftsman
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized void primeMaterialsNeeded(int craftID,VectorialClock vec) throws RemoteException
    {
        // Change states
        this.pmr = true;
        // Wake Up Entrepreneur
        notifyAll();
        this.repo.changeCraftsmanStateAndPMR(CraftsmanState.CONTACTING_THE_ENTREPENEUR, craftID, this.pmr,vec);
    }
    /**
     * Batch ready for transfer operation. 
     * The craftsman phones the entrepreneur to let her know she should collect 
     * a batch of goods.
     * @param craftID identification of the craftsman
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized void batchReadyForTransfer(int craftID,VectorialClock vec) throws RemoteException
    {
        // Set Product Request
        this.pcr = true;
        // Change State
        this.repo.changeCraftsmanStateAndPCR(CraftsmanState.CONTACTING_THE_ENTREPENEUR, craftID, this.pcr,vec);
        // Wake up Entrepreneur	
        notifyAll();
    }
    /**
     * Go shopping operation.
     * The customer visits the shop.
     * @param custID identification of the customer
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized void goShopping(int custID,VectorialClock vec) throws RemoteException
    {
        // Change State
        repo.changeCustomerState(CustomerState.CHECKING_SHOP_DOOR_OPEN, custID,vec);
    }
    /**
     * Is door open operation.
     * The customer checks if the shop door is open.
     * @param vec Vectorial Clock.
     * @return true, if the shop door is open
     *         false, otherwise
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized boolean isDooROpen(VectorialClock vec) throws RemoteException {
        // Verify if the customer can go in
        boolean isDoorOpen = (this.repo.GetShopState() == comInf.States.ShopState.OPEN);
        if (isDoorOpen)
        {
            this.aboutToEnter++;
        }
        return isDoorOpen;
    }
    
    /**
     * Try again later operation.
     * The customer goes back to perform his daily chores.
     * @param custID identification of the customer
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized void tryAgainLater(int custID,VectorialClock vec) throws RemoteException {
        // Change State
        repo.changeCustomerState(CustomerState.CARRYING_DAILY_CHORES, custID,vec);
    }
    /**
     * Enter the shop operation.
     * The customer enters the shop.
     * @param custID identification of the customer
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized void enterShop(int custID,VectorialClock vec) throws RemoteException {
        if (this.aboutToEnter > 0)
        {
            this.aboutToEnter--;
        }
        // New customer inside the shop
        this.nCust += 1;
        // Change State
        repo.changeCustomerStateAndCustInside(CustomerState.APRAISING_OFFER_IN_DISPLAY, custID, this.nCust,vec);
    }
    /**
     * Perusing around operation.
     * The customer inspects the offer in display and eventually picks up some products.
     * He may randomly pick up 0, 1 or 2 products.
     * @param custID identification of the customer
     * @param vec Vectorial Clock.
     * @return number of products to buy
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized int perusingAround(int custID,VectorialClock vec) throws RemoteException {
        // Verify how many products the customer is able to purchase
        if (this.nProd > 0)
        {
            int numProductsPick = randomProductsPick(this.nProd);
            if (numProductsPick > 0)
            {
                this.nProd -= numProductsPick;
                repo.changeNPI_S(this.nProd,vec);
                return numProductsPick;
            }
        }
        return 0;
    }

    /**
     * Random products to pick number Random algorithm to calculate the number
     * of products the customer will pick.
     * @param lim limit of products to purchase
     * @return number of goods to buy
     */
    private int randomProductsPick(int lim) {
        lim = (lim > 3) ? 3 : lim;
        double val = Math.random();
        if (val < 0.3) {
            return 0;
        } else if (val < 0.7) {
            return lim >= 1 ? 1 : 0;
        } else {
            return lim >= 2 ? 2 : 0;
        }
    }
    
    /**
     * I want this operation.
     * The customer queues to pay for the selected goods.
     * @param custID identification of the customer
     * @param numProducts number of selected products
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized void iWantThis(int custID, int numProducts,VectorialClock vec) throws RemoteException {
        // Change State
        // Add Customer to the Queue
        queue.write(custID);
        // Wake Up Entrepreneur
        notifyAll();
        // Add Bought Pieces to the client
        this.repo.changeCustomerStateAndBoughtProducts(CustomerState.BUYING_SOME_GOODS, custID, numProducts,vec);
        this.custBalc[custID] = false;
        // Verify when the customer is not in the balcony
        while(this.custBalc[custID] == false)
        {
            try {
                wait();
            } catch (InterruptedException ex) {Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);}
        }
    }

    /**
     * Customer exits shop
     * @param custID customer identification
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized void exitShop(int custID,VectorialClock vec) throws RemoteException {
        // Wake Up Entrepreneur
        notifyAll();
        this.nCust -= 1;
        // Change State
        repo.changeCustomerStateAndCustInside(CustomerState.CARRYING_DAILY_CHORES, custID,this.nCust,vec);
    }
}
