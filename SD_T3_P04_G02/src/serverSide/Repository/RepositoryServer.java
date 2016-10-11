package serverSide.Repository;

import comInf.RemoteObjects;
import genclass.GenericIO;
import interfaces.Register;
import interfaces.RepositoryInterface;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * This data Type simulates a solution of the Repository server in the Aveiro Handicraft Problem.
 * @author vsantos
 */
public class RepositoryServer {
   /**
     * Number of finished threads.
     *
     * @serialField run
     */
    private static int run = 0;

    /**
     * Main Program of the Prime Storage Server.
     *
     * @param args arguments to the program
     */
    public static void main(String[] args) {

        // Inputs for communication
        int portNumb;
        String regAddress;
        int regPort;
        if (args.length == 3)
        {
            portNumb = Integer.parseInt(args[0]);
            regAddress = args[1];
            regPort = Integer.parseInt(args[2]);
        }
        else
        {
            portNumb = 1099;
            regAddress = "localhost";
            regPort = 2222;
        }

        /* RMI Service Location */
        if (System.getSecurityManager() == null)
        {
            System.setSecurityManager(new SecurityManager());
        }

        Registry registry = null;
        RepositoryInterface repositoryInter = null;       // interface to the remote object

        Repository repository = new Repository(); /* Remote Object */
        try {
            repositoryInter = (RepositoryInterface) UnicastRemoteObject.exportObject(repository,portNumb);
        } catch (RemoteException e) {
            GenericIO.writelnString("Exception generating the Repository Stub:" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        GenericIO.writelnString("Repository Interface is ready!");
        
        /* Registry repository in RMI Registry */
        try {
            registry = LocateRegistry.getRegistry(regAddress, regPort);
        } catch (RemoteException e) {
            GenericIO.writelnString("Exception in the RMI regist creation: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        GenericIO.writelnString("RMI Regist accessed!");
        
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
            regHandler.bind(RemoteObjects.repo, repositoryInter);
        } catch (RemoteException e) {
            GenericIO.writelnString("Exception in the Repository Regist: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            GenericIO.writelnString("Repository is already registed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        GenericIO.writelnString("Repository is ready!");
        
        // Wait for the active entities to Stop (Entrepreneur, Customers and Crafstmen)
        waitForTheEnd();
        
        // End Server
        try
        {
            regHandler.unbind(RemoteObjects.repo);
            regHandler.shutDown();
            UnicastRemoteObject.unexportObject(repository, true);
        }
        catch(NotBoundException e){
            GenericIO.writelnString("Repository Regist not bound!" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        catch(RemoteException e){
            GenericIO.writelnString("Repository Regist not found!" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Done");
        repository.sortLog();
    }    
    
    /**
     * Wait for the active entities to Stop (Entrepreneur, Customers and Crafstmen).
     */
    public static void waitForTheEnd()
    {
        try
        {
            synchronized(Class.forName("serverSide.Repository.RepositoryServer"))
            {
                while (run < 3)
                {
                    Class.forName("serverSide.Repository.RepositoryServer").wait();
                    run++;
                }
            }
        }
        catch (ClassNotFoundException e)
        {   GenericIO.writelnString ("The Data Type serverSide.Repository.RepositoryServer was not found!");
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
    public synchronized static void endRun()
    {
        try
        {
            Class.forName("serverSide.Repository.RepositoryServer").notifyAll();
        }
        catch (ClassNotFoundException e)
        { GenericIO.writelnString ("The Data Type serverSide.Repository.RepositoryServer was not found!");
          e.printStackTrace ();
          System.exit (1);
        }
    }

}
