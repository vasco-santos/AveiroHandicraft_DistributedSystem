package serverSide.Shop;

import comInf.States.CraftsmanState;
import comInf.States.CustomerState;
import comInf.States.EntrepreneurState;
import comInf.States.InitialState;
import comInf.Tuple;
import comInf.VectorialClock;
import genclass.GenericIO;
import interfaces.ShopInterface;
import java.rmi.RemoteException;

/**
 * Remote Shop Data Type, with Entity States State Machine.
 * Receives client request, validate Entity State, update Vectorial Clock,
 * handle the request and responds.
 * @author vsantos
 */
public class RemoteShop implements ShopInterface
{

    /**
     * Vectorial clock of Shop.
     * @serialField vetorclock
     */
    private final VectorialClock vectorClock;
    
    /**
     * Shop Monitor.
     *   @serialField shop.
     */
    private final Shop shop;
    
    /**
     * Instantiate Remote Shop Data Type.
     * @param shop Shop Monitor.
     */
    public RemoteShop (Shop shop)
    {
        this.shop = shop;
        vectorClock = new VectorialClock(InitialState.getNumberOfEntities(),8);
    }

    /**
     * Prime Materials Needed invocation by the client.
     * @param tuple with Craftsman State and Vector Clock.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException  Unable to call remote object
     */
    @Override
    public Tuple primeMaterialsNeeded(Tuple tuple, int craftID) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != CraftsmanState.FETCHING_PRIME_MATERIALS)
        {
            GenericIO.writeString("Invalid State!\nExpected: FETCHING_PRIME_MATERIALS\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        shop.primeMaterialsNeeded(craftID, vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), CraftsmanState.CONTACTING_THE_ENTREPENEUR);
    }

    /**
     * Batch Ready For Transfer invocation by the client.
     * @param tuple with Craftsman State and Vector Clock.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException  Unable to call remote object
     */
    @Override
    public Tuple batchReadyForTransfer(Tuple tuple, int craftID) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != CraftsmanState.STORING_FOR_TRANSFER)
        {
            GenericIO.writeString("Invalid State!\nExpected: STORING_FOR_TRANSFER\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        shop.batchReadyForTransfer(craftID, vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), CraftsmanState.CONTACTING_THE_ENTREPENEUR);
    }

    /**
     * Go Shopping invocation by the client.
     * @param tuple with Customer State and Vector Clock.
     * @param custID customer identification.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple goShopping(Tuple tuple, int custID) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != CustomerState.CARRYING_DAILY_CHORES)
        {
            GenericIO.writeString("Invalid State!\nExpected: CARRYING_DAILY_CHORES\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        shop.goShopping(custID, vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), CustomerState.CHECKING_SHOP_DOOR_OPEN);
    }

    /**
     * Is door Open invocation by the client.
     * @param tuple with Customer State and Vector Clock.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple isDoorOpen(Tuple tuple) throws RemoteException 
    {
        // Verify State
        if (tuple.getState() != CustomerState.CHECKING_SHOP_DOOR_OPEN)
        {
            GenericIO.writeString("Invalid State!\nExpected: CHECKING_SHOP_DOOR_OPEN\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        boolean isDoorOpen = shop.isDooROpen(vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), isDoorOpen, CustomerState.CHECKING_SHOP_DOOR_OPEN);
    }

    /**
     * Try Again Later invocation by the client.
     * @param tuple with Customer State and Vector Clock.
     * @param custID customer identification.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple tryAgainLater(Tuple tuple, int custID) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != CustomerState.CHECKING_SHOP_DOOR_OPEN)
        {
            GenericIO.writeString("Invalid State!\nExpected: CHECKING_SHOP_DOOR_OPEN\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        shop.tryAgainLater(custID, vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), CustomerState.CARRYING_DAILY_CHORES);
    }

    /**
     * Enter shop invocation by the client.
     * @param tuple with Customer State and Vector Clock.
     * @param custID customer identification.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple enterShop(Tuple tuple, int custID) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != CustomerState.CHECKING_SHOP_DOOR_OPEN)
        {
            GenericIO.writeString("Invalid State!\nExpected: CHECKING_SHOP_DOOR_OPEN\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        shop.enterShop(custID, vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), CustomerState.APRAISING_OFFER_IN_DISPLAY);
    }

    /**
     * Perusing Around invocation by the client.
     * @param tuple with Customer State and Vector Clock.
     * @param custID customer identification.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple perusingAround(Tuple tuple, int custID) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != CustomerState.APRAISING_OFFER_IN_DISPLAY)
        {
            GenericIO.writeString("Invalid State!\nExpected: APRAISING_OFFER_IN_DISPLAY\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        int purchasedProducts = shop.perusingAround(custID, vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), purchasedProducts, CustomerState.APRAISING_OFFER_IN_DISPLAY);
    }

    /**
     * I Want this invocation by the client.
     * @param tuple with Customer State and Vector Clock.
     * @param custID customer identification.
     * @param numProducts number of products.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple iWantThis(Tuple tuple, int custID, int numProducts) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != CustomerState.APRAISING_OFFER_IN_DISPLAY)
        {
            GenericIO.writeString("Invalid State!\nExpected: APRAISING_OFFER_IN_DISPLAY\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        shop.iWantThis(custID, numProducts, vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), CustomerState.BUYING_SOME_GOODS);
    }

    /**
     * Exit Shop invocation by the client.
     * @param tuple with Customer State and Vector Clock.
     * @param custID customer identification.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple exitShop(Tuple tuple, int custID) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != CustomerState.APRAISING_OFFER_IN_DISPLAY && tuple.getState() != CustomerState.BUYING_SOME_GOODS)
        {
            GenericIO.writeString("Invalid State!\nExpected: APRAISING_OFFER_IN_DISPLAY\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        shop.exitShop(custID, vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), CustomerState.CARRYING_DAILY_CHORES);
    }

    /**
     * Prepare to work invocation by the client.
     * @param tuple with Entrepreneur State and Vector Clock.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple prepareToWork(Tuple tuple) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != EntrepreneurState.OPENING_THE_SHOP)
        {
            GenericIO.writeString("Invalid State!\nExpected: APRAISING_OFFER_IN_DISPLAY\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        shop.prepareToWork(vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), EntrepreneurState.WAITING_FOR_NEXT_TASK);
    }

    /**
     * Appraise Sit invocation by the client.
     * @param tuple with Entrepreneur State and Vector Clock.
     * @return Tuple with next state, new Vector Clock and function result.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple appraiseSit(Tuple tuple) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != EntrepreneurState.WAITING_FOR_NEXT_TASK && tuple.getState() != EntrepreneurState.ATTENDING_A_CUSTOMER)
        {
            GenericIO.writeString("Invalid State!\nExpected: WAITING_FOR_NEXT_TASK\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        char option = shop.appraiseSit(vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), option, EntrepreneurState.WAITING_FOR_NEXT_TASK);
    }

    /**
     * Address a Customer invocation by the client.
     * @param tuple with Entrepreneur State and Vector Clock.
     * @return Tuple with next state, customer id and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple addressACustomer(Tuple tuple) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != EntrepreneurState.WAITING_FOR_NEXT_TASK)
        {
            GenericIO.writeString("Invalid State!\nExpected: WAITING_FOR_NEXT_TASK\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        int custID = shop.addressACustomer(vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), custID, EntrepreneurState.ATTENDING_A_CUSTOMER);
    }

    /**
     * Say Good Bye invocation by the client.
     * @param tuple with Entrepreneur State and Vector Clock.
     * @param custID customer identification
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple sayGoodByeToCustomer(Tuple tuple, int custID) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != EntrepreneurState.ATTENDING_A_CUSTOMER)
        {
            GenericIO.writeString("Invalid State!\nExpected: ATTENDING_A_CUSTOMER\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        shop.sayGoodByeToCustomer(custID, vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), EntrepreneurState.WAITING_FOR_NEXT_TASK);
    }

    /**
     * Close the door invocation by the client.
     * @param tuple with Entrepreneur State and Vector Clock.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple closeTheDoor(Tuple tuple) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != EntrepreneurState.WAITING_FOR_NEXT_TASK)
        {
            GenericIO.writeString("Invalid State!\nExpected: WAITING_FOR_NEXT_TASK\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        shop.closeTheDoor(vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), EntrepreneurState.WAITING_FOR_NEXT_TASK);
    }

    /**
     * Customers in the Shop invocation by the client.
     * @param tuple with Entrepreneur State and Vector Clock.
     * @return Tuple with next state, operation result and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple customersInTheShop(Tuple tuple) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != EntrepreneurState.WAITING_FOR_NEXT_TASK)
        {
            GenericIO.writeString("Invalid State!\nExpected: WAITING_FOR_NEXT_TASK\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        boolean b = shop.customerIntTheShop(vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), b, EntrepreneurState.WAITING_FOR_NEXT_TASK);
    }

    /**
     * Prepare to Leave invocation by the client.
     * @param tuple with Entrepreneur State and Vector Clock.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple prepareToLeave(Tuple tuple) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != EntrepreneurState.WAITING_FOR_NEXT_TASK)
        {
            GenericIO.writeString("Invalid State!\nExpected: WAITING_FOR_NEXT_TASK\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        shop.prepareToLeave(vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), EntrepreneurState.CLOSING_THE_SHOP);
    }

    /**
     * Return to Shop invocation by the client.
     * @param tuple with Entrepreneur State and Vector Clock.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple returnToShop(Tuple tuple) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != EntrepreneurState.CLOSING_THE_SHOP && tuple.getState() != EntrepreneurState.COLLECTING_BATCH_OF_PRODUCTS 
                && tuple.getState() != EntrepreneurState.BUYING_PRIME_MATERIAL && tuple.getState() != EntrepreneurState.EXITING_THE_SHOP)
        {
            GenericIO.writeString("Invalid State!\nExpected: CLOSING_THE_SHOP or COLLECTING_BATCH_OF_PRODUCTS or BUYING_PRIME_MATERIAL\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        shop.returnToShop(vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), EntrepreneurState.OPENING_THE_SHOP);
    }

    /**
     * Entrepreneur exit shop invocation by the client.
     * @param tuple with Entrepreneur State and Vector Clock.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple entExitShop(Tuple tuple, char alternative) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != EntrepreneurState.CLOSING_THE_SHOP)
        {
            GenericIO.writeString("Invalid State!\nExpected: CLOSING_THE_SHOP\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        shop.entExitShop(alternative, vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), EntrepreneurState.EXITING_THE_SHOP);
    }

    /**
     * Arrange Products invocation by the client.
     * @param tuple with Entrepreneur State and Vector Clock.
     * @return Tuple with next state and new Vector Clock.
     * @throws RemoteException Unable to call remote object
     */
    @Override
    public Tuple arrangeProducts(Tuple tuple, int numberOfProducts) throws RemoteException
    {
        // Verify State
        if (tuple.getState() != EntrepreneurState.COLLECTING_BATCH_OF_PRODUCTS)
        {
            GenericIO.writeString("Invalid State!\nExpected: COLLECTING_BATCH_OF_PRODUCTS\nReceived: " + tuple.getState());
            System.exit(-1);
        }
        // Valid State
        shop.arrangeProducts(numberOfProducts, vectorClock.update(tuple.getClock()));
        return new Tuple(vectorClock.getCopy(), EntrepreneurState.OPENING_THE_SHOP);
    }

    /**
     * Ask Server for Shutdown. 
     * The client says that his work is done. Server verifies if it can shutdown
     */
    @Override
    public void shutDown() throws RemoteException
    {
        ShopServer.endRun();
    }
    
    public static void update(VectorialClock vc)
    {
    }
}
