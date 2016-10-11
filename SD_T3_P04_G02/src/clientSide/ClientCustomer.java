package clientSide;

import comInf.ServerRegists;
import clientSide.Entities.Customer;
import comInf.RemoteObjects;
import comInf.States.InitialState;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import genclass.GenericIO;
import interfaces.RepositoryInterface;
import interfaces.ShopInterface;

/**
 * This data type generate the Client Customer from the Aveiro Handicraft Problem  (access to shared
 * regions with remote object).
 * Solution of the Aveiro Handicraft Problem that implements the type 2 client-server model
 * (server replication) with static launch of the threads.
 * @author vsantos
 */
public class ClientCustomer
{
    /**
    * Main Program - Customer.
    * @param args port and host numbers
    */
   public static void main (String [] args)
   {
       GenericIO.writelnString("      Problema artesanato aveiro - Customer \n");
       
       // Inputs for communication
        String regAddress;
        int regPort;
        if (args.length == 2)
        {
            regAddress = args[0];
            regPort = Integer.parseInt(args[1]);
        }
        else
        {
            regAddress = "localhost";
            regPort = 2222;
        }
       
        // Communications - Locate by remote object name the registed services
        RepositoryInterface repoInter = null;       // Repository Interface (Remote Object)
        ShopInterface shopInter = null;             // Shop Interface (Remote Object)
        
        ServerRegists repoRegist = new ServerRegists(regAddress, regPort, RemoteObjects.repo);
        
        Registry registry;
        try
        {
            registry = LocateRegistry.getRegistry(repoRegist.getRmiRegHostName(), repoRegist.getRmiRegPortNumb());
            repoInter = (RepositoryInterface) registry.lookup(repoRegist.getRegistEntry());
            shopInter = (ShopInterface) registry.lookup(RemoteObjects.shop);
        }
        catch (RemoteException e)
        {
            GenericIO.writelnString ("Exception in the location of the server: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { GenericIO.writelnString ("The server is not registed: " + e.getMessage () + "!");
          e.printStackTrace ();
          System.exit (1);
        }
        
        // Customer Thread Initialization
        Customer customer[] = new Customer[InitialState.getNumberOfClients()];
        for (int j = 0; j < InitialState.getNumberOfClients(); j++) {
            customer[j] = new Customer(j, repoInter, shopInter);
        }
        
        // Threads Start
        for (Customer c : customer) {
            c.start();
        }
        
        // Wait for the Simulation end
        for (Customer co : customer) {
            try {
                co.join();
            } catch (InterruptedException ex) {
            }
        }
        
        // Informs servers that this client ended its operations 
        try
        {
            shopInter.shutDown();
            repoInter.shutDown();
        }
        catch (RemoteException e)
        {
            GenericIO.writelnString ("Exception invoking the remote method setLogFileName: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }
   }
}
