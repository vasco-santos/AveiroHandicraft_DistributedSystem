package serverSide.Shop;

import comInf.RemoteObjects;
import comInf.ServerRegists;
import genclass.GenericIO;
import interfaces.Register;
import interfaces.RepositoryInterface;
import interfaces.ShopInterface;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * This data Type simulates a solution of the Shop server in the Aveiro Handicraft Problem.
 * @author vsantos
 */
public class ShopServer {
   /**
     * Number of finished threads.
     * @serialField run
     */
    private static int run = 0;

    /**
     * Main Program of the Prime Storage Server.
     * @param args arguments to the program
     */
    public static void main(String[] args) {

        // Inputs for Communication
        String regAddress;
        int regPort;
        int portNumb;
        if (args.length == 3)
        {
            portNumb = Integer.parseInt(args[0]);
            regAddress = args[1];
            regPort = Integer.parseInt(args[2]);
        }
        else
        {
            portNumb = 1100;
            regAddress = "localhost";
            regPort = 2222;
        }

        /* obtenção da localização do serviço de registo RMI */
        if (System.getSecurityManager() == null)
        {
            System.setSecurityManager(new SecurityManager());
        }
        Registry registry = null;        
        
        // Locate by remote object name the registed services
        RepositoryInterface repoInter = null;       // Repository Interface (Remote Object)
         
        ServerRegists repoRegist = new ServerRegists(regAddress, regPort, RemoteObjects.repo);
        try
        {
            registry = LocateRegistry.getRegistry(repoRegist.getRmiRegHostName(), repoRegist.getRmiRegPortNumb());
            repoInter = (RepositoryInterface) registry.lookup(repoRegist.getRegistEntry());
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
        
        Shop shop = new Shop(repoInter);
        RemoteShop remoteShop = new RemoteShop(shop); // represents remote object
        ShopInterface shopInter = null;  // interface to the remote object
        try {
            shopInter = (ShopInterface) UnicastRemoteObject.exportObject(remoteShop, portNumb);
        } catch (RemoteException e) {
            GenericIO.writelnString("Exception in the RMI regist creation:  " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        GenericIO.writelnString("RMI Regist created!");

        /* Registry Shop in RMI Registry */
        Register regHandler = null;
        try
        {
            regHandler = (Register) registry.lookup(RemoteObjects.registry);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("RegisterRemoteObject lookup exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        catch (NotBoundException e)
        { GenericIO.writelnString ("RegisterRemoteObject not bound exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
        
        try
        {
            regHandler.bind(RemoteObjects.shop, shopInter);
        } catch (RemoteException e) {
            GenericIO.writelnString("Exception in the Shop Regist: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            GenericIO.writelnString("Shop is already registed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        GenericIO.writelnString("Shop registed and ready!");
        
        // Wait for the active entities to Stop (Entrepreneur, Customers and Crafstmen)
        waitForTheEnd();
        
        // End Server
        try
        {
            regHandler.unbind(RemoteObjects.shop);
            regHandler.shutDown();
            UnicastRemoteObject.unexportObject(remoteShop, true);
        }
        catch(NotBoundException e){
            GenericIO.writelnString("Shop Regist not bound!" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        catch(RemoteException e){
            GenericIO.writelnString("Shop Regist not found!" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }        
    
    /**
     * Wait for the active entities to Stop (Entrepreneur, Customers and Crafstmen).
     */
    public static void waitForTheEnd()
    {
        try
        {
            synchronized(Class.forName("serverSide.Shop.ShopServer"))
            {
                while (run < 3)
                {
                    Class.forName("serverSide.Shop.ShopServer").wait();
                    run++;
                }
            }
        }
        catch (ClassNotFoundException e)
        {   GenericIO.writelnString ("The Data Type serverSide.Shop.ShopServer was not found!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (InterruptedException e)
        {   GenericIO.writelnString ("The static method was interruptded!");
            e.printStackTrace ();
            System.exit (1);
        }
    }
    
    /**
     * Ask for the Server to shut down.
     */
    public synchronized static void endRun()
    {
        try
        {
            Class.forName("serverSide.Shop.ShopServer").notifyAll();
        }
        catch (ClassNotFoundException e)
        { GenericIO.writelnString ("The Data Type serverSide.Shop.ShopServer was not found!");
          e.printStackTrace ();
          System.exit (1);
        }
    }
    
}
