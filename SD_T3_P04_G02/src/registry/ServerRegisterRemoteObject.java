package registry;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.AccessException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.*;
import genclass.GenericIO;
import interfaces.Register;
import static java.lang.Thread.sleep;

/**
 *  This data type instantiates and registers a remote object that enables the registration of other remote objects
 *  located in the same or other processing nodes in the local registry service.
 *  Communication is based in Java RMI.
 */

public class ServerRegisterRemoteObject
{
    /**
     * Number of finished servers.
     * @serialField run
     */
    private static int run = 0;
    
  /**
   *  Main task.
   * @param args Console Arguments.
   */
   public static void main(String[] args)
   {
        /* get location of the registry service */
        // Inputs for communication
        String rmiRegHostName;
        int rmiRegPortNumb;
        int listeningPort;
        if (args.length == 3)
        {
            listeningPort = Integer.parseInt(args[0]);
            rmiRegHostName = args[1];
            rmiRegPortNumb = Integer.parseInt(args[2]);
        }
        else
        {
            listeningPort = 0;
            rmiRegHostName = "localhost";
            rmiRegPortNumb = 22410;
        }

        /* create and install the security manager */

         if (System.getSecurityManager () == null)
            System.setSecurityManager (new SecurityManager ());
         GenericIO.writelnString ("Security manager was installed!");

        /* instantiate a registration remote object and generate a stub for it */

         RegisterRemoteObject regEngine = new RegisterRemoteObject (rmiRegHostName, rmiRegPortNumb);
         Register regEngineStub = null;

         try
         { regEngineStub = (Register) UnicastRemoteObject.exportObject (regEngine, listeningPort);
         }
         catch (RemoteException e)
         { GenericIO.writelnString ("RegisterRemoteObject stub generation exception: " + e.getMessage ());
           System.exit (1);
         }
         GenericIO.writelnString ("Stub was generated!");

        /* register it with the local registry service */

         String nameEntry = "RegisterHandler";
         Registry registry = null;

         try
         { registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
         }
         catch (RemoteException e)
         { GenericIO.writelnString ("RMI registry creation exception: " + e.getMessage ());
           System.exit (1);
         }
         GenericIO.writelnString ("RMI registry was created!");

         try
         { registry.rebind (nameEntry, regEngineStub);
         }
         catch (RemoteException e)
         { GenericIO.writelnString ("RegisterRemoteObject remote exception on registration: " + e.getMessage ());
           System.exit (1);
         }
         GenericIO.writelnString ("RegisterRemoteObject object was registered!");

         // Wait for the active entities to Stop (Entrepreneur and Crafstmen)
         waitForTheEnd();
         
         // End Server
        try
        {
            registry.unbind(nameEntry);
            UnicastRemoteObject.unexportObject(regEngine, true);
        }
        catch(NotBoundException e){
            GenericIO.writelnString("Workshop Regist not bound!" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        catch(RemoteException e){
            GenericIO.writelnString("Workshop Regist not found!" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
   }
   
   /**
     * Wait for the servers registed to Stop (Repository, Shop, Workshop and Prime Storage).
     */
    public static void waitForTheEnd()
    {
        try
        {
            synchronized(Class.forName("registry.ServerRegisterRemoteObject"))
            {
                while (run < 4)
                {
                    Class.forName("registry.ServerRegisterRemoteObject").wait();
                    System.out.println("end1");
                    run++;
                }
            }
        }
        catch (ClassNotFoundException e)
        {   GenericIO.writelnString ("The Data Type registry.ServerRegisterRemoteObject was not found!");
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
            Class.forName("registry.ServerRegisterRemoteObject").notifyAll();
        }
        catch (ClassNotFoundException e)
        { GenericIO.writelnString ("The Data Type serverSide.Workshop.WorkshopServer was not found!");
          e.printStackTrace ();
          System.exit (1);
        }
    }
   
}
