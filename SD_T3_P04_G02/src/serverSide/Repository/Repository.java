package serverSide.Repository;

import comInf.States.CraftsmanState;
import comInf.States.CustomerState;
import comInf.States.EntrepreneurState;
import comInf.States.InitialState;
import comInf.States.ShopState;
import comInf.Tuple;
import comInf.VectorialClock;
import genclass.GenericIO;
import genclass.TextFile;
import interfaces.RepositoryInterface;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Handles all the information of the Entities.
 *
 * @author vsantos
 */
public class Repository implements RepositoryInterface {
    
    /**
     * Vectorial clock of repository.
     * @serialField vetorclock
     */
    private VectorialClock vectorClock;
   
    /**
     * Name of the log file name.
     *
     * @serialField logFileName
     */
    private String logFileName;

    /**
     * Current Internal State of the Shop.
     *
     * @serialField shopS
     */
    private final Shop shopS;

    /**
     * Current Internal State of the Workshop.
     *
     * @serialField workshopS
     */
    private WorkShop workshopS;

    /**
     * Entrepreneur current State.
     *
     * @serialField eStat
     */
    private EntrepreneurState eStat;

    /**
     * Customers current state.
     *
     * @serialField custStat
     */
    private final Customer[] custStat;

    /**
     * Craftsman current state.
     *
     * @serialField craftStat
     */
    private final Craftsman[] craftStat;

    /**
     * Current number of prime materials available.
     *
     * @serialField primeMaterialsS
     */
    private int primeMaterialsS;
    
    /**
     * Array with all log occurences.
     *  @serialField logArray
     */
    private final ArrayList<String[]> logArray;

    /**
     * Repository Monitor Instantiation.
     */
    public Repository() {
        // Initialize customers state
        this.custStat = new Customer[InitialState.getNumberOfClients()];
        for (int i = 0; i < InitialState.getNumberOfClients(); i++) {
            custStat[i] = new Customer();

        }
        // Initialize craftsmen state
        this.craftStat = new Craftsman[InitialState.getNumberOfCraftmen()];
        for (int i = 0; i < InitialState.getNumberOfCraftmen(); i++) {
            craftStat[i] = new Craftsman();
        }
        // Initialize shop state
        this.shopS = new Shop();
        // Initialize workshop state
        this.workshopS = new WorkShop();
        //File name set
        this.logFileName = "log";
        setLog();
        vectorClock = new VectorialClock(InitialState.getNumberOfEntities(),6);
        logArray = new ArrayList();
    }
    
    /**
     * Create log File.
     */
    public void setLog()
    {
        // Report initial State
        String header = ("\n               Aveiro Handicraft SARL - Description of the internal state\n"
                + "\n"
                + "ENTREPRE  CUST_0  CUST_1 CUST_2   CRAFT_0 CRAFT_1 CRAFT_2          SHOP                 WORKSHOP                VEC CLOCK    \n"
                + "  Stat   Stat BP Stat BP Stat BP  Stat PP Stat PP Stat PP Stat NCI NPI PCR PMR  APMI NPI NSPM TAPM TNP    0   1   2   3   4   5   6 \n");
        System.out.print(header);
        
        TextFile log = new TextFile();
        if (!log.openForWriting(".",this.logFileName)) {
            GenericIO.writelnString("A operação de criação do ficheiro log falhou!");
            System.exit(1);
        }

        log.writelnString(header);
        if (!log.close()) {
            GenericIO.writelnString("A operação de fecho do ficheiro log falhou!");
            System.exit(1);
        }
    }

    /**
     * Get current Shop
     * @return shopS Shop
     */
    public synchronized Shop GetShop()
    {
        return this.shopS;
    }

    /**
     * Get current Shop Internal State.
     * @return shopS Shop Internal State
     */
    @Override
    public synchronized ShopState GetShopState()
    {
        return this.shopS.GetsStat();
    }

    /**
     * Get current Workshop Internal State.
     * @return workshopS Workshop Internal State
     */
    @Override
    public synchronized WorkShop GetWorkState()
    {
        return this.workshopS;
    }

    /**
     * Get current Entrepreneur State.
     * @return eStat Entrepreneur State
     */
    @Override
    public synchronized EntrepreneurState GetEntrepreneur()
    {
        return this.eStat;
    }

    /**
     * Get current Customer State.
     * @param custId identifier of customer on array
     * @return CustomerState Customer State
     */
    @Override
    public synchronized CustomerState GetCustomerState(int custId)
    {
        return this.custStat[custId].getCustomerState();
    }

    /**
     * Get Customer Information.
     * @param custId identifier of customer on array
     * @return Customer Customer Internal Information
     */
    @Override
    public synchronized Customer GetCustomer(int custId)
    {
        return this.custStat[custId];
    }

    /**
     * Get current Craftsman state.
     * @param craftId identifier of craftsman on array
     * @return CraftsmanState Craftsman State
     */
    @Override
    public synchronized CraftsmanState GetCraftsmanState(int craftId)
    {
        return this.craftStat[craftId].getCraftsmanState();
    }

    /**
     * Get current Craftsman information.
     * @param craftId identifier of craftsman on array
     * @return Craftsman Craftsman Internal Information
     */
    @Override
    public synchronized Craftsman GetCraftsman(int craftId)
    {
        return this.craftStat[craftId];
    }

    /**
     * Get current prime materials available.
     * @return primeMaterialsS Prime Materials in Prime Storage
     */
    @Override
    public synchronized int GetprimeMaterialsS()
    {
        return this.primeMaterialsS;
    }

    /**
     * Update current Shop State.
     * @param shopS new shop status
     */
    @Override
    public synchronized void SetShopState(ShopState shopS)
    {
        this.shopS.SetsStat(shopS);
    }

    /**
     * Update current Workshop state.
     * @param workshopS new work shop status
     */
    @Override
    public synchronized void SetWorkState(WorkShop workshopS)
    {
        this.workshopS = workshopS;
    }

    /**
     * Update current Entrepreneur state.
     * @param eStat new entrepreneur status
     */
    @Override
    public synchronized void SetEntrepreneur(EntrepreneurState eStat)
    {
        this.eStat = eStat;
    }

    /**
     * Update current Customer State.
     * @param custStat new customer state
     * @param custId customer identifier on array
     */
    @Override
    public synchronized void SetCustomerState(CustomerState custStat, int custId)
    {
        this.custStat[custId].setCustomerState(custStat);
    }

    /**
     * Update current Craftsman State.
     * @param craftStat new craftsman state
     * @param craftId craftsman identifier on array
     */
    @Override
    public synchronized void SetCraftsmanState(CraftsmanState craftStat, int craftId)
    {
        this.craftStat[craftId].setCraftsmanState(craftStat);
    }

    /**
     * Update number of prime materials available.
     * @param primeMaterialsS new number of prime materials available
     */
    @Override
    public synchronized void SetprimeMaterialsS(int primeMaterialsS)
    {
        this.primeMaterialsS = primeMaterialsS;
    }

    /**
     * Log new situation.
     * @param v
     */
    public void log(VectorialClock v) {
        int [] clock = v.getIntegerArray();  
        String logLine = String.format("  %4s   %4s %2s %4s %2s %4s %2s  %4s %2s %4s %2s %4s %2s %4s  %2s  %2s  %1s    %1s    %2s  %2s   %2s   %2s   %2s  %2d  %2d  %2d  %2d  %2d  %2d  %2d\n",
                this.eStat.getState(), this.custStat[0].getCustomerState().getState(), this.custStat[0].getBoughtProducts(), this.custStat[1].getCustomerState().getState(),
                this.custStat[1].getBoughtProducts(), this.custStat[2].getCustomerState().getState(), this.custStat[2].getBoughtProducts(),
                this.craftStat[0].getCraftsmanState().getState(), this.craftStat[0].getprodProducts(),
                this.craftStat[1].getCraftsmanState().getState(), this.craftStat[1].getprodProducts(), this.craftStat[2].getCraftsmanState().getState(), this.craftStat[2].getprodProducts(),
                this.shopS.GetsStat().getState(), this.shopS.GetsNCI(), this.shopS.GetNPI_S(), this.shopS.getPCR(), this.shopS.getPMR(), this.workshopS.GetAPMI(),
                this.workshopS.GetNPI_W(), this.workshopS.GetNSPM(), this.workshopS.GetTAPM(), this.workshopS.GetTNP(),clock[0],clock[1],clock[2],clock[3],clock[4],clock[5],clock[6]);
        System.out.print(logLine);
        String logWords[] = logLine.trim().split(" +");
        logArray.add(logWords);
        reportStatus(logLine);
        System.out.flush();
    }
    
    /**
     * Sort Log array.
     */
    public void sortLog()
    {
        String result = "";
        String arr[];
        String[][] array = new String[logArray.size()][30];
        for(int i = 0; i < logArray.size(); i++){
            arr = logArray.get(i);
            for(int j = 0;j < arr.length;j++){
                array[i][j] = arr[j];
            }
        }
        
        Arrays.sort(array, new Comparator<String[]>() {
            @Override
            public int compare(String[] p1, String[] p2) {
                boolean firstBiggerThanSecond = false;
                boolean secondBiggerThanFirst = false;
                for (int i = p1.length - 7; i < p1.length; i++) {
                    if (Integer.parseInt(p1[i]) < Integer.parseInt(p2[i])) {
                        secondBiggerThanFirst = true;
                    }
                    if (Integer.parseInt(p1[i]) > Integer.parseInt(p2[i])) {
                        firstBiggerThanSecond = true;
                    }
                }
                if(secondBiggerThanFirst & firstBiggerThanSecond){
                    return 0;
                }
                if(secondBiggerThanFirst & !firstBiggerThanSecond){
                    return -1;
                }  
                return 0;
            }
        });
        
        result += ("\n               Aveiro Handicraft SARL - Description of the internal state\n"
                + "\n"
                + "ENTREPRE  CUST_0  CUST_1 CUST_2   CRAFT_0 CRAFT_1 CRAFT_2          SHOP                 WORKSHOP                VEC CLOCK    \n"
                + "  Stat   Stat BP Stat BP Stat BP  Stat PP Stat PP Stat PP Stat NCI NPI PCR PMR  APMI NPI NSPM TAPM TNP    0   1   2   3   4   5   6 \n");
        
        for (int i = 0; i < array.length; i++)
        {
            String resTmp = String.format("  %4s   %4s %2s %4s %2s %4s %2s  %4s %2s %4s %2s %4s %2s %4s  %2s  %2s  %1s    %1s    %2s  %2s   %2s   %2s   %2s  %2s  %2s  %2s  %2s  %2s  %2s  %2s\n",
                    array[i][0],array[i][1],array[i][2],array[i][3],array[i][4],array[i][5],array[i][6],array[i][7],array[i][8],array[i][9],array[i][10],
                    array[i][11],array[i][12],array[i][13],array[i][14],array[i][15],array[i][16],array[i][17],array[i][18],array[i][19],array[i][20],array[i][21],
                    array[i][22],array[i][23],array[i][24],array[i][25],array[i][26],array[i][27],array[i][28],array[i][29]);
            result += resTmp;        
        }
        
        TextFile log = new TextFile();                      // instanciação de uma variável de tipo ficheiro de texto
        if (!log.openForWriting(".", this.logFileName+"Sorted")) {
            GenericIO.writelnString("A operação de criação do ficheiro log falhou!");
            System.exit(1);
        }
        log.writelnString(result);
        if (!log.close()) {
            GenericIO.writelnString("A operação de fecho do ficheiro log falhou!");
            System.exit(1);
        }
    }

    /**
     * Update Log State.
     * @param logLine
     */
    private void reportStatus(String logLine)
    {
        TextFile log = new TextFile();                      // instanciação de uma variável de tipo ficheiro de texto

        if (!log.openForAppending(".", this.logFileName)) {
            GenericIO.writelnString("A operação de criação do ficheiro log falhou!");
            System.exit(1);
        }
        log.writelnString(logLine);
        if (!log.close()) {
            GenericIO.writelnString("A operação de fecho do ficheiro log falhou!");
            System.exit(1);
        }
    }

    /**
     * End of operations for the customer. Verify if the life cycle of the
     * customer is over. The customer life cycle is over if all prime materials
     * have been transformed into products and there are no more products to be
     * sold.
     * @return true, if the life cycle of the customer is over false, otherwise
     */
    @Override
    public synchronized Tuple endOperCustomer(int custID,VectorialClock vec) throws RemoteException
    {
        vectorClock.update(vec);
        boolean res = GetShop().GetNPI_S() == 0
                && GetShop().GetsStat() == ShopState.OPEN
                && GetWorkState().GetNSPM() == InitialState.getNumberOfDeleveries()
                && GetWorkState().GetAPMI() == 0
                && GetWorkState().GetNPI_W() == 0;
        Tuple t = new Tuple(vec.getCopy(),res);
        return t;
    }

    /**
     * End of operation to the craftsman. Verify if the life cycle of the
     * craftsman is over The craftsman life cycle is over if the entrepreneur
     * has delivered all the supplies and there are no more prime materials.
     *
     * @return true if the craftsman life cycle is over false, otherwise
     */
    @Override
    public synchronized Tuple endOperCraftsman(int custID,VectorialClock vec) throws RemoteException
    {
        vectorClock.update(vec);
        boolean res = ((GetWorkState().GetNPI_W() == 0 || GetShop().GetPCR())
                && GetWorkState().GetAPMI() == 0
                && GetWorkState().GetNSPM() == InitialState.getNumberOfDeleveries());
        Tuple t = new Tuple(vec.getCopy(),res);
        return t;
    }

    /**
     * End of operations for the entrepreneur. Verify if the life cycle of the
     * entrepreneur is over. The entrepreneur life cycle is over if all prime
     * materials have been converted into products and if all products have been
     * sold and there are no requests of service and the shop is empty.
     *
     * @return true, if the life cycle of the entrepreneur is over false,
     * otherwise
     */
    @Override
    public synchronized Tuple endOperEntrep(VectorialClock vec) throws RemoteException
    {
        vectorClock.update(vec);
        boolean res = (GetShop().GetNPI_S() == 0
                && GetWorkState().GetNSPM() == InitialState.getNumberOfDeleveries()
                && GetWorkState().GetNPI_W() == 0
                && GetWorkState().GetAPMI() == 0
                && GetShop().GetsNCI() == 0);
        Tuple t = new Tuple(vec.getCopy(),res);
        return t;
    }

    /**
     * Update current Craftsman State.
     * @param craftStat new craftsman state
     * @param craftId craftsman identifier on array
     */
    @Override
    public synchronized void changeCraftsmanState(CraftsmanState craftStat, int craftId, VectorialClock vec)
    {
        this.craftStat[craftId].setCraftsmanState(craftStat);
        log(vec);
    }

    /**
     * Update current Customer State.
     * @param custStat new customer state
     * @param custId customer identifier on array
     */
    @Override
    public synchronized void changeCustomerState(CustomerState custStat, int custId,VectorialClock vec)
    {
        this.custStat[custId].setCustomerState(custStat);
        log(vec);
    }

    /**
     * Update current Entrepreneur state.
     * @param eStat new entrepreneur status
     */
    @Override
    public synchronized void changeEntrepreneurState(EntrepreneurState eStat,VectorialClock vec)
    {
        this.eStat = eStat;
        log(vec);
    }

    /**
     * Update number of prime materials available.
     * @param craftStat new actual status of craftsman
     * @param craftId index of craftsman on array
     * @param primeMaterials_w new number of prime materials available
     */
    @Override
    public synchronized void changeCraftsmanStateAndPrimeMaterials(CraftsmanState craftStat, int craftId, int primeMaterials_w,VectorialClock vec)
    {
        this.craftStat[craftId].setCraftsmanState(craftStat);
        this.GetWorkState().SetAPMI(primeMaterials_w);
        log(vec);
    }

    /**
     * Update current Shop State.
     * @param shopS new shop status
     */
    @Override
    public synchronized void changeShopState(ShopState shopS,VectorialClock vec)
    {
        this.shopS.SetsStat(shopS);
        log(vec);
    }

    /**
     * Update current Entrepreneur state.
     * @param eStat new shop status
     * @param shopS new entrepreneur status
     */
    @Override
    public synchronized void changeEntrepreneurStateAndShopState(ShopState shopS, EntrepreneurState eStat, VectorialClock vec)
    {
        this.shopS.SetsStat(shopS);
        this.eStat = eStat;
        log(vec);
    }

    /**
     * Change npi_s variable.
     * @param npi_s number of products in
     */
    @Override
    public synchronized void changeNPI_S(int npi_s,VectorialClock vec)
    {
        this.shopS.SetNPI_S(npi_s);
        log(vec);
    }

    /**
     * Change customer current state and number of clients inside.
     * @param custStat new customer state
     * @param custId index of customer on array
     * @param nci number of clients in
     */
    @Override
    public synchronized void changeCustomerStateAndCustInside(CustomerState custStat, int custId, int nci,VectorialClock vec)
    {
        this.custStat[custId].setCustomerState(custStat);
        this.shopS.SetsNCI(nci);
        log(vec);
    }

    /**
     * Change customer state and number of bought products.
     * @param custStat new customer state
     * @param custId index of customer in array
     * @param bp new number of products bought
     */
    @Override
    public synchronized void changeCustomerStateAndBoughtProducts(CustomerState custStat, int custId, int bp,VectorialClock vec)
    {
        this.custStat[custId].setCustomerState(custStat);
        this.custStat[custId].setBoughtProducts(this.custStat[custId].getBoughtProducts() + bp);
        log(vec);
    }

    /**
     * Change craftsman state on repository and PMR flag.
     * @param craftStat new craftsman state
     * @param craftId index of craftsman
     * @param pmr new value
     */
    @Override
    public synchronized void changeCraftsmanStateAndPMR(CraftsmanState craftStat, int craftId, boolean pmr,VectorialClock vec)
    {
        this.craftStat[craftId].setCraftsmanState(craftStat);
        this.shopS.SetPMR(pmr);
        log(vec);
    }

    /**
     * New craftsman state and collection products flag value on shop.
     * @param craftStat new craftsman state
     * @param craftId index of craftsman on array
     * @param pcr new flag value
     */
    @Override
    public synchronized void changeCraftsmanStateAndPCR(CraftsmanState craftStat, int craftId, boolean pcr,VectorialClock vec)
    {
        this.craftStat[craftId].setCraftsmanState(craftStat);
        this.shopS.SetPCR(pcr);
        log(vec);
    }

    /**
     * Change entrepreneur state and PMR flag on repository.
     * @param eStat new entrepreneur state
     * @param pmr PMR flag
     */
    @Override
    public synchronized void changeEntrepreneurStateAndPMR(EntrepreneurState eStat, boolean pmr,VectorialClock vec) 
    {
        this.eStat = eStat;
        this.shopS.SetPMR(pmr);
        log(vec);
    }

    /**
     * Change entrepreneur state and prime materials.
     * @param eStat new entrepreneur state
     */
    @Override
    public synchronized void changeEntrepreneurStateAndPrimeMaterialsS(EntrepreneurState eStat,VectorialClock vec)
    {
        this.eStat = eStat;
        this.primeMaterialsS = GetprimeMaterialsS() - InitialState.getNumberOfSuppliedPrimes();
        log(vec);
    }

    /**
     * Change entrepreneur state and PCR flag on repository.
     * @param eStat new entrepreneur state
     * @param pcr PMR flag
     */
    @Override
    public synchronized void changeEntrepreneurStateAndPCR(EntrepreneurState eStat, boolean pcr,VectorialClock vec)
    {
        this.eStat = eStat;
        this.shopS.SetPCR(pcr);
        log(vec);
    }

    /**
     * Go to store changes Complex change of states enrolled on single action.
     * @param craftStat new craftsman state
     * @param craftId index of craftsman on array
     */
    @Override
    public synchronized void goToStoreLog(CraftsmanState craftStat, int craftId,VectorialClock vec)
    {
        this.craftStat[craftId].setCraftsmanState(craftStat);
        GetCraftsman(craftId).setProdProducts(GetCraftsman(craftId).getprodProducts() + 1);
        GetWorkState().SetTNP(GetWorkState().GetTNP() + 1);
        GetWorkState().SetNPI_W(GetWorkState().GetNPI_W() + 1);
        log(vec);
    }

    /**
     * Change entrepreneur state, prime materials flag and number of supplies.
     * @param eStat New entrepreneur state
     * @param numberSupplies number of supplies
     * @param nPrimeMat number of prime materials
     */
    @Override
    public synchronized void changeEntrepreneurStateAndPrimeMatAndNumberSupplies(EntrepreneurState eStat, int numberSupplies, int nPrimeMat,VectorialClock vec)
    {
        this.eStat = eStat;
        GetWorkState().SetTAPM(this.GetWorkState().GetTAPM() + InitialState.getNumberOfSuppliedPrimes());
        GetWorkState().SetNSPM(numberSupplies);
        GetWorkState().SetAPMI(nPrimeMat);
        log(vec);
    }

    /**
     * Change entrepreneur state and number of products.
     * @param eStat new entrepreneur state
     * @param nFinishedProd number of finished products
     */
    @Override
    public synchronized void changeEntrepreneurStateAndNPI_W(EntrepreneurState eStat, int nFinishedProd,VectorialClock vec)
    {
        this.eStat = eStat;
        GetWorkState().SetNPI_W(nFinishedProd);
        log(vec);
    }

    /**
     * Change log file name.
     * @param name log file name
     */
    @Override
    public synchronized void setLogFileName(String name)
    {
        this.logFileName = name;
    }

    /**
     * Get current flag value.
     * @return true, if flag is on false, if not
     */
    @Override
    public synchronized boolean getPMR()
    {
        return GetShop().GetPMR();
    }

    /**
     * Ask Server for Shutdown.
     * The client says that his work is done. Server verify if it can shutdown
     */
    @Override
    public void shutDown() throws RemoteException
    {
        RepositoryServer.endRun();
    }

}
