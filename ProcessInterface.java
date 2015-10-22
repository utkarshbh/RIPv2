import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Utkarsh Bhatia
 *
 */
public interface ProcessInterface extends Remote{
	/**
	 * 
	 * @param IP
	 * @return
	 * @throws RemoteException
	 */
	public String replyToconnect(String IP) throws RemoteException;
}
