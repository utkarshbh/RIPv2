import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * @author Utkarsh Bhatia
 *
 */
public interface ProcessListenInterface extends Remote{
	/**
	 * Adds the neighbor to the routing table of the current RIP Router
	 * @param addIP
	 * @param linkW
	 * @param localAddr
	 * @throws RemoteException
	 */
	public void addToRoutingTable(String addIP, Integer linkW, String localAddr) throws RemoteException;
	/**
	 * Updates the routing table based on the routing table received from the neighbor
	 * @param IP
	 * @param neighbourRoutingTable
	 * @throws RemoteException
	 */
	public void updateRouting(String IP, ArrayList<RouteValues> neighbourRoutingTable) throws RemoteException;
	/**
	 * deletes the node when notified by the neighbor that the IP of the node sent has failed
	 * @param nodeDownIP
	 * @throws RemoteException
	 */
	public void deleteNode(String nodeDownIP) throws RemoteException;
}
