package serverSide.Repository;

import comInf.States.CraftsmanState;

/**
 *  Craftsman current information.
 * @author andrealves
 */
public class Craftsman {

    /**
     * Craftsman current internal state.
     * @serialField stat
     */
    private CraftsmanState stat;
    
    /**
     * Amount of products produced by the Craftsman.
     * @serialField prodProducts
     */
    private int prodProducts;
    
    /**
     * Craftsman Instantiation.
     */
    public Craftsman()
    {
        this.stat = CraftsmanState.FETCHING_PRIME_MATERIALS;
        this.prodProducts = 0;
    }

    /**
     * Get Craftsman current state.
     * @return stat CraftsmanState
     */
    public CraftsmanState getCraftsmanState() {
        return this.stat;
    }

    /**
     * Get Craftsman current produced products.
     * @return prodProducts Produced Products
     */
    public int getprodProducts() {
        return this.prodProducts;
    }

    /**
     * Update Craftsman state.
     * @param stat new craftsman status
     */
    public void setCraftsmanState(CraftsmanState stat) {
        this.stat = stat;
    }

    /**
     * Update Craftsman number of produced products.
     * @param prodProducts number of produced products
     */
    public void setProdProducts(int prodProducts) {
        this.prodProducts = prodProducts;
    }
}
