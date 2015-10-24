import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Utkarsh Bhatia
 *
 */
public class ProcessDown implements Runnable{

	//Declaring the variables
	String nodeDown;
	Registry lookup;
	Boolean registered;
	ProcessListenInterface processListenInter;
	String[] nodeDownPrefixArr;
	String nodeDownNtwPrefix;
	
	/**
	 * @param nodeDown
	 */
	public ProcessDown(String nodeDown) {
		super();
		this.nodeDown = nodeDown;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
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
				Process.neighbourIP.remove(nodeDown);
			}
			for (int i = 0; i < Process.routingTable.size(); i++) {
				if(Process.routingTable.get(i).getNextHop().equalsIgnoreCase(nodeDownNtwPrefix)){
					Process.routingTable.get(i).setDistance(Math.abs(99999));
				}
			}
			for (int i = 0; i < Process.neighbourIP.size(); i++) {
				try{
					lookup = LocateRegistry.getRegistry(Process.neighbourIP.get(i), 6364);
					processListenInter = (ProcessListenInterface) lookup.lookup("processRIPListen");
					processListenInter.deleteNode(nodeDownNtwPrefix);
				} catch(Exception e){
					e.printStackTrace();
				}
		}
		synchronized (Process.class) {
			if(availablePath == true){
				Process.routingTable.remove(indexVal);
			}
			for (int i = 0; i < Process.neighbourIP.size(); i++) {
				try{
					lookup = LocateRegistry.getRegistry(Process.neighbourIP.get(i), 6364);
					processListenInter = (ProcessListenInterface) lookup.lookup("processRIPListen");
					processListenInter.deleteNode(nodeDownNtwPrefix);
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
}
