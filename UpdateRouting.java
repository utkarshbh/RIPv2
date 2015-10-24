import java.util.ArrayList;

/**
 * @author Utkarsh Bhatia
 *
 */
public class UpdateRouting implements Runnable{

	//Declaring the variables
	String fromIP;
	ArrayList<RouteValues> neighList;
	String[] fromIpNtwPrefixArray;
	String fromIpNtwPrefix;
	String[] localNtwPfxArray;
	String localNetwPrefix;
	
	/**
	 * @param fromIP
	 * @param neighList
	 */
	public UpdateRouting(String fromIP, ArrayList<RouteValues> neighList) {
		super();
		this.fromIP = fromIP;
		this.neighList = neighList;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			boolean availablePath = false;
			int indexVal = 0;
			fromIpNtwPrefixArray = fromIP.split("\\.");
			fromIpNtwPrefix = fromIpNtwPrefixArray[0] + "." + fromIpNtwPrefixArray[1] + "." + fromIpNtwPrefixArray[2] + ".0/24";
			synchronized (Process.class) {
				localNtwPfxArray = Process.localIP.split("\\.");
				localNetwPrefix = localNtwPfxArray[0] + "." + localNtwPfxArray[1] + "." + localNtwPfxArray[2] + ".0/24";
				for (int i = 0; i < neighList.size(); i++) {
						if(!neighList.get(i).getDestIpAddr().equalsIgnoreCase(localNetwPrefix)){
						indexVal = 0; availablePath = false;
						for (int j = 0; j < Process.routingTable.size(); j++) {
							if(neighList.get(i).getDestIpAddr().equalsIgnoreCase(Process.routingTable.get(j).getDestIpAddr())){
								availablePath = true;
								indexVal = j;
								break;
							}
						}
						if(Process.neighbourList.containsKey(fromIpNtwPrefix) && !(neighList.get(i).getNextHop().equalsIgnoreCase(localNetwPrefix))){
							if(availablePath == false){
								RouteValues routeVal = new RouteValues(neighList.get(i).getDestIpAddr(), "255.255.255.0", fromIpNtwPrefix, neighList.get(i).getDistance() + Process.neighbourList.get(fromIpNtwPrefix));
								Process.routingTable.add(routeVal);
							} else{
								if(Process.routingTable.get(indexVal).getDistance() > (neighList.get(i).getDistance() + Process.neighbourList.get(fromIpNtwPrefix))){
									Process.routingTable.get(indexVal).setNextHop(fromIpNtwPrefix);
									Process.routingTable.get(indexVal).setDistance(neighList.get(i).getDistance() + Process.neighbourList.get(fromIpNtwPrefix));
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
