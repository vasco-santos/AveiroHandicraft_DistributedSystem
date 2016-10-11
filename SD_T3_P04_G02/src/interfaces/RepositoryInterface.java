package interfaces;

import comInf.States.CraftsmanState;
import comInf.States.CustomerState;
import comInf.States.EntrepreneurState;
import comInf.States.ShopState;
import comInf.Tuple;
import comInf.VectorialClock;
import java.rmi.Remote;
import java.rmi.RemoteException;
import serverSide.Repository.Craftsman;
import serverSide.Repository.Customer;
import serverSide.Repository.WorkShop;

/**
 *   This data type describes the interaction with the Repository, as a remote object, which is a service
 * in the Aveiro Handicraft Problem that implements the type 2 of the client-server model.
 * @author vsantos
 */
public interface RepositoryInterface extends Remote
{
    // Craftsman access Repository
    /**
    *  End operation craftsman. Check if the life cycle is over.
    * @param custID with id of craftsman.
    * @param vec Vectorial Clock.
    * @return true, if the life cycle continues
    *         false, if the end operations.
    * @throws java.rmi.RemoteException Unable to call remote object
    */    
    public Tuple endOperCraftsman(int custID, VectorialClock vec) throws RemoteException;
    
    // Customer access Repository
    /**
    *  End operation customer. Check if the life cycle is over.
    * @param custID with id of customer.
    * @param vec Vectorial Clock.
    * @return true, if the life cycle continues
    *         false, if the end operations
    * @throws java.rmi.RemoteException Unable to call remote object
    */      
    public Tuple endOperCustomer(int custID, VectorialClock vec) throws RemoteException;
    
    // Entrepreneur access Repository
    /**
    *  End operation entrepreneur. Check if the life cycle is over.
    * @param vec Vectorial Clock.
    * @return true, if the life cycle continues
    *         false, if the end operations
     * @throws java.rmi.RemoteException Unable to call remote object
    */  
    public Tuple endOperEntrep(VectorialClock vec) throws RemoteException;
    
    /**
     * Get current Shop Internal State.
     * @return shopS Shop Internal State
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public ShopState GetShopState()throws RemoteException;

    /**
     * Get current Workshop Internal State.
     * @return workshopS Workshop Internal State
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public WorkShop GetWorkState()throws RemoteException;

    /**
     * Get current Entrepreneur State.
     * @return eStat Entrepreneur State
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public EntrepreneurState GetEntrepreneur() throws RemoteException;
    
    /**
     * Get current Customer State.
     * @param custId identifier of customer on array
     * @return CustomerState Customer State
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public CustomerState GetCustomerState(int custId) throws RemoteException;
    /**
     * Get Customer Information.
     * @param custId identifier of customer on array
     * @return Customer Customer Internal Information
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public Customer GetCustomer(int custId) throws RemoteException;
    /**
     * Get current Craftsman state.
     * @param craftId identifier of craftsman on array
     * @return CraftsmanState Craftsman State
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public CraftsmanState GetCraftsmanState(int craftId) throws RemoteException;
    /**
     * Get current Craftsman information.
     * @param craftId identifier of craftsman on array
     * @return Craftsman Craftsman Internal Information
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public  Craftsman GetCraftsman(int craftId) throws RemoteException;

    /**
     * Get current prime materials available.
     * @return primeMaterialsS Prime Materials in Prime Storage
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public int GetprimeMaterialsS() throws RemoteException;

    /**
     * Update current Shop State.
     * @param shopS new shop status 
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public  void SetShopState(ShopState shopS) throws RemoteException;

    /**
     * Update current Workshop state.
     * @param workshopS new work shop status
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void SetWorkState(WorkShop workshopS) throws RemoteException;

    /**
     * Update current Entrepreneur state.
     * @param eStat new entrepreneur status
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void SetEntrepreneur(EntrepreneurState eStat) throws RemoteException;

    /**
     * Update current Customer State.
     * @param custStat new customer state
     * @param custId customer identifier on array
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void SetCustomerState(CustomerState custStat, int custId) throws RemoteException;

    /**
     * Update current Craftsman State.
     * @param craftStat new craftsman state
     * @param craftId craftsman identifier on array
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public  void SetCraftsmanState(CraftsmanState craftStat, int craftId) throws RemoteException;

    /**
     * Update number of prime materials available.
     * @param primeMaterialsS new number of prime materials available
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void SetprimeMaterialsS(int primeMaterialsS) throws RemoteException;

    /**
     * Update current Craftsman State.
     * @param craftStat new craftsman state
     * @param craftId craftsman identifier on array
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void changeCraftsmanState(CraftsmanState craftStat, int craftId,VectorialClock vec) throws RemoteException;

    /**
     * Update current Customer State.
     * @param custStat new customer state
     * @param custId customer identifier on array
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void changeCustomerState(CustomerState custStat, int custId,VectorialClock vec) throws RemoteException;

    /**
     * Update current Entrepreneur state.
     * @param eStat new entrepreneur status
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void changeEntrepreneurState(EntrepreneurState eStat,VectorialClock vec) throws RemoteException;

    /**
     * Update number of prime materials available.
     * @param craftStat new actual status of craftsman
     * @param craftId index of craftsman on array
     * @param primeMaterials_w new number of prime materials available
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void changeCraftsmanStateAndPrimeMaterials(CraftsmanState craftStat, int craftId, int primeMaterials_w,VectorialClock vec) throws RemoteException;

    /**
     * Update current Shop State.
     * @param shopS new shop status
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void changeShopState(ShopState shopS,VectorialClock vec) throws RemoteException;

    /**
     * Update current Entrepreneur state.
     * @param eStat new shop status
     * @param shopS new entrepreneur status
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void changeEntrepreneurStateAndShopState(ShopState shopS, EntrepreneurState eStat,VectorialClock vec) throws RemoteException;

    /**
     * Change npi_s variable.
     * @param npi_s number of products in
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void changeNPI_S(int npi_s,VectorialClock vec) throws RemoteException;

    /**
     * Change customer current state and number of clients inside.
     * @param custStat new customer state
     * @param custId index of customer on array
     * @param nci number of clients in
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void changeCustomerStateAndCustInside(CustomerState custStat, int custId, int nci,VectorialClock vec) throws RemoteException;

    /**
     * Change customer state and number of bought products.
     * @param custStat new customer state
     * @param custId index of customer in array
     * @param bp new number of products bought
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void changeCustomerStateAndBoughtProducts(CustomerState custStat, int custId, int bp,VectorialClock vec)throws RemoteException;

    /**
     * Change craftsman state on repository and PMR flag.
     * @param craftStat new craftsman state
     * @param craftId index of craftsman
     * @param pmr new value
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void changeCraftsmanStateAndPMR(CraftsmanState craftStat, int craftId, boolean pmr,VectorialClock vec)throws RemoteException;

    /**
     * New craftsman state and collection products flag value on shop.
     * @param craftStat new craftsman state
     * @param craftId index of craftsman on array
     * @param pcr new flag value
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void changeCraftsmanStateAndPCR(CraftsmanState craftStat, int craftId, boolean pcr,VectorialClock vec)throws RemoteException;

    /**
     * Change entrepreneur state and PMR flag on repository.
     * @param eStat new entrepreneur state
     * @param pmr PMR flag
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void changeEntrepreneurStateAndPMR(EntrepreneurState eStat, boolean pmr,VectorialClock vec)throws RemoteException;
    /**
     * Change entrepreneur state and prime materials.
     * @param eStat new entrepreneur state
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */    
    public void changeEntrepreneurStateAndPrimeMaterialsS(EntrepreneurState eStat,VectorialClock vec)throws RemoteException;

    /**
     * Change entrepreneur state and PCR flag on repository.
     * @param eStat new entrepreneur state
     * @param pcr PMR flag
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void changeEntrepreneurStateAndPCR(EntrepreneurState eStat, boolean pcr,VectorialClock vec)throws RemoteException;

    /**
     * Go to store changes Complex change of states enrolled on single action.
     * @param craftStat new craftsman state
     * @param craftId index of craftsman on array
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void goToStoreLog(CraftsmanState craftStat, int craftId,VectorialClock vec)throws RemoteException;

    /**
     * Change entrepreneur state, prime materials flag and number of supplies.
     * @param eStat New entrepreneur state
     * @param numberSupplies number of supplies
     * @param nPrimeMat number of prime materials
     * @param vec Vectorial Clock.
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void changeEntrepreneurStateAndPrimeMatAndNumberSupplies(EntrepreneurState eStat, int numberSupplies, int nPrimeMat,VectorialClock vec)throws RemoteException;

    /**
     * Change entrepreneur state and number of products.
     * @param eStat new entrepreneur state
     * @param nFinishedProd number of finished products
     * @param vec Vectorial Clock. 
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void changeEntrepreneurStateAndNPI_W(EntrepreneurState eStat, int nFinishedProd,VectorialClock vec)throws RemoteException;

    /**
     * Change log file name.
     * @param name log file name 
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void setLogFileName(String name)throws RemoteException;

    /**
     * Get current flag value.
     * @return true, if flag is on
     *         false, if not
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public boolean getPMR()throws RemoteException;

    /**
     * Ask Server for Shutdown.
     * The client says that his work is done. Server verify if it can shutdown
     * @throws java.rmi.RemoteException Unable to call remote object
     */
    public void shutDown() throws RemoteException;

}
