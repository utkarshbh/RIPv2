import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * @author Utkarsh Bhatia
 *
 */
public class TriggerUpdate implements Runnable{

	//Declaring the variables
	String nodeDown;
	String[] nodeDownPrefixArr;
	String nodeDownNtwPrefix;
	Registry lookup;
	Boolean registered;
	ProcessListenInterface processListenInter;
	
	/**
	 * @param nodeDown
	 */
	public TriggerUpdate(String nodeDown) {
		super();
		this.nodeDown = nodeDown;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		boolean neighbour = false;
		boolean availablePath = false;
		int indexVal = 0;
		nodeDownPrefixArr = nodeDown.split("\\.");
		nodeDownNtwPrefix = nodeDownPrefixArr[0] + "." + nodeDownPrefixArr[1] + "." + nodeDownPrefixArr[2] + ".0/24";
		if(Process.neighbourList.containsKey(nodeDownNtwPrefix)){
			neighbour = true;
		}
		for (int i = 0; i < Process.routingTable.size(); i++) {
			if(Process.routingTable.get(i).getDestIpAddr().equalsIgnoreCase(nodeDownNtwPrefix)){
				availablePath = true;
				indexVal = i;
			}
		}
		synchronized (Process.class) {
			if(neighbour == true){
				Process.neighbourList.remove(nodeDownNtwPrefix);
			}
		}
		synchronized (Process.class) {
			if(availablePath == true){
				Process.routingTable.remove(indexVal);
			}
		}
	}
	
}
