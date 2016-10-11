package serverSide.Repository;

/**
 * workshop current information.
 * @author andrealves
 */
public class WorkShop {

    /**
     * Number of prime materials in the workshop.
     * @serialField APMI
     */
    private int APMI;

    /**
     * Number of finished products in the store room.
     * @serialField NPI_W
     */
    private int NPI_W;

    /**
     * Number of times that prime materials have been supplied.
     * @serialField NSPM
     */
    private int NSPM;

    /**
     * Total number of prime materials supplied.
     * @serialField TAPM
     */
    private int TAPM;

    /**
     * Total number of products produced.
     * @serialField TNP
     */
    private int TNP;
    
    /**
     * Instantiation of the Workshop.
     */
    public WorkShop()
    {
        this.APMI = 0;
        this.NPI_W = 0;
        this.NSPM = 0;
        this.TAPM = 0;
        this.TNP = 0;
    }

    /**
     * Get current number of prime materials available in the workshop.
     * @return APMI prime materials number
     */
    public int GetAPMI() {
        return this.APMI;
    }

    /**
     * Get current number of products inside store room.
     * @return NPI_W products number
     */
    public int GetNPI_W() {
        return this.NPI_W;
    }

    /**
     * Get current number of prime materials supplies.
     * @return NSPM number of supplies
     */
    public int GetNSPM() {
        return this.NSPM;
    }

    /**
     * Get total number of prime materials supplied.
     * @return TAPM total number
     */
    public int GetTAPM() {
        return this.TAPM;
    }

    /**
     * Get total number of products produced.
     * @return TNP total number
     */
    public int GetTNP() {
        return this.TNP;
    }

    /**
     * Update current number of prime materials in the workshop.
     * @param APMI number of prime materials
     */
    public synchronized void SetAPMI(int APMI) {
        this.APMI = APMI;
    }

    /**
     * Update current number of products in the store room.
     * @param NPI_W new number of products in the store
     */
    public synchronized void SetNPI_W(int NPI_W) {
        this.NPI_W = NPI_W;
    }

    /**
     * Update current number of prime materials supplies.
     * @param NSPM new number of prime materials supplies
     */
    public synchronized void SetNSPM(int NSPM) {
        this.NSPM = NSPM;
    }

    /**
     * Update total number of prime materials supplied.
     * @param TAPM new total of prime materials
     */
    public synchronized void SetTAPM(int TAPM) {
        this.TAPM = TAPM;
    }

    /**
     * Update total number of products produced.
     * @param TNP new total number of products produced
     */
    public synchronized void SetTNP(int TNP) {
        this.TNP = TNP;
    }
}
