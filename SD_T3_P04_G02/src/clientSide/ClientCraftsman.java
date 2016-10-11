package clientSide;

import comInf.ServerRegists;
import clientSide.Entities.Craftsman;
import comInf.RemoteObjects;
import comInf.States.InitialState;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import genclass.GenericIO;
import interfaces.*;

/**
 * This data type generate the Client Craftsman from the Aveiro Handicraft Problem  (access to shared
 * regions with remote object).
 * Solution of the Aveiro Handicraft Problem that implements the type 2 client-server model
 * (server replication) with static launch of the threads.
 * @author vsantos
 */
public class ClientCraftsman
{
   /**
    * Main Program - Craftsman.
    * @param args port and host numbers
    */
   public static void main (String [] args)
   {
       GenericIO.writelnString("      Problema artesanato aveiro - Craftsman \n");
       
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
            regAddress  = "localhost";
            regPort = 2222;
        }
        // Communications - Locate by remote object name the registed services
        RepositoryInterface repoInter = null;       // Repository Interface (Remote Object)
        ShopInterface shopInter = null;             // Shop Interface (Remote Object)
        WorkshopInterface workshopInter = null;     // Workshop Interface (Remote Object)
        
        ServerRegists repoRegist = new ServerRegists(regAddress, regPort, RemoteObjects.repo);        
        Registry registry;
        try
        {
            registry = LocateRegistry.getRegistry(repoRegist.getRmiRegHostName(), repoRegist.getRmiRegPortNumb());
            repoInter = (RepositoryInterface) registry.lookup(repoRegist.getRegistEntry());
            shopInter = (ShopInterface) registry.lookup(RemoteObjects.shop);
            workshopInter = (WorkshopInterface) registry.lookup(RemoteObjects.workshop);
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
        
        // Crafstman Thread Initialization
        Craftsman craftsman[] = new Craftsman[InitialState.getNumberOfCraftmen()];
        for (int j = 0; j < InitialState.getNumberOfCraftmen(); j++) {
            craftsman[j] = new Craftsman(j, repoInter, shopInter, workshopInter);
        }
        
        // Thread Start
        for (Craftsman c : craftsman) {
            c.start();
        }
        
        // Wait for the Simulation end
        for (Craftsman c : craftsman) {
            try {
                c.join();
            } catch (InterruptedException ex) {
            }
        }
        
        // Informs servers that this client ended its operations 
        try
        {
            repoInter.shutDown();
            shopInter.shutDown();
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
