import java.net.Inet4Address;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Utkarsh Bhatia
 *
 */
public class ProcessListen extends UnicastRemoteObject implements ProcessListenInterface{

	//Declaring the variables
	Scanner scan;
	private Registry rgs;
	private String localIP;
	Registry lookup;
	ProcessListenInterface procListenInter;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @throws RemoteException
	 */
	protected ProcessListen() throws RemoteException {
		super();
		try {
			//binding the registry
			scan = new Scanner(System.in);
			rgs = LocateRegistry.createRegistry(6364);
			rgs.rebind("processRIPListen", this);
			localIP = Inet4Address.getLocalHost().getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see ProcessListenInterface#updateRouting(java.lang.String, java.util.ArrayList)
	 */
	public void updateRouting(String IP, ArrayList<RouteValues> neighbourRoutingTable) throws RemoteException{
		new Thread(new UpdateRouting(IP, neighbourRoutingTable)).start();
	}

	/* (non-Javadoc)
	 * @see ProcessListenInterface#addToRoutingTable(java.lang.String, java.lang.Integer, java.lang.String)
	 */
	public void addToRoutingTable(String addIP, Integer linkW, String localAddr) throws RemoteException{
		new Thread(new ForwardTable(addIP, linkW, localIP, true)).start();;
	}
	
	/* (non-Javadoc)
	 * @see ProcessListenInterface#deleteNode(java.lang.String)
	 */
	public void deleteNode(String nodeDownIP) throws RemoteException{
		new Thread(new TriggerUpdate(nodeDownIP)).start();
	}
}
