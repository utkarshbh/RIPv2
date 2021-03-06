
/**
 * @author Utkarsh Bhatia
 *
 */
public class AddToRouting implements Runnable{

	//Declaring the variables
	String addIP;
	Integer linkWeight;
	String[] networkPrefixArray;
	String networkPrefix;
	
	/**
	 * @param addIP
	 * @param linkWeight
	 */
	public AddToRouting(String addIP, Integer linkWeight) {
		super();
		this.addIP = addIP;
		this.linkWeight = linkWeight;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		boolean neighbour = false;
		boolean availablePath = false;
		int indexVal = 0;
		networkPrefixArray = addIP.split("\\.");
		networkPrefix = networkPrefixArray[0] + "." + networkPrefixArray[1] + "." + networkPrefixArray[2] + ".0/24";
		if(Process.neighbourList.containsKey(networkPrefix)){
			neighbour = true;
		}
		for (int i = 0; i < Process.routingTable.size(); i++) {
			if(Process.routingTable.get(i).getDestIpAddr().equalsIgnoreCase(networkPrefix)){
				availablePath = true;
				indexVal = i;
			}
		}
		synchronized (Process.class) {
			if(neighbour==false){
				Process.neighbourList.put(networkPrefix, linkWeight);
				Process.neighbourIP.add(addIP);
			} else{
				Process.neighbourList.put(networkPrefix, linkWeight);
			}
		}
		synchronized (Process.class) {
			if(neighbour == false && availablePath == false){
				RouteValues routeVal = new RouteValues(networkPrefix, "255.255.255.0", networkPrefix, linkWeight);
				Process.routingTable.add(routeVal);
			} else if(neighbour == false && availablePath == true){
				if(Process.routingTable.get(indexVal).getDistance() > linkWeight){
					Process.routingTable.get(indexVal).setNextHop(networkPrefix);
					Process.routingTable.get(indexVal).setDistance(linkWeight);
				}
			} else if(neighbour == true &&  availablePath == true){
				Process.routingTable.get(indexVal).setNextHop(networkPrefix);
				Process.routingTable.get(indexVal).setDistance(linkWeight);
			}
		}
	}

}
