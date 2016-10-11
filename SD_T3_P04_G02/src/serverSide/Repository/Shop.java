package serverSide.Repository;

import comInf.States.InitialState;
import comInf.States.ShopState;
import comInf.Queue;

/**
 * Shop current Information.
 * @author andrealves
 */
public class Shop {

    /**
     * Shop current internal state.
     * @serialField sStat
     */
    private ShopState sStat;

    /**
     * Number of customers in the shop.
     * @serialField NCI
     */
    private int NCI;

    /**
     * Number of products in the shop.
     * @serialField NPI_S
     */
    private int NPI_S;

    /**
     * Products Collecting request.
     * @serialField PCR
     */
    private Boolean PCR;

    /**
     * Prime Materials Delivery request.
     * @serialField PMR
     */
    private Boolean PMR;

    /**
     * Queue of the customers that want to buy products.
     *   @serialField queue
     */
    private Queue queue;
    
    /**
     * Instantiation of the Shop.
     */
    public Shop()
    {
        this.NCI = 0;
        this.NPI_S = 0;
        this.PCR = false;
        this.PMR = false;
        this.queue = new Queue(InitialState.getNumberOfClients());
        this.sStat = ShopState.CLOSED;
    }

    /**
     * Get shop current state.
     * @return sStat Shop current state
     */
    public ShopState GetsStat() {
        return this.sStat;
    }

    /**
     * Get shop current number of customers inside.
     * @return NCI number of customers
     */
    public int GetsNCI() {
        return this.NCI;
    }

    /**
     * Get Shop current number of products inside.
     * @return NPI_S number of products
     */
    public int GetNPI_S() {
        return this.NPI_S;
    }

    /**
     * Get Products Collecting request state.
     * @return PCR request value
     */
    public Boolean GetPCR() {
        return this.PCR;
    }

    /**
     * Get Products Collecting request state.
     * @return PCR request value
     */
    public String getPCR(){
        if(this.PCR){
            return "T";
        }
        else{
            return "F";
        }
    }
    
    /**
     * Get Prime Materials Request State.
     * @return PMR request value
     */
    public Boolean GetPMR() {
        return this.PMR;
    }
    
    /**
     * Get Prime Materials Request State.
     * @return PMR request value
     */
    public String getPMR(){
        if(this.PMR){
            return "T";
        }
        else{
            return "F";
        }
    }
    
    /**
     * Get Customers Queue.
     * @return queue Customers
     */
    public Queue Getqueue() {
        return this.queue;
    }

    /**
     * Update shop current state.
     * @param sStat with the new state
     */
    public void SetsStat(ShopState sStat) {
        this.sStat = sStat;
    }

    /**
     * Update shop current number of clients inside.
     * @param NCI new number of clients inside
     */
    public void SetsNCI(int NCI) {
        this.NCI = NCI;
    }

    /**
     * Update shop current number of products inside.
     * @param NPI_S new number of products inside
     */
    public void SetNPI_S(int NPI_S) {
        this.NPI_S = NPI_S;
    }

    /**
     * Update shop current Products collecting request state.
     * @param PCR boolean with new request state
     */
    public void SetPCR(Boolean PCR) {
        this.PCR = PCR;
    }

    /**
     * Update shop current Prime Materials delivering request state.
     * @param PMR new state
     */
    public void SetPMR(Boolean PMR) {
        this.PMR = PMR;
    }

    /**
     * Update customers Queue.
     * @param queue set up the queue
     */
    public void Setqueue(Queue queue) {
        this.queue = queue;
    }
}
