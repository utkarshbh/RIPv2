import java.io.Serializable;

/**
 * @author Utkarsh Bhatia
 *
 */
public class RouteValues implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Declaring the variables
	String DestIpAddr;
	String subnetMask;
	String nextHop;
	Integer distance;
	
	/**
	 * @param DestIpAddr
	 * @param subnetMask
	 * @param nextHop
	 * @param distance
	 */
	public RouteValues(String DestIpAddr, String subnetMask, String nextHop,
			Integer distance) {
		super();
		this.DestIpAddr = DestIpAddr;
		this.subnetMask = subnetMask;
		this.nextHop = nextHop;
		this.distance = distance;
	}
	
	/**
	 * @return
	 */
	public String getDestIpAddr() {
		return DestIpAddr;
	}
	/**
	 * @param destIpAddr
	 */
	public void setDestIpAddr(String destIpAddr) {
		DestIpAddr = destIpAddr;
	}
	/**
	 * @return
	 */
	public String getSubnetMask() {
		return subnetMask;
	}
	/**
	 * @param subnetMask
	 */
	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}
	/**
	 * @return
	 */
	public String getNextHop() {
		return nextHop;
	}
	/**
	 * @param nextHop
	 */
	public void setNextHop(String nextHop) {
		this.nextHop = nextHop;
	}
	/**
	 * @return
	 */
	public Integer getDistance() {
		return distance;
	}
	/**
	 * @param distance
	 */
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	
}
