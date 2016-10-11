package clientSide;

import comInf.ServerRegists;
import clientSide.Entities.Entrepreneur;
import comInf.RemoteObjects;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import genclass.GenericIO;
import interfaces.PrimeStorageInterface;
import interfaces.RepositoryInterface;
import interfaces.ShopInterface;
import interfaces.WorkshopInterface;

/**
 * This data type generate the Client Entrepreneur from the Aveiro Handicraft Problem  (access to shared
 * regions with remote object).
 * Solution of the Aveiro Handicraft Problem that implements the type 2 client-server model
 * (server replication) with static launch of the threads.
 * @author vsantos
 */
public class ClientEntrepreneur
{
    /**
    * Main Program - Entrepreneur.
    * @param args port and host numbers
    */
   public static void main (String [] args)
   {
        GenericIO.writelnString("      Problema artesanato aveiro - Entrepreneur \n");
       
        // Inputs for communication
        String regAddress, logName;
        int regPort;
        if (args.length == 3)
        {
            regAddress = args[0];
            regPort = Integer.parseInt(args[1]);
            logName = args[2];
        }
        else
        {
            regAddress = "localhost";
            regPort = 2222;
            logName = "log";
        }
        
        // Communications - Locate by remote object name the registed services
        RepositoryInterface repoInter = null;       // Repository Interface (Remote Object)
        ShopInterface shopInter = null;             // Shop Interface (Remote Object)
        WorkshopInterface workshopInter = null;     // Workshop Interface (Remote Object)
        PrimeStorageInterface primeStorageInter = null; // PrimeStorage Interface (Remote Object)
        
        ServerRegists repoRegist = new ServerRegists(regAddress, regPort, RemoteObjects.repo);        
        Registry registry;        
        try
        {
            registry = LocateRegistry.getRegistry(repoRegist.getRmiRegHostName(), repoRegist.getRmiRegPortNumb());
            repoInter = (RepositoryInterface) registry.lookup(repoRegist.getRegistEntry());
            repoInter.setLogFileName(logName); //  Communicate to the server the log name
            shopInter = (ShopInterface) registry.lookup(RemoteObjects.shop);
            workshopInter = (WorkshopInterface) registry.lookup(RemoteObjects.workshop);
            primeStorageInter = (PrimeStorageInterface) registry.lookup(RemoteObjects.primeStorage);
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
                
        // Entrepreneur Thread Initialization
        Entrepreneur entrepreneur = new Entrepreneur(repoInter, shopInter, workshopInter, primeStorageInter);
        
        // Threads Start
        entrepreneur.start();
        
        // Wait for the Simulation end
        try {
            entrepreneur.join();
        } catch (InterruptedException ex) {}
        
        // Informs servers that this client ended its operations 
        try
        {
            primeStorageInter.shutDown();
            shopInter.shutDown();
            repoInter.shutDown();
            workshopInter.shutDown();
        }
        catch (RemoteException e)
        {
            GenericIO.writelnString ("Exception invoking the remote method setLogFileName: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }
   }
}
