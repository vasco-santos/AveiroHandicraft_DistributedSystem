package serverSide.PrimeStorage;

import comInf.RemoteObjects;
import comInf.ServerRegists;
import genclass.GenericIO;
import interfaces.PrimeStorageInterface;
import interfaces.Register;
import interfaces.RepositoryInterface;
import interfaces.WorkshopInterface;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * This data Type simulates a solution of the Prime Storage server in the Aveiro Handicraft Problem.
 * @author vsantos
 */
public class PrimeStorageServer 
{       
    /**
     * Number of finished threads.
     *
     * @serialField run
     */
    private static int run = 0;
        
    /**
     * Main Program of the Prime Storage Server.
     * @param args arguments to the program
     */
    public static void main(String[] args) {

        // Inputs for communication
        String regAddress;
        int regPort;
        int portNumb;

        if (args.length == 3) {
            portNumb = Integer.parseInt(args[0]); // LA EM BAIXO
            regAddress = args[1];
            regPort = Integer.parseInt(args[2]);
        } else {
            portNumb = 1102;
            regAddress = "localhost";
            regPort = 2222;
        }

        /* obtenção da localização do serviço de registo RMI */
        if (System.getSecurityManager() == null)
        {
            System.setSecurityManager(new SecurityManager());
        }

        Registry registry = null;
        /* Remote Object */
        RemotePrimeStorage primeStorageRemote = null;                              // represents remote object
        PrimeStorageInterface primeStorageInter = null;             // interface to the remote object

                // Locate by remote object name the registed services
        RepositoryInterface repoInter = null;       // Repository Interface (Remote Object)
        WorkshopInterface workshopInter = null;     // Workshop Interface (Remote Object)
        
        ServerRegists repoRegist = new ServerRegists(regAddress, regPort, RemoteObjects.repo);
        try
        {
            registry = LocateRegistry.getRegistry(repoRegist.getRmiRegHostName(), repoRegist.getRmiRegPortNumb());
            repoInter = (RepositoryInterface) registry.lookup(repoRegist.getRegistEntry());
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
        
        PrimeStorage primeStorage = new PrimeStorage(repoInter, workshopInter);
        primeStorageRemote = new RemotePrimeStorage(primeStorage);
        try {
            primeStorageInter = (PrimeStorageInterface) UnicastRemoteObject.exportObject(primeStorageRemote, portNumb);
        } catch (RemoteException e) {
            GenericIO.writelnString("Exception generating the PrimeStorage Stub: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        GenericIO.writelnString("Prime Storage Interface is ready!");

        /* Registry Prime Storage in RMI Registry */
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
        
        try {
            regHandler.bind(RemoteObjects.primeStorage, primeStorageInter);
        } catch (RemoteException e) {
            GenericIO.writelnString("Exception in the Prime Storage Regist: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            GenericIO.writelnString("Pime Storage is already registed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        GenericIO.writelnString("Prime Storage registed and ready!");
        
        // Wait for the active entities to Stop (Entrepreneur)
        waitForTheEnd();
        
        // End Server
        try
        {
            regHandler.unbind(RemoteObjects.primeStorage);
            regHandler.shutDown();
            UnicastRemoteObject.unexportObject(primeStorageRemote, true);
        }
        catch(NotBoundException e){
            GenericIO.writelnString("Prime Storage Regist not bound!" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        catch(RemoteException e){
            GenericIO.writelnString("Prime Storage Regist not found!" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Wait for the active entities to Stop (Entrepreneur).
     */
    public static void waitForTheEnd()
    {
        try
        {
            synchronized(Class.forName("serverSide.PrimeStorage.PrimeStorageServer"))
            {
                while (run == 0)
                {
                    Class.forName("serverSide.PrimeStorage.PrimeStorageServer").wait();
                    run++;
                }
            }
        }
        catch (ClassNotFoundException e)
        {   GenericIO.writelnString ("The Data Type serverSide.PrimeStorage.PrimeStorageServer was not found!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (InterruptedException e)
        { GenericIO.writelnString ("The static method was interruptded!");
        }
    }
    
    /**
     * Ask for the Server to shut down.
     */
    public static synchronized void endRun()
    {
        try
        {
            Class.forName("serverSide.PrimeStorage.PrimeStorageServer").notifyAll();
        }
        catch (ClassNotFoundException e)
        { GenericIO.writelnString ("The Data Type serverSide.PrimeStorage.PrimeStorageServer was not found!");
          e.printStackTrace ();
          System.exit (1);
        }
    }
}
