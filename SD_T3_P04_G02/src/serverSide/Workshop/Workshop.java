package serverSide.Workshop;

import comInf.States.CraftsmanState;
import comInf.States.EntrepreneurState;
import comInf.States.InitialState;
import comInf.Tuple;
import comInf.VectorialClock;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import interfaces.RepositoryInterface;
import interfaces.ShopInterface;
import java.rmi.RemoteException;

/**
 * This data type implements the Workshop. All the communications take place
 * here concurrent solution based on Monitors as the synchronization element
 * between the store Entrepreneur and the clients.
 *
 * @author vsantos
 */
public class Workshop {

    /**
     * Vectorial clock of workshop.
     * @serialField vetorclock
     */
    private final VectorialClock vectorClock;

    /**
     * Number of prime materials in the workshop.
     * @serialField nPrimeMat
     */
    private int nPrimeMat;

    /**
     * Number of finished products in the storeroom.
     * @serialField nFinishedProd
     */
    private int nFinishedProd;

    /**
     * Number of times the workshop was supplied.
     * @serialField numberSupplies
     */
    private int numberSupplies;

    /**
     * Repository to log changes.
     * @serialField logger
     */
    private final RepositoryInterface repo;

    /**
     * Repository to log changes.
     * @serialField logger
     */
    private final ShopInterface shop;

    /**
     * Products transfer required.
     * @serialField productsRequired
     */
    private boolean productsRequired;

    /**
     * Prime Materials required.
     * @serialField primesRequired
     */
    private boolean primesRequired;

    /**
     * Boolean Array that is set when each craftsman is working on a product.
     * @serialField isWorking
     */
    private final boolean[] isWorking;

    /**
     * Workshop Instantiation.
     * @param repoInter Repository Interface.
     * @param shopInter Shop Interface.
     */
    public Workshop(RepositoryInterface repoInter, ShopInterface shopInter) {
        repo = repoInter;
        shop = shopInter;
        this.nFinishedProd = 0;
        this.nPrimeMat = 0;
        this.numberSupplies = 0;
        this.productsRequired = false;
        this.isWorking = new boolean[InitialState.getNumberOfCraftmen()];
        Arrays.fill(this.isWorking, Boolean.FALSE);
        vectorClock = new VectorialClock(InitialState.getNumberOfEntities(), 8);
    }

    /**
     * Collect materials operation. The craftsman gets the prime materials that
     * he needs to produce a product.
     * @param craftID identification of the customer
     * @param vec Vectorial Clock.
     * @return Tuple with Craftsman next State, Vector Time Stamp and a boolean
     * true, if prime materials were sucessfully collected, 
     * false, otherwise.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized boolean collectMaterials(int craftID, VectorialClock vec) throws RemoteException
    {
        // Wait for Prime Materials
        while ((this.nPrimeMat < InitialState.getNumberOfPrimeMaterialsPerProduct())
                && this.numberSupplies != InitialState.getNumberOfDeleveries()) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Workshop.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // Available Prime Materials to produce
        if (this.nPrimeMat >= InitialState.getNumberOfPrimeMaterialsPerProduct()
                && (this.primesRequired
                || this.numberSupplies == InitialState.getNumberOfDeleveries()
                || this.nPrimeMat > InitialState.getNumberOfPrimeMaterialsAlert())) {
            this.nPrimeMat -= InitialState.getNumberOfPrimeMaterialsPerProduct();
            repo.changeCraftsmanStateAndPrimeMaterials(CraftsmanState.FETCHING_PRIME_MATERIALS, craftID, this.nPrimeMat,vec);
            isWorking[craftID] = true;
            return true;
        }
        repo.changeCraftsmanState(CraftsmanState.FETCHING_PRIME_MATERIALS, craftID, vec);
        return false;
    }

    /**
     * Prepare to produce operation. The craftsman prepares materials for the
     * production of a new product.
     * @param craftId identification of the craftsman
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized void prepareToProduce(int craftId, VectorialClock vec) throws RemoteException
    {
        repo.changeCraftsmanState(CraftsmanState.PRODUCING_NEW_PIECE, craftId, vec);
    }

    /**
     * Go to store operation. The craftsman stores the finished product.
     * @param craftId identification of the craftsman.
     * @param vec Vectorial Clock.
     * @return Tuple with Craftsman next State, Vector Time Stamp and 
     * number of products presently stored in the storeroom.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized int goToStore(int craftId, VectorialClock vec) throws RemoteException
    {
        this.nFinishedProd += 1;
        repo.goToStoreLog(CraftsmanState.STORING_FOR_TRANSFER, craftId,vec);
        // Craftsman finished the product
        isWorking[craftId] = false;
        // Send request to the Entrepreneur if the number of products in the store
        // room is higher than the alert number and the request was not made yet
        if ((this.nFinishedProd >= InitialState.getNumberOfProductsToTransfer()) && !this.productsRequired)
        {
            this.productsRequired = true;
            return this.nFinishedProd;
        }
        // Final Request to the Entrepreneur - all primes deliveries made, no primes 
        // in the workshop and all Crafstmen finished the products they were producing
        else if (this.numberSupplies == InitialState.getNumberOfDeleveries() 
                && this.nPrimeMat == 0 && this.noCraftWorking())
        {
            return InitialState.getNumberOfProductsToTransfer();
        }
        return 0;
    }

    /**
     * Back to work operation. The craftsman returns to work.
     * @param craftId identification of the craftsman
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized void backToWork(int craftId, VectorialClock vec) throws RemoteException 
    {
        repo.changeCraftsmanState(CraftsmanState.FETCHING_PRIME_MATERIALS, craftId, vec);
    }

    /**
     * Check for materials operation. Verify if there are enough prime
     * materials.
     * @param craftID indicates the craftsman identifier on array
     * @param vec Vectorial Clock.
     * @return if there is prime materials or not
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized boolean checkForMaterials(int craftID, VectorialClock vec) throws RemoteException
    {
        if ((this.nPrimeMat <= InitialState.getNumberOfPrimeMaterialsAlert()) && !this.repo.getPMR()
                && this.numberSupplies < InitialState.getNumberOfDeleveries() && !this.primesRequired) {
            this.primesRequired = true;
            return false;
        }
        return true;
    }

    /**
     * Go to the workshop operation. The entrepreneur collects a batch of
     * products from the storeroom at the workshop.
     * @param vec Vectorial Clock.
     * @return Vectorial Clock updated.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized VectorialClock goToworkShop(VectorialClock vec) throws RemoteException
    {
        // Change State
        this.productsRequired = false;
        int productsToTransfer = nFinishedProd;
        this.nFinishedProd = 0;
        this.repo.changeEntrepreneurStateAndNPI_W(EntrepreneurState.COLLECTING_BATCH_OF_PRODUCTS, nFinishedProd,vec);
        VectorialClock res = shop.arrangeProducts(new Tuple(vec, EntrepreneurState.COLLECTING_BATCH_OF_PRODUCTS), productsToTransfer).getClock();
        return res;
    }

    /**
     * Replenish Stock operation. The Entrepreneur delivers Prime Materials to
     * the workshop.
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public synchronized void replenishStock(VectorialClock vec) throws RemoteException
    {
        this.primesRequired = false;
        // Change State
        this.nPrimeMat += InitialState.getNumberOfSuppliedPrimes();
        this.numberSupplies += 1;
        // Wake Up Craftsmen
        notifyAll();
        this.repo.changeEntrepreneurStateAndPrimeMatAndNumberSupplies(EntrepreneurState.DELIVERING_PRIME_MATERIALS, this.numberSupplies, this.nPrimeMat,vec);
    }

    /**
     * Verify if all the Craftsmen finished their products.
     * @return true, if craftsmen finished their products, false, otherwise
     */
    public boolean noCraftWorking()
    {
        for (boolean c : this.isWorking) {
            if (c) {
                return false;
            }
        }
        return true;
    }
}
