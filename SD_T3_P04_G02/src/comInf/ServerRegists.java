package comInf;

/**
 * Server registry Data Type.
 * @author vsantos
 */
public class ServerRegists
{
    /**
     * Hostname of the system where the register service is located.
     * @serialField rmiRegHostName
     */
    private final String rmiRegHostName;
    
    /**
     * Listening port.
     * @serialField rmiRegPortNumb
     */
    private final int rmiRegPortNumb;
    
    /**
     * RMI Registry entry name.
     * @serialField registEntry
     */
    private final String registEntry;
    
    /**
     * Server Registry Data Type.
     * @param rmiRegHostName hostname.
     * @param rmiRegPortNumb port number.
     * @param registEntry registry entry name.
     */
    public ServerRegists(String rmiRegHostName, int rmiRegPortNumb, String registEntry)
    {
        this.rmiRegHostName = rmiRegHostName;
        this.rmiRegPortNumb = rmiRegPortNumb;
        this.registEntry = registEntry;
    }

    /**
     * Get Registry hostname.
     * @return rmiRegHostName hostname.
     */
    public String getRmiRegHostName()
    {
        return rmiRegHostName;
    }

    /**
     * Get Registry Port.
     * @return rmiRegPortNumb port number.
     */
    public int getRmiRegPortNumb()
    {
        return rmiRegPortNumb;
    }

    /**
     * Get Registry entry name.
     * @return registEntry entry name.
     */
    public String getRegistEntry()
    {
        return registEntry;
    }
}
