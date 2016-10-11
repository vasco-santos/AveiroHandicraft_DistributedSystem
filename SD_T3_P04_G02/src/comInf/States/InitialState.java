package comInf.States;

import java.io.Serializable;

/**
 * Initial State of the Simulation.
 * @author vsantos
 */
public class InitialState implements Serializable{
    
    private static final int numberOfClients = 3;
    private static final int numberOfProductsToTransfer = 4;
    private static final int numberOfSuppliedPrimes = 8;
    private static final int numberOfCraftmen = 3;
    private static final int numberOfPrimeMaterialsAlert = 6;
    private static final int numberOfPrimeMaterialsPerProduct = 5;
    private static final int totalNumberOfPrimeMaterials = 80;
    private static final int numberOfDeleveries = 10;
    private static final int numberOfEntities = 7;
    private static final int localIndexEntrepreneur = 0;
    private static final int localIndexBaseCraftsman = 1;
    private static final int localIndexBaseCustomer = 4;
    /**
    *  Serialization key.
    *    @serialField serialVersionUID
    */
    private static final long serialVersionUID = 1005L;

    /**
     * Get local index of this entity
     * @return localIndexEntrepreneur Local index of this entitie
     */
    public static int getLocalIndexEntrepreneur() {
        return localIndexEntrepreneur;
    }
        /**
     * Get local index of this entity
     * @return localIndexBaseCraftsman Local index of this entitie
     */
    public static int getLocalIndexBaseCraftsman() {
        return localIndexBaseCraftsman;
    }
        /**
     * Get local index of this entity
     * @return localIndexBaseCustomer Local index of this entitie
     */
    public static int getLocalIndexBaseCustomer() {
        return localIndexBaseCustomer;
    }
    /**
     * Get Number Of Clients Operation.
     * @return numberOfClients Number of Clients
     */
    public static int getNumberOfClients() {
        return numberOfClients;
    }
     /**
     * Get Number Of Entities Operation.
     * @return numberOfClients Number of Entities
     */
    public static int getNumberOfEntities() {
        return numberOfEntities;
    }
    
    /**
     * Get Number of Products to Transfer Operation.
     * @return numberOfProductsToTransfer Number of Products to transfer
     */
    public static int getNumberOfProductsToTransfer() {
        return numberOfProductsToTransfer;
    }
    
    /**
     * Get Number of Supplied Primes Operation.
     * @return numberOfSupliedPrimes Number of Supplied Primes
     */
    public static int getNumberOfSuppliedPrimes() {
        return numberOfSuppliedPrimes;
    }
    
    /**
     * Get Number of Craftsmen operation.
     * @return numberOfCraftmen Number of Craftsmen
     */
    public static int getNumberOfCraftmen() {
        return numberOfCraftmen;
    }

    /**
     * Get Number Of Prime Materials to alert the entrepreneur.
     * @return numberOfPrimeMaterialsAlert Number of Prime Materials threshold
     */
    public static int getNumberOfPrimeMaterialsAlert() {
        return numberOfPrimeMaterialsAlert;
    }

    /**
     * Get Number of Prime Materials to build a product.
     * @return numberOfPrimeMaterialsPerProduct Number of Prime Materials per Product
     */
    public static int getNumberOfPrimeMaterialsPerProduct() {
        return numberOfPrimeMaterialsPerProduct;
    }

    /**
     * Get total number of prime materials stored in the Prime Storage.
     * @return totalNumberOfPrimeMaterials Number of total Prime Materials.
     */
    public static int getTotalNumberOfPrimeMaterials() {
        return totalNumberOfPrimeMaterials;
    }
    
    /**
     * Get number of deliveries of the entrepreneur to replenish the workshop stock.
     * @return numberOfDeleveries Total number of deliveries.
     */
    public static int getNumberOfDeleveries() {
        return numberOfDeleveries;
    }
}
